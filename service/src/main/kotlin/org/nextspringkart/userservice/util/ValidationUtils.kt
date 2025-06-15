package org.nextspringkart.userservice.util

import java.util.regex.Pattern

object ValidationUtils {

    private val EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    )

    private val PHONE_PATTERN = Pattern.compile(
        "^[\\+]?[1-9]?[0-9]{7,15}$"
    )

    private val PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    )

    fun isValidEmail(email: String?): Boolean {
        return email != null && EMAIL_PATTERN.matcher(email).matches()
    }

    fun isValidPhone(phone: String?): Boolean {
        return phone != null && PHONE_PATTERN.matcher(phone).matches()
    }

    fun isStrongPassword(password: String?): Boolean {
        return password != null && PASSWORD_PATTERN.matcher(password).matches()
    }

    fun sanitizeString(input: String?): String? {
        return input?.trim()?.takeIf { it.isNotBlank() }
    }

    fun isValidPostalCode(postalCode: String?, country: String): Boolean {
        if (postalCode.isNullOrBlank()) return false

        return when (country.uppercase()) {
            "US" -> postalCode.matches(Regex("^\\d{5}(-\\d{4})?$"))
            "CA" -> postalCode.matches(Regex("^[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d$"))
            "UK", "GB" -> postalCode.matches(Regex("^[A-Za-z]{1,2}\\d[A-Za-z\\d]? \\d[A-Za-z]{2}$"))
            "IN" -> postalCode.matches(Regex("^\\d{6}$"))
            else -> postalCode.length in 3..10 // Generic validation
        }
    }
}