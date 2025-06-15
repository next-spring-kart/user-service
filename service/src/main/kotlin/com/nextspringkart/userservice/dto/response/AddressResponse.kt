package com.nextspringkart.userservice.dto.response

data class AddressResponse(
    val id: Long,
    val addressLine1: String,
    val addressLine2: String?,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String,
    val isDefault: Boolean
)