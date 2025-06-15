package com.nextspringkart.userservice.service

import com.nextspringkart.userservice.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.MacAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.expiration}")
    private val expiration: Long
) {
    private val algorithm: MacAlgorithm = Jwts.SIG.HS256
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(user: User): String {
        val claims = mutableMapOf<String, Any>(
            "userId" to user.id,
            "role" to user.role.name,
            "firstName" to user.firstName,
            "lastName" to user.lastName
        )
        return createToken(claims, user.email)
    }

    private fun createToken(claims: MutableMap<String, Any>?, subject: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)

        return Jwts.builder()
            .claims()
            .add(claims)
            .subject(subject)
            .issuedAt(now)
            .expiration(expiryDate)
            .and()
            .signWith(key, algorithm)
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    fun extractUserId(token: String) = extractClaim(token) { it["userId"] as Long }
    fun extractRole(token: String) = extractClaim(token) { it["role"] as String }
    fun extractUsername(token: String): String? = extractClaim(token, Claims::getSubject)
    fun getExpirationTime() = expiration
    private fun isTokenExpired(token: String) = extractExpiration(token).before(Date())
    private fun extractExpiration(token: String) = extractClaim(token, Claims::getExpiration)
}