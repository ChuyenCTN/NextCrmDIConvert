package com.me.nextcrmdependencyinjection.di.component

import com.me.nextcrmdependencyinjection.di.module.ActivityModule
import dagger.Component

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
}