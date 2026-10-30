package com.example.bc23.core.security

/**
 * Strict legal & financial restrictions enforcement guard.
 * As mandated by regulatory compliance and initial Phase 01 requirements:
 * - Real money transactions and wagering are strictly disabled.
 * - Demo mode is enforced system-wide.
 * - Client wallet balances are isolated to local sandbox demo storage.
 */
object DemoWalletGuard {

    const val IS_DEMO_ENFORCED = true
    const val REAL_PAYMENTS_ENABLED = false

    fun assertDemoModeAllowed(): Boolean {
        if (!IS_DEMO_ENFORCED || REAL_PAYMENTS_ENABLED) {
            throw SecurityException("Real-money operations are strictly blocked until verified regulatory licensing.")
        }
        return true
    }

    fun getRegulatoryNotice(): String {
        return "REGULATORY SAFEGUARD: BC23 Game 58.com Prototype is running in Sandboxed Demonstration Mode. " +
                "Real payment processing, actual cash deposits, withdrawals, and cash wagering are disabled."
    }

    fun sanitizeDemoTransactionAmount(amount: Double): Double {
        assertDemoModeAllowed()
        return amount.coerceIn(1.0, 100000.0)
    }
}
