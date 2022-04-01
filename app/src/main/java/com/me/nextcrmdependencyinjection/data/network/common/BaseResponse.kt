package com.me.nextcrmdependencyinjection.data.network.common

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("meta")
    var meta: Meta? = null
)