package com.example.recipestorepro.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ContentTypeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Content-type", "application/jason")
            .build()
        return chain.proceed(newRequest)
    }
}