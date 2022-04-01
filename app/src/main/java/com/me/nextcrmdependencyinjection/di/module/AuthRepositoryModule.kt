package com.me.nextcrmdependencyinjection.di.module

import com.me.nextcrmdependencyinjection.data.repository.AuthRepository
import org.koin.dsl.module

val auRepoModule = module {
    single {
        AuthRepository(
            get()
        )
    }
}
