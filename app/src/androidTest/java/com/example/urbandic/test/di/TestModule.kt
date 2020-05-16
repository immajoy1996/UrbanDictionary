package com.example.urbandic.test.di

import androidx.test.platform.app.InstrumentationRegistry
import com.example.urbandic.di.AppScope
import com.example.urbandic.room.WordItemDao
import com.example.urbandic.viewmodel.DictionaryViewModelInterface
import dagger.Module
import dagger.Provides
import io.mockk.mockk

@Module
class TestModule {

    @Provides
    @AppScope
    internal fun providesContext() = InstrumentationRegistry.getInstrumentation().targetContext

    @Provides
    @AppScope
    internal fun providesDictionaryViewModel(): DictionaryViewModelInterface = mockk(relaxed = true)

    @Provides
    @AppScope
    internal fun providesWordItemDao(): WordItemDao = mockk(relaxed = true)
}