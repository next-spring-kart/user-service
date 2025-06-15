package com.nextspringkart.userservice.dto.response

import com.nextspringkart.userservice.entity.Role
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String?,
    val role: Role,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val addresses: List<AddressResponse> = emptyList()
)