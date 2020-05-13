package com.mjdistillers.drinkthedrink.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetSearchBarsRequest {
    @SerializedName("bar_latitude")
    @Expose
    var barLatitude: Double? = 0.0

    @SerializedName("bar_longitude")
    @Expose
    var barLongitude: Double? = 0.0

    @SerializedName("query")
    @Expose
    var query: String? = ""

}