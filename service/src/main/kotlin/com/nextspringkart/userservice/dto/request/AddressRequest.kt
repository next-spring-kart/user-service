package com.nextspringkart.userservice.dto.request

import jakarta.validation.constraints.NotBlank

data class AddressRequest(
    @field:NotBlank(message = "Address line 1 is required")
    val addressLine1: String,

    val addressLine2: String? = null,

    @field:NotBlank(message = "City is required")
    val city: String,

    @field:NotBlank(message = "State is required")
    val state: String,

    @field:NotBlank(message = "Postal code is required")
    val postalCode: String,

    @field:NotBlank(message = "Country is required")
    val country: String,

    val isDefault: Boolean = false
)