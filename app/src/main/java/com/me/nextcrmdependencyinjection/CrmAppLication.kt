package com.me.nextcrmdependencyinjection

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.me.nextcrmdependencyinjection.di.component.CrmComponent
import com.me.nextcrmdependencyinjection.di.component.DaggerCrmComponent
import com.me.nextcrmdependencyinjection.di.module.ApplicationModule
import com.me.nextcrmdependencyinjection.di.module.NetWorkModule


class CrmAppLication : Application(), LifecycleObserver {

    lateinit var component: CrmComponent

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        setupLifecycleListener()

        component = DaggerCrmComponent.builder()
            .applicationModule(ApplicationModule(this))
            .netWorkModule(NetWorkModule())
            .build()
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