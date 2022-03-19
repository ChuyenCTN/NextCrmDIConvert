package com.me.nextcrmdependencyinjection.network.remote.auth


interface AuthRepository {

    suspend fun login(authRequest: AuthRequest): AuthResponse
}