package com.vro.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class VROHttpClient {

    class Builder(
        private var ignoreSSL: Boolean = true,
        private var interceptors: MutableList<Interceptor> = mutableListOf(),
        private var trustManagers: MutableList<TrustManager> = mutableListOf(),
    ) {
        fun ignoreSSL(ignoreSSL: Boolean) = apply { this.ignoreSSL = ignoreSSL }

        fun interceptors(interceptors: List<Interceptor>) = apply {
            this.interceptors.addAll(interceptors)
        }

        fun trustManagers(trustManagers: List<TrustManager>) = apply {
            this.trustManagers.addAll(trustManagers)
        }

        fun build(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)

            interceptors.forEach { interceptor ->
                builder.addInterceptor(interceptor)
            }

            val sslContext = SSLContext.getInstance(SSL_PROTOCOL)
            sslContext.init(null, trustManagers.toTypedArray(), SecureRandom())

            if (ignoreSSL) {
                builder
                    .sslSocketFactory(
                        sslContext.socketFactory,
                        emptyTrustManager
                    )
                    .hostnameVerifier { _, _ -> true }
            } else {
                if (trustManagers.isNotEmpty()) {
                    builder.sslSocketFactory(
                        sslContext.socketFactory,
                        trustManagers.first() as X509TrustManager
                    )
                }
            }

            return builder.build()
        }
    }

    companion object {
        const val CONNECT_TIMEOUT_SECONDS = 60L
        const val READ_TIMEOUT_SECONDS = 60L
        const val WRITE_TIMEOUT_SECONDS = 60L
        const val SSL_PROTOCOL = "SSL"

        val emptyTrustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                // Nothing to do
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                // Nothing to do
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    }
}
