
package com.mjdistillers.drinkthedrink.model.response.store_detail;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("miles")
    @Expose
    private String miles = "";
    @SerializedName("store_image")
    @Expose
    private String storeImage = "";
    @SerializedName("user_id")
    @Expose
    private Integer userId = 0;
    @SerializedName("store_latitude")
    @Expose
    private String storeLatitude = "";
    @SerializedName("store_longitude")
    @Expose
    private String storeLongitude = "";
    @SerializedName("store_name")
    @Expose
    private String storeName = "";
    @SerializedName("store_desc")
    @Expose
    private String storeDesc = "";
    @SerializedName("store_street_address")
    @Expose
    private String storeStreetAddress = "";
    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges = "";
    @SerializedName("delivery_comment")
    @Expose
    private String deliveryComment = "";


    @SerializedName("store_follow")
    @Expose
    private boolean store_follow;


    @SerializedName("store_website")
    @Expose
    private String store_website;


    @SerializedName("owner_phone")
    @Expose
    private String owner_phone;

    @SerializedName("store_timing")
    @Expose
    private String store_timing;


    @SerializedName("features")
    @Expose
    private List<String> features = new ArrayList<>();
    @SerializedName("store_gallery")
    @Expose
    private List<String> storeGallery = new ArrayList<>();
    @SerializedName("todayEvent")
    @Expose
    private List<TodayEvent> todayEvent = new ArrayList<>();
    @SerializedName("comingEvents")
    @Expose
    private List<ComingEvent> comingEvents = new ArrayList<>();

    @SerializedName("store_team")
    @Expose
    private List<StoreTeam> storeTeam = null;

    public boolean isStore_follow() {
        return store_follow;
    }

    public String getStore_website() {
        return store_website;
    }

    public void setStore_website(String store_website) {
        this.store_website = store_website;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getStore_timing() {
        return store_timing;
    }

    public void setStore_timing(String store_timing) {
        this.store_timing = store_timing;
    }



    public boolean getStore_follow() {
        return store_follow;
    }

    public void setStore_follow(boolean store_follow) {
        this.store_follow = store_follow;
    }


    public List<StoreTeam> getStoreTeam() {
        return storeTeam;
    }

    public void setStoreTeam(List<StoreTeam> storeTeam) {
        this.storeTeam = storeTeam;
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

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(String storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public String getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(String storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getStoreStreetAddress() {
        return storeStreetAddress;
    }

    public void setStoreStreetAddress(String storeStreetAddress) {
        this.storeStreetAddress = storeStreetAddress;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getDeliveryComment() {
        return deliveryComment;
    }

    public void setDeliveryComment(String deliveryComment) {
        this.deliveryComment = deliveryComment;
    }


    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public List<String> getStoreGallery() {
        return storeGallery;
    }

    public void setStoreGallery(List<String> storeGallery) {
        this.storeGallery = storeGallery;
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

}
