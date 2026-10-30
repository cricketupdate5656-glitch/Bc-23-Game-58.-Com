package com.example.bc23.core.security

import java.security.MessageDigest
import java.util.Locale
import java.util.UUID

object SecurityUtils {

    /**
     * Hashes plain text using SHA-256 for secure credential comparison.
     * Never stores raw passwords in plain text.
     */
    fun hashSha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Generates a random session token for mock/demo API authentication.
     */
    fun generateSecureToken(): String {
        return "bc23_tok_" + UUID.randomUUID().toString().replace("-", "")
    }

    /**
     * Masks sensitive user identifiers for UI display (e.g., 58921473 -> 5892***3).
     */
    fun maskUid(uid: String): String {
        if (uid.length <= 4) return uid
        val prefix = uid.take(4)
        val suffix = uid.takeLast(1)
        val masked = "*".repeat((uid.length - 5).coerceAtLeast(2))
        return "$prefix$masked$suffix"
    }

    /**
     * Formats currency amounts safely for UI.
     */
    fun formatCurrency(amount: Double, symbol: String = "$"): String {
        return String.format(Locale.US, "%s%,.2f", symbol, amount)
    }
}
