package com.mjdistillers.drinkthedrink.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetBarsDataRequest {
    @SerializedName("bar_latitude")
    @Expose
    var barLatitude: Double? = null
    @SerializedName("bar_longitude")
    @Expose
    var barLongitude: Double? = null
    @SerializedName("drink_special")
    @Expose
    var drinkSpecial: String? = null
    @SerializedName("events")
    @Expose
    var events: String? = null

    @SerializedName("drinks_only")
    @Expose
    var drinks_only=""

    @SerializedName("night_club")
    @Expose
    var night_club=""

    @SerializedName("theme_bar")
    @Expose
    var theme_bar=""

    @SerializedName("restaurant")
    @Expose
    var restaurant=""

    @SerializedName("patio")
    @Expose
    var patio=""

}