package com.example.bc23.core.common

object Constants {
    const val APP_NAME = "BC23 Game 58.com"
    const val APP_DOMAIN = "58.com"
    const val PLATFORM_TAG = "BC23_ANDROID"

    // Demo Financial Guardrails
    const val DEMO_INITIAL_BALANCE = 12580.00
    const val DEMO_CURRENCY = "USD"
    const val DEMO_CURRENCY_SYMBOL = "$"
    const val DEMO_MODE_NOTICE = "DEMO SIMULATION ONLY — No real money, deposits, or withdrawals are enabled."

    // VIP Constants
    const val DEFAULT_VIP_LEVEL = 3
    const val DEFAULT_VIP_POINTS = 4500
    const val DEFAULT_VIP_NEXT_TIER_POINTS = 10000

    // DataStore Preferences
    const val PREFS_NAME = "bc23_secure_preferences"
    const val KEY_AUTH_TOKEN = "auth_token"
    const val KEY_USER_ID = "user_id"
    const val KEY_USERNAME = "username"
    const val KEY_USER_UID = "user_uid"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"
    const val KEY_SOUND_EFFECTS = "sound_effects"
    const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"

    // Network & Endpoints
    const val NETWORK_TIMEOUT_SECONDS = 30L
    const val DB_NAME = "bc23_game_master.db"
}
