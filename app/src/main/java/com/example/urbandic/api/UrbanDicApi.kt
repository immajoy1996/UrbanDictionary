package com.example.urbandic.api

import com.example.urbandic.data.DictionaryTermResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

interface UrbanDicApiInterface {
    fun getDefinition(term: String): Flowable<DictionaryTermResponse>
}

class UrbanDicApi : UrbanDicApiInterface {

    override fun getDefinition(term: String): Flowable<DictionaryTermResponse> {
        return getUrbanDicApi().getDefinition(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        private const val BASE_URL = "https://mashape-community-urban-dictionary.p.rapidapi.com"
        private const val RAPID_API_HOST_KEY = "x-rapidapi-host"
        private const val RAPID_API_HOST_VALUE = "mashape-community-urban-dictionary.p.rapidapi.com"
        private const val RAPID_API_KEY = "x-rapidapi-key"
        private const val RAPID_API_VALUE = "e11daaf4eemsh1aeb2879d7f4a2ep18720fjsn84b451927ac0"

        private var client: OkHttpClient? = null

        fun getUrbanDicApi(): UrbanDicEndpoints {
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

            //important line
            httpClient.addInterceptor(logging)
            httpClient.addNetworkInterceptor(MyOkHttpInterceptor())

            client = httpClient.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                .client(client)
                .build().create(UrbanDicEndpoints::class.java)
        }

        class MyOkHttpInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header(RAPID_API_HOST_KEY, RAPID_API_HOST_VALUE)
                    .header(RAPID_API_KEY, RAPID_API_VALUE)
                    .build()
                return chain.proceed(newRequest)
            }
        }

        private val gsonInstance: Gson
            get() = GsonBuilder().create()
    }
}