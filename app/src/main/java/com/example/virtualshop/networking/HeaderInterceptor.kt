package com.example.virtualshop.networking

import com.example.virtualshop.localstorage.GlobalAppication
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("Authorization", GlobalAppication.prefs.getJWT())
                .build()

        return chain.proceed(request)
    }
}