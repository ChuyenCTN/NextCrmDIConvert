package com.me.nextcrmdependencyinjection.di.module

import android.app.Activity
import android.content.Context
import com.me.nextcrmdependencyinjection.di.anotation.ApplicationContext
import com.me.nextcrmdependencyinjection.ui.login.LoginActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context = activity

}