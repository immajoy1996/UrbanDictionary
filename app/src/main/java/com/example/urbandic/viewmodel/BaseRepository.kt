package com.example.urbandic.viewmodel

import com.example.urbandic.data.DictionaryTermResponse
import com.example.urbandic.api.UrbanDicApiInterface
import io.reactivex.Flowable
import javax.inject.Inject

interface BaseRepositoryInterface {
    fun getSomething(term: String): Flowable<DictionaryTermResponse>
}

class BaseRepository @Inject constructor(
    private val apiService: UrbanDicApiInterface
) : BaseRepositoryInterface {

    override fun getSomething(term: String): Flowable<DictionaryTermResponse> {
        return apiService.getDefinition(term)
    }
}