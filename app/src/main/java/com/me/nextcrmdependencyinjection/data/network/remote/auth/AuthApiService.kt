package com.me.nextcrmdependencyinjection.data.network.remote.auth

import com.me.nextcrmdependencyinjection.data.model.auth.AuthRequest
import com.me.nextcrmdependencyinjection.data.model.auth.AuthResponse
import com.me.nextcrmdependencyinjection.data.model.auth.CustommerResponse
import io.reactivex.Observable
import com.me.nextcrmdependencyinjection.data.network.common.DataResponse
import retrofit2.http.*

interface AuthApiService {

//    @POST("api/app-mobile/login")
//    fun login(@Body loginRequest: AuthRequest?): Observable<DataResponse<AuthResponse?>?>?
//
//    @GET("api/app-mobile/validate-tenant/{Ma_Khach_Hang}")
//    fun checkExistCustomer(@Path("Ma_Khach_Hang") tenantCode: String): Observable<DataResponse<CustommerResponse?>?>?

    @POST("api/app-mobile/login")
    suspend fun login(@Body loginRequest: AuthRequest):DataResponse<AuthResponse?>?
}