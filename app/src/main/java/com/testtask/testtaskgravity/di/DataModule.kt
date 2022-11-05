package com.testtask.testtaskgravity.di

import android.content.Context
import android.content.SharedPreferences
import com.testtask.testtaskgravity.BuildConfig
import com.testtask.testtaskgravity.core.network.NetworkHandler
import com.testtask.testtaskgravity.core.network.Request
import com.testtask.testtaskgravity.core.network.ServiceFactory
import com.testtask.testtaskgravity.data.AppRepositoryImpl
import com.testtask.testtaskgravity.data.local.Cache
import com.testtask.testtaskgravity.data.local.CacheImpl
import com.testtask.testtaskgravity.data.remote.Remote
import com.testtask.testtaskgravity.data.remote.RemoteImpl
import com.testtask.testtaskgravity.data.remote.service.ApiService
import com.testtask.testtaskgravity.domain.repository.AppRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<AppRepository> {
        AppRepositoryImpl(
            remote = get(),
            cache = get()
        )
    }

    single<Remote> {
        RemoteImpl(
            request = get(),
            service = get()
        )
    }

    single<Cache> {
        CacheImpl(
            mainPreferences = getMainPreference(context = androidApplication())
        )
    }

    single {
        Request(
            networkHandler = NetworkHandler(context = androidApplication())
        )
    }

    single {
        ServiceFactory.makeService(BuildConfig.DEBUG, ApiService::class.java)
    }
}

private fun getMainPreference(context: Context): SharedPreferences =
    context.getSharedPreferences(BuildConfig.APP_STORAGE, Context.MODE_PRIVATE)