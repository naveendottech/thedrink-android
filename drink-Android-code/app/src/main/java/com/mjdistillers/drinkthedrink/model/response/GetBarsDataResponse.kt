package com.mjdistillers.drinkthedrink.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class GetBarsDataResponse {
    @SerializedName("status")
    @Expose
    var status = false
    @SerializedName("data")
    @Expose
    var data: List<GetBarsDataResponseData> = ArrayList()

}