package com.mjdistillers.drinkthedrink.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetBarsDataResponseData {



    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("user_id")
    @Expose
    var userId: Int = 0
    @SerializedName("bar_name")
    @Expose
    var barName: String  = ""
    @SerializedName("bar_street_address")
    @Expose
    var barStreetAddress: String  = ""
    @SerializedName("bar_city")
    @Expose
    var barCity: String  = ""
    @SerializedName("bar_state")
    @Expose
    var barState: String = ""
    @SerializedName("bar_country")
    @Expose
    var barCountry: String = ""
    @SerializedName("bar_zipcode")
    @Expose
    var barZipcode: Int = 0
    @SerializedName("bar_latitude")
    @Expose
    var barLatitude: String = ""
    @SerializedName("bar_longitude")
    @Expose
    var barLongitude: String = ""
    @SerializedName("bar_image")
    @Expose
    var barImage: String = ""
    @SerializedName("distance")
    @Expose
    var distance: Double = 0.0
    @SerializedName("vibes")
    @Expose
    var vibes: String = ""
    @SerializedName("product_types")
    @Expose
    var productTypes: Any = Any()
    @SerializedName("order")
    @Expose
    var order: Int = 0
    @SerializedName("busiest_day")
    @Expose
    var busiestDay: Int = 0
    @SerializedName("bar_marker")
    @Expose
    var barMarker: String = ""
    @SerializedName("current_usa_date_time")
    @Expose
    var currentUsaDateTime: String = ""
    @SerializedName("special_drink_price")
    @Expose
    var specialDrinkPrice: String = ""
    @SerializedName("current_date_time")
    @Expose
    var currentDateTime: String = ""
    @SerializedName("events_detail")
    @Expose
    var eventsDetail: String = ""
    @SerializedName("today_event")
    @Expose
    var todayEvent: String = ""
    @SerializedName("events_dollar")
    @Expose
    var eventsDollar: String = ""
    @SerializedName("all_null")
    @Expose
    var allNull: Boolean = false

    @SerializedName("dollars")
    @Expose
    var dollars:Int = 0

    @SerializedName("bar_team")
    @Expose
    val barTeam: List<BarTeam>? = null


}