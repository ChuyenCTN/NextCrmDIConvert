package com.me.nextcrmdependencyinjection.di.module

import com.hosco.nextcrm.callcenter.common.Const
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


private fun provideOkhttpClient(): OkHttpClient {
    val interceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method, original.body)
        val request = requestBuilder.build()
        chain.proceed(request)
    }
    return if (Const.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(Const.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .readTimeout(Const.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Const.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .build()
    } else {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(Const.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .readTimeout(Const.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Const.REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .build()
    }
}

private fun  provideRetrofit(){

}