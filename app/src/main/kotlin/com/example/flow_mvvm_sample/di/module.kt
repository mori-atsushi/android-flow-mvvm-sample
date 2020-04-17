package com.example.flow_mvvm_sample.di

import com.example.flow_mvvm_sample.data.api.GithubApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val module = module {
    // data/api
    single {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        MoshiConverterFactory.create()
    }
    single {
        Retrofit.Builder()
            .baseUrl(GithubApi.HTTPS_API_GITHUB_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single {
        get<Retrofit>().create(GithubApi::class.java)
    }
}
