package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBarDetailsRequest {

    @SerializedName("bar_id")
    @Expose
    private Integer barId = 0;

    @SerializedName("miles")
    @Expose
    private Double miles = 0.0d;

    @SerializedName("dollar")
    @Expose
    private Integer dollar = 0;

    @SerializedName("user_id")
    @Expose
    private Integer user_id = 0;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }

    public Double getMiles() {
        return miles;
    }

    public void setMiles(Double miles) {
        this.miles = miles;
    }

    public Integer getDollar() {
        return dollar;
    }

    public void setDollar(Integer dollar) {
        this.dollar = dollar;
    }
}
