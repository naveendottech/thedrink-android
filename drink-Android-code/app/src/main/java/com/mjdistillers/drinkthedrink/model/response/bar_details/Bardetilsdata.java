
package com.mjdistillers.drinkthedrink.model.response.bar_details;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mjdistillers.drinkthedrink.adapters.RetrofitNullToEmptyString;
import com.mjdistillers.drinkthedrink.model.response.BarTeam;

public class Bardetilsdata {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("miles")
    @Expose
    private String miles = "";
    @SerializedName("dollar")
    @Expose
    private String dollar = "";
    @SerializedName("bar_image")
    @Expose
    private String barImage = "";
    @SerializedName("user_id")
    @Expose
    private Integer userId = 0;
    @SerializedName("bar_name")
    @Expose
    private String barName = "";
    @SerializedName("bar_desc")
    @Expose
    private String barDesc = "";

    @SerializedName("bar_website")
    @Expose
    private String bar_website;

    @SerializedName("owner_phone")
    @Expose
    private String owner_phone;

    @SerializedName("bar_timing")
    @Expose
    private String bar_timing;

    public String getBar_website() {
        return bar_website;
    }

    public void setBar_website(String bar_website) {
        this.bar_website = bar_website;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getBar_timing() {
        return bar_timing;
    }

    public void setBar_timing(String bar_timing) {
        this.bar_timing = bar_timing;
    }

    @SerializedName("bar_follow")
    @Expose
    private Boolean bar_follow = true;

    @SerializedName("bar_street_address")
    @Expose
    private String barStreetAddress = "";
    @SerializedName("features")
    @Expose
    private List<String> features = new ArrayList<>();
    @SerializedName("bar_gallery")
    @Expose
    private List<String> barGallery = new ArrayList<>();

    @SerializedName("todayEvent")
    @Expose
    private List<TodayEvent> todayEvent = new ArrayList<>();

    @SerializedName("comingEvents")
    @Expose
    private List<ComingEvent> comingEvents = new ArrayList<>();

    // For Stores Detail Response
    @SerializedName("delivery_charges")
    @Expose
    String delivery_charges =  "";

    @SerializedName("delivery_comment")
    @JsonAdapter(RetrofitNullToEmptyString.class)
    @Expose
    String delivery_comment = "";
    // For Stores Detail Response

    @SerializedName("bar_team")
    @Expose
    private List<BarTeam> barTeam = new ArrayList<>();

    public List<BarTeam> getBarTeam() {
        return barTeam;
    }

    public void setBarTeam(List<BarTeam> barTeam) {
        this.barTeam = barTeam;
    }

    private String store_website = "";

    public String getStore_website() {
        return store_website;
    }

    public void setStore_website(String store_website) {
        this.store_website = store_website;
    }

    public Boolean getBar_follow() {
        return bar_follow;
    }

    public void setBar_follow(Boolean bar_follow) {
        this.bar_follow = bar_follow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getDollar() {
        return dollar;
    }

    public void setDollar(String dollar) {
        this.dollar = dollar;
    }

    public String getBarImage() {
        return barImage;
    }

    public void setBarImage(String barImage) {
        this.barImage = barImage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getBarDesc() {
        return barDesc;
    }

    public void setBarDesc(String barDesc) {
        this.barDesc = barDesc;
    }

    public String getBarStreetAddress() {
        return barStreetAddress;
    }

    public void setBarStreetAddress(String barStreetAddress) {
        this.barStreetAddress = barStreetAddress;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public List<String> getBarGallery() {
        return barGallery;
    }

    public void setBarGallery(List<String> barGallery) {
        this.barGallery = barGallery;
    }

    public List<TodayEvent> getTodayEvent() {
        return todayEvent;
    }

    public void setTodayEvent(List<TodayEvent> todayEvent) {
        this.todayEvent = todayEvent;
    }

    public List<ComingEvent> getComingEvents() {
        return comingEvents;
    }

    public void setComingEvents(List<ComingEvent> comingEvents) {
        this.comingEvents = comingEvents;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(String delivery_charges) {
        this.delivery_charges = delivery_charges;
    }

    public String getDelivery_comment() {
        return delivery_comment;
    }


    public void setDelivery_comment(String delivery_comment) {
        this.delivery_comment = delivery_comment;
    }



}
