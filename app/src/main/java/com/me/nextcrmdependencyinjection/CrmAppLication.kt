package com.me.nextcrmdependencyinjection

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.me.nextcrmdependencyinjection.di.module.authModule
import com.me.nextcrmdependencyinjection.di.module.authRepoModule
import com.me.nextcrmdependencyinjection.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CrmAppLication : Application(), LifecycleObserver {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        startKoin {
            androidContext(this@CrmAppLication)
            modules(listOf(authModule, authRepoModule, viewModelModule))
        }

        setupLifecycleListener()
    }


    private fun setupLifecycleListener() {
        ProcessLifecycleOwner.get().lifecycle
            .addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        android.util.Log.d("My_Lifecycle", "Returning to foreground…")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        android.util.Log.d("My_Lifecycle", "Moving to background…")
    }


}