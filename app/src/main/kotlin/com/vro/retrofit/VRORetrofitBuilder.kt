package com.vro.retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vro.retrofit.VROHttpClient.Companion.emptyTrustManager
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class VRORetrofitBuilder {

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
    private var baseUrl: String = DEFAULT_BASE_URL
    private var ignoreSSL: Boolean = true
    private var interceptors: List<Interceptor> = listOf()

    fun createRetrofit(token: String? = null, url: String) =
        httpLoggingInterceptorLevel(loggingInterceptorLevel()).interceptors(addInterceptors(token)).baseUrl(url).build()

    abstract fun addInterceptors(token: String?): List<Interceptor>

    abstract fun loggingInterceptorLevel(): HttpLoggingInterceptor.Level

    private fun baseUrl(url: String) = also { builder ->
        builder.baseUrl = url
    }

    private fun httpLoggingInterceptorLevel(level: HttpLoggingInterceptor.Level) = also { builder ->
        builder.httpLoggingInterceptor.level = level
    }

    private fun interceptors(interceptors: List<Interceptor>) = also { builder ->
        builder.interceptors = interceptors
    }

    private fun build(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(createGsonConverterFactory())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .client(createHttpClient())
        .build()

    private fun createGsonConverterFactory() = GsonConverterFactory.create(
        GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .serializeNulls()
            .create()
    )

    private fun createHttpClient() = VROHttpClient.Builder()
        .ignoreSSL(ignoreSSL)
        .interceptors(interceptors + httpLoggingInterceptor)
        .trustManagers(listOf(emptyTrustManager))
        .build()

    companion object {
        private const val DEFAULT_BASE_URL = ""
    }
}
