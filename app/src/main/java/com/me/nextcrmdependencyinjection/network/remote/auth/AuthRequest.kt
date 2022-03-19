package com.me.nextcrmdependencyinjection.network.remote.auth

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("tenant_code")
    val tenantcode: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?

)
