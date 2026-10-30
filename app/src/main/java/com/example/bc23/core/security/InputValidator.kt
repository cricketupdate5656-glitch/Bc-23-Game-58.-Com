package com.example.bc23.core.security

object InputValidator {

    fun validateUsername(username: String): ValidationResult {
        val trimmed = username.trim()
        return when {
            trimmed.isEmpty() -> ValidationResult(false, "Username cannot be empty")
            trimmed.length < 3 -> ValidationResult(false, "Username must be at least 3 characters")
            trimmed.length > 20 -> ValidationResult(false, "Username cannot exceed 20 characters")
            !trimmed.matches(Regex("^[a-zA-Z0-9_]+$")) -> ValidationResult(false, "Only letters, numbers, and underscores allowed")
            else -> ValidationResult(true)
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult(false, "Password cannot be empty")
            password.length < 6 -> ValidationResult(false, "Password must be at least 6 characters")
            else -> ValidationResult(true)
        }
    }

    fun validateReferralCode(code: String): ValidationResult {
        val trimmed = code.trim().uppercase()
        return when {
            trimmed.isEmpty() -> ValidationResult(true) // optional
            trimmed.length < 4 || trimmed.length > 12 -> ValidationResult(false, "Referral code must be 4-12 alphanumeric characters")
            !trimmed.matches(Regex("^[A-Z0-9]+$")) -> ValidationResult(false, "Referral code must be uppercase letters and numbers")
            else -> ValidationResult(true)
        }
    }

    fun validateDemoAmount(amountStr: String, minAmount: Double = 10.0, maxAmount: Double = 50000.0): ValidationResult {
        val amount = amountStr.toDoubleOrNull()
            ?: return ValidationResult(false, "Please enter a valid numeric amount")
        return when {
            amount < minAmount -> ValidationResult(false, "Minimum demo amount is $${minAmount.toInt()}")
            amount > maxAmount -> ValidationResult(false, "Maximum demo amount is $${maxAmount.toInt()}")
            else -> ValidationResult(true)
        }
    }

    data class ValidationResult(val isValid: Boolean, val errorMessage: String? = null)
}
