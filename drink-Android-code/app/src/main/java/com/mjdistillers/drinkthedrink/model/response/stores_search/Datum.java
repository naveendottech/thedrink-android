
package com.mjdistillers.drinkthedrink.model.response.stores_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id = 0;

    @SerializedName("store_name")
    @Expose
    private String storeName = "";

    @SerializedName("distance")
    @Expose
    private Double distance = 0.0;

    @SerializedName("store_image")
    @Expose
    private String store_image = "";

    public String getStore_image() {
        return store_image;
    }

    public void setStore_image(String store_image) {
        this.store_image = store_image;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
