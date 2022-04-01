package com.me.nextcrmdependencyinjection.data.network.remote.auth

import com.me.nextcrmdependencyinjection.data.model.auth.AuthRequest
import com.me.nextcrmdependencyinjection.data.model.auth.AuthResponse
import com.me.nextcrmdependencyinjection.data.network.common.DataResponse


interface AuthHelper {

    suspend fun login(authRequest: AuthRequest): DataResponse<AuthResponse?>?
}