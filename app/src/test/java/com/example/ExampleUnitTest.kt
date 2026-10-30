package com.example

import com.example.bc23.core.security.DemoWalletGuard
import com.example.bc23.core.security.InputValidator
import com.example.bc23.core.security.SecurityUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testSha256Hashing_deterministic() {
        val hash1 = SecurityUtils.hashSha256("bc23demo123")
        val hash2 = SecurityUtils.hashSha256("bc23demo123")
        assertEquals(hash1, hash2)
        assertEquals(64, hash1.length)
    }

    @Test
    fun testUidMasking() {
        val masked = SecurityUtils.maskUid("58921473")
        assertEquals("5892***3", masked)
    }

    @Test
    fun testInputValidator_validations() {
        assertTrue(InputValidator.validateUsername("arshad_lucky").isValid)
        assertFalse(InputValidator.validateUsername("ab").isValid) // too short
        assertFalse(InputValidator.validateUsername("").isValid)

        assertTrue(InputValidator.validatePassword("bc23demo123").isValid)
        assertFalse(InputValidator.validatePassword("123").isValid) // too short

        assertTrue(InputValidator.validateDemoAmount("100.0").isValid)
        assertFalse(InputValidator.validateDemoAmount("0.5").isValid) // below minimum
        assertFalse(InputValidator.validateDemoAmount("invalid").isValid)
    }

    @Test
    fun testDemoWalletGuard_enforcesSanitization() {
        assertTrue(DemoWalletGuard.assertDemoModeAllowed())
        val sanitized = DemoWalletGuard.sanitizeDemoTransactionAmount(500.0)
        assertEquals(500.0, sanitized, 0.001)
    }
}
