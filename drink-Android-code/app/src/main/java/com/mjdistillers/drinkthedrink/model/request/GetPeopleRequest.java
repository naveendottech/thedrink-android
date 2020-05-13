package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPeopleRequest {

    @SerializedName("user_id")
    @Expose
    int user_id;

    @SerializedName("latitude")
    @Expose
    Double latitude = 0.0;

    @SerializedName("longitude")
    @Expose
    Double longitude = 0.0;

    @SerializedName("privacy")
    @Expose
    String privacy = "";

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
