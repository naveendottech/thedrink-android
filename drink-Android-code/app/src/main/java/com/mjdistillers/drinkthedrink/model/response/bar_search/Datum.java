
package com.mjdistillers.drinkthedrink.model.response.bar_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("bar_name")
    @Expose
    private String barName = "";
    @SerializedName("bar_tags")
    @Expose
    private String barTags = "";
    @SerializedName("vibes")
    @Expose
    private String vibes = "";
    @SerializedName("place_type")
    @Expose
    private String placeType = "";

    @SerializedName("distance")
    @Expose
    private Double distance= 0.0;

    @SerializedName("bar_image")
    @Expose
    private String bar_image = "";

    public String getBar_image() {
        return bar_image;
    }

    public void setBar_image(String bar_image) {
        this.bar_image = bar_image;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getBarTags() {
        return barTags;
    }

    public void setBarTags(String barTags) {
        this.barTags = barTags;
    }

    public String getVibes() {
        return vibes;
    }

    public void setVibes(String vibes) {
        this.vibes = vibes;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
