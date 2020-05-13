package com.mjdistillers.drinkthedrink.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateRoleRequest {
    @SerializedName("role")
    @Expose
    var role = 0
    @SerializedName("user_id")
    @Expose
    var user_id = 0
    @SerializedName("fav_alcohol")
    @Expose
    var fav_alcohol: String? = null
    @SerializedName("alcohol_online")
    @Expose
    var alcohol_online: String? = null
    @SerializedName("speciality")
    @Expose
    var speciality: String? = null
    @SerializedName("outing_day")
    @Expose
    var outing_day: String? = null
}