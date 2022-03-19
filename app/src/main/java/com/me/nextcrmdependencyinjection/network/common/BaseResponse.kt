package com.me.nextcrmdependencyinjection.network.common

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("meta")
    var meta: Meta? = null
)