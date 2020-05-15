package com.example.urbandic.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    @AppScope
    internal fun provideContext() = context
}