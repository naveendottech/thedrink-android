package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStoresDetailRequest {

    @SerializedName("store_id")
    @Expose
    public Integer store_id = 0;

    @SerializedName("miles")
    @Expose
    public Double miles = 0.0d;

    @SerializedName("user_id")
    @Expose
    private Integer user_id = 0;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public long getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public Double getMiles() {
        return miles;
    }

    public void setMiles(Double miles) {
        this.miles = miles;
    }
}
