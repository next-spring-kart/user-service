package com.nextspringkart.userservice.filter

import com.nextspringkart.userservice.entity.Role
import com.nextspringkart.userservice.entity.User
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RoleHeaderAuthenticationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val rolesHeader = request.getHeader("X-User-Roles")
        val username = request.getHeader("X-Username")
        val userId = request.getHeader("X-User-Id")
        val authorities = rolesHeader.split(",").map { SimpleGrantedAuthority("ROLE_" + it.trim()) }
        val user = User(
            id = userId.toLong(),
            email = username,
            firstName = "",
            lastName = "",
            password = "",
            role = rolesHeader.split(",").firstOrNull()?.let { role ->
                when (role.trim().uppercase()) {
                    "ADMIN" -> Role.ADMIN
                    "USER" -> Role.USER
                    else -> Role.MODERATOR
                }
            } ?: Role.USER,
        )
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            user,
            null,
            authorities
        )

        filterChain.doFilter(request, response)
    }
}