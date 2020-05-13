package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStoresRequest {

    @SerializedName("store_latitude")
    @Expose
    Double store_latitude;

    @SerializedName("store_longitude")
    @Expose
    Double store_longitude = null;

    @SerializedName("tastings")
    @Expose
    String tastings;

    @SerializedName("delivery")
    @Expose
    String delivery = null;

    public Double getStore_latitude() {
        return store_latitude;
    }

    public void setStore_latitude(Double store_latitude) {
        this.store_latitude = store_latitude;
    }

    public Double getStore_longitude() {
        return store_longitude;
    }

    public void setStore_longitude(Double store_longitude) {
        this.store_longitude = store_longitude;
    }

    public String getTastings() {
        return tastings;
    }

    public void setTastings(String tastings) {
        this.tastings = tastings;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
