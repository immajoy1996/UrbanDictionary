package com.example.urbandic.test.di

import com.example.urbandic.DictionaryScreenUiTest
import com.example.urbandic.di.AppComponent
import com.example.urbandic.di.AppScope
import dagger.Component

@AppScope
@Component(
    modules = [
        TestModule::class
    ]
)

interface TestComponent : AppComponent {
    fun inject(dictionaryScreenUiTest: DictionaryScreenUiTest)
}