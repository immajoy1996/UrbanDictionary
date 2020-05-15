package com.example.urbandic.api

import com.example.urbandic.data.DictionaryTermResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanDicEndpoints {
    @GET(DEFINE)
    fun getDefinition(@Query("term") term: String): Flowable<DictionaryTermResponse>

    companion object {
        private const val DEFINE = "/define"
    }
}