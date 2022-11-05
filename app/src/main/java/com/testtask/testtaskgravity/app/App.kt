package com.testtask.testtaskgravity.app

import android.app.Application
import com.testtask.testtaskgravity.di.appModule
import com.testtask.testtaskgravity.di.dataModule
import com.testtask.testtaskgravity.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(
                appModule,
                dataModule,
                domainModule
            ))
        }
    }
}