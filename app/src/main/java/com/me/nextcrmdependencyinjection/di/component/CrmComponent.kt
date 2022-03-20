package com.me.nextcrmdependencyinjection.di.component

import android.app.Application
import com.me.nextcrmdependencyinjection.di.module.ApplicationModule
import com.me.nextcrmdependencyinjection.di.module.NetWorkModule
import com.me.nextcrmdependencyinjection.ui.MainActivity
import com.me.nextcrmdependencyinjection.ui.login.LoginActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetWorkModule::class, ApplicationModule::class]
)
interface CrmComponent {

//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun build(): CrmComponent
//
//
//    }


    fun inject(activity: LoginActivity)
    fun inject(activity: MainActivity)
}