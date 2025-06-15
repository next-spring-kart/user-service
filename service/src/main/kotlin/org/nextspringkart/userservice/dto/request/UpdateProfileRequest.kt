package org.nextspringkart.userservice.dto.request

data class UpdateProfileRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null
)