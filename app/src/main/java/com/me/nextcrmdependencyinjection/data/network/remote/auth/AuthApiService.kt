package com.me.nextcrmdependencyinjection.data.network.remote.auth

import com.me.nextcrmdependencyinjection.data.model.auth.AuthRequest
import com.me.nextcrmdependencyinjection.data.model.auth.AuthResponse
import com.me.nextcrmdependencyinjection.data.model.auth.CustommerResponse
import com.me.nextcrmdependencyinjection.data.network.common.DataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {

    @POST("api/app-mobile/login")
    suspend fun login(@Body loginRequest: AuthRequest): DataResponse<AuthResponse?>?

    @GET("api/app-mobile/validate-tenant/{Ma_Khach_Hang}")
    suspend fun checkExistCustomer(@Path("Ma_Khach_Hang") tenantCode: String): DataResponse<CustommerResponse?>?
}