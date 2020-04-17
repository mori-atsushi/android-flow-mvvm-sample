package com.example.flow_mvvm_sample

import android.app.Application
import com.example.flow_mvvm_sample.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(module)
        }
    }
}