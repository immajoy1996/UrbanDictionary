package com.example.urbandic.ui

import android.app.Application
import com.example.urbandic.di.AppComponentHolder
import com.example.urbandic.di.ContextModule
import com.example.urbandic.di.DaggerAppComponent

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setDaggerComponent()
    }

    private fun setDaggerComponent() {
        AppComponentHolder.component =
            DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()
    }
}