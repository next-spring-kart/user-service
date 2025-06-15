package com.nextspringkart.userservice.dto.response

data class AuthResponse(
    val token: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long,
    val user: UserResponse
)
