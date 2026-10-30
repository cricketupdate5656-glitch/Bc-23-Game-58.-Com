package com.example.bc23

import android.app.Application
import com.example.bc23.di.AppContainer
import com.example.bc23.di.DefaultAppContainer

class BC23Application : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer(this)
    }
}
