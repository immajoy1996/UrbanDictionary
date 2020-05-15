package com.example.urbandic.di

import com.example.urbandic.ui.DictionaryActivity
import dagger.Component

@AppScope
@Component(
    modules = [
        BaseModule::class,
        ContextModule::class
    ]
)

interface AppComponent {
    fun inject(dictionaryActivity: DictionaryActivity)
}