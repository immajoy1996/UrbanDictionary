package com.example.urbandic.di

import android.content.Context
import androidx.room.Room
import com.example.urbandic.api.UrbanDicApi
import com.example.urbandic.api.UrbanDicApiInterface
import com.example.urbandic.room.WordDatabase
import com.example.urbandic.room.WordItemDao
import com.example.urbandic.viewmodel.*
import dagger.Module
import dagger.Provides

@Module
class BaseModule {

    @Provides
    @AppScope
    internal fun provideBaseRepository(urbanDicApiInterface: UrbanDicApiInterface): BaseRepositoryInterface {
        return BaseRepository(urbanDicApiInterface)
    }

    @Provides
    @AppScope
    internal fun provideDictionaryViewModel(baseRepository: BaseRepositoryInterface): DictionaryViewModelInterface {
        return DictionaryViewModel(
            baseRepository
        )
    }

    @Provides
    @AppScope
    internal fun provideUrbanDicApi(): UrbanDicApiInterface {
        return UrbanDicApi()
    }

    @Provides
    @AppScope
    internal fun providesWordDatabase(context: Context): WordDatabase {
        return Room.databaseBuilder(context, WordDatabase::class.java, "word_database").build()
    }

    @Provides
    @AppScope
    internal fun providesWordItemDao(database: WordDatabase): WordItemDao {
        return database.wordItemDao()
    }
}