package com.app.helpua.app

import android.app.Application
import com.app.helpua.app.di.viewModelModule
import com.app.helpua.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            modules(dataModule, viewModelModule)
        }
    }
}