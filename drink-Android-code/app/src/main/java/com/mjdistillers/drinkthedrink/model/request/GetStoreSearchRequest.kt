package com.mjdistillers.drinkthedrink.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetStoreSearchRequest{
    @SerializedName("store_latitude")
    @Expose
    var barLatitude: Double? = 0.0
    @SerializedName("store_longitude")
    @Expose
    var barLongitude: Double? = 0.0

    @SerializedName("query")
    @Expose
    var query: String? = ""

}