package com.me.nextcrmdependencyinjection.di.module

import android.content.Context
import com.google.gson.GsonBuilder
import com.hosco.nextcrm.callcenter.common.Const
import com.me.nextcrmdependencyinjection.BuildConfig
import com.me.nextcrmdependencyinjection.data.network.remote.auth.AuthApiHelperImpl
import com.me.nextcrmdependencyinjection.data.network.remote.auth.AuthApiService
import com.me.nextcrmdependencyinjection.data.network.remote.auth.AuthHelper
import com.me.nextcrmdependencyinjection.utils.NetworkHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val authModule = module {
    single { provideOkhttpClient() }
    single { provideRetrofit(get()) }
    single { provideNetworkHelper(get()) }
    single { provideAuthApiService(get()) }

    single<AuthHelper> {
        return@single AuthApiHelperImpl(get())
    }

}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)


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

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()
}

fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
    retrofit.create(AuthApiService::class.java)