package com.me.nextcrmdependencyinjection.network.remote.auth

data class AuthSipRequest(
    val userName: String,
    val passWord: String,
    val domain: String,
    val transportType: String
)