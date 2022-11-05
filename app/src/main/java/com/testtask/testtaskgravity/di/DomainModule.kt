package com.testtask.testtaskgravity.di

import com.testtask.testtaskgravity.domain.usecases.GetDefaultLinks
import com.testtask.testtaskgravity.domain.usecases.GetUser
import com.testtask.testtaskgravity.domain.usecases.SaveUser
import org.koin.dsl.module

val domainModule = module {

    single {
        GetDefaultLinks(
            repository = get()
        )
    }

    single {
        GetUser(
            repository = get()
        )
    }

    single {
        SaveUser(
            repository = get()
        )
    }
}