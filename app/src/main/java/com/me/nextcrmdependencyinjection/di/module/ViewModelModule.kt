package com.me.nextcrmdependencyinjection.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.me.nextcrmdependencyinjection.di.anotation.ViewModelKey
import com.me.nextcrmdependencyinjection.network.remote.auth.AuthApi
import com.me.nextcrmdependencyinjection.ui.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

//@Suppress("unused")
//@Module
//abstract class ViewModelModule {
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(LoginViewModel::class)
//    abstract fun postLoginViewModel(authApi: AuthApi): ViewModel
//
//    @Binds
//    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//
//}