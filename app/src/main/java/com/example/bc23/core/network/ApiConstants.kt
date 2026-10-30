package com.example.bc23.core.network

object ApiConstants {
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_CLIENT_VERSION = "X-BC23-Client-Version"
    const val HEADER_DEVICE_PLATFORM = "X-BC23-Platform"
    const val HEADER_DEMO_MODE = "X-BC23-Demo-Mode"

    const val PLATFORM_ANDROID = "android"
    const val CURRENT_API_VERSION = "v1"

    const val TIMEOUT_CONNECT = 15L
    const val TIMEOUT_READ = 20L
    const val TIMEOUT_WRITE = 20L
}
