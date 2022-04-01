package com.me.nextcrmdependencyinjection.data.network.remote.auth

import com.me.nextcrmdependencyinjection.data.model.auth.AuthRequest
import com.me.nextcrmdependencyinjection.data.model.auth.AuthResponse
import com.me.nextcrmdependencyinjection.data.network.common.DataResponse

class AuthApiHelperImpl(private val authApiService: AuthApiService) : AuthHelper {
    override suspend fun login(authRequest: AuthRequest): DataResponse<AuthResponse?>? {
        return authApiService.login(authRequest)
    }


}