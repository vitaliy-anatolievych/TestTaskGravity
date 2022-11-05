package com.testtask.testtaskgravity.di

import com.testtask.testtaskgravity.presentation.ui.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        MainViewModel(
            getDefaultLinksUseCase = get(),
            getUserUseCase = get(),
            saveUserUseCase = get()
        )
    }
}
