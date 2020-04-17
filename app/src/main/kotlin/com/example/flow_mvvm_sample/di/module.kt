package com.example.flow_mvvm_sample.di

import com.example.flow_mvvm_sample.data.api.GithubApi
import com.example.flow_mvvm_sample.data.repository.RepoRepository
import com.example.flow_mvvm_sample.data.repository.RepoRepositoryImpl
import com.example.flow_mvvm_sample.ui.top.TopViewModel
import com.example.flow_mvvm_sample.util.DateJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

val module = module {
    // ui
    viewModel { TopViewModel(get()) }

    // data/repository
    single<RepoRepository> { RepoRepositoryImpl(get()) }

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
        Moshi.Builder()
            .add(Date::class.java, DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(GithubApi.HTTPS_API_GITHUB_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single {
        get<Retrofit>().create(GithubApi::class.java)
    }
}
