package com.me.nextcrmdependencyinjection.network.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class DataResponse<T> : BaseResponse() {
    @Expose
    @SerializedName("data")
     val data: T? = null
}