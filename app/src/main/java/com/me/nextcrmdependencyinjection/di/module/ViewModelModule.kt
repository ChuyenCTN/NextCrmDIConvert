package com.me.nextcrmdependencyinjection.di.module

import com.me.nextcrmdependencyinjection.base.BaseViewModel
import com.me.nextcrmdependencyinjection.ui.login.AuthViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        AuthViewModel(get(), get(), get())
    }
}