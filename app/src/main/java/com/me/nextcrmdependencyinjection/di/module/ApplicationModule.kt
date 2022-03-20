package com.me.nextcrmdependencyinjection.di.module

import android.app.Application
import com.me.nextcrmdependencyinjection.di.anotation.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Application = this.application
}