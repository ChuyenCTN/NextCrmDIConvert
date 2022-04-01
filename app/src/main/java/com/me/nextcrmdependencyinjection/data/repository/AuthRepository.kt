package com.me.nextcrmdependencyinjection.data.repository

import com.me.nextcrmdependencyinjection.data.model.auth.AuthRequest
import com.me.nextcrmdependencyinjection.data.network.remote.auth.AuthHelper

class AuthRepository(private val authHelper: AuthHelper) {
    suspend fun login(authRequest: AuthRequest) = authHelper.login(authRequest)

    suspend fun checkExistCustomer(tenantCode: String) = authHelper.checkExistCustomer(tenantCode)
}