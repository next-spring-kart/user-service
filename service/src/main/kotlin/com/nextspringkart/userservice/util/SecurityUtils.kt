package com.nextspringkart.userservice.util

import com.nextspringkart.userservice.entity.User
import com.nextspringkart.userservice.exception.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtils {

    fun getCurrentUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("No authentication found")

        return when (val principal = authentication.principal) {
            is User -> principal.id
            is UserDetails -> {
                // If using custom UserDetails, extract user ID from username or details
                throw UnauthorizedException("Unable to extract user ID from authentication")
            }

            else -> throw UnauthorizedException("Invalid authentication principal")
        }
    }

    fun getCurrentUserEmail(): String {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("No authentication found")

        return when (val principal = authentication.principal) {
            is User -> principal.email
            is UserDetails -> principal.username
            else -> throw UnauthorizedException("Invalid authentication principal")
        }
    }

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw UnauthorizedException("No authentication found")

        return when (val principal = authentication.principal) {
            is User -> principal
            else -> throw UnauthorizedException("Invalid authentication principal")
        }
    }

    fun hasRole(role: String): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: return false

        return authentication.authorities.any {
            it.authority == "ROLE_$role" || it.authority == role
        }
    }

    fun isAuthenticated(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication != null && authentication.isAuthenticated
    }
}