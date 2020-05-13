
package com.mjdistillers.drinkthedrink.model.response.get_stores;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("user_id")
    @Expose
    private Integer userId = 0;
    @SerializedName("store_name")
    @Expose
    private String storeName = "";
    @SerializedName("store_desc")
    @Expose
    private String storeDesc = "";
    @SerializedName("store_street_address")
    @Expose
    private String storeStreetAddress = "";
    @SerializedName("store_city")
    @Expose
    private String storeCity = "";
    @SerializedName("store_state")
    @Expose
    private String storeState = "";
    @SerializedName("store_country")
    @Expose
    private String storeCountry = "";
    @SerializedName("store_zipcode")
    @Expose
    private Integer storeZipcode = 0;
    @SerializedName("store_latitude")
    @Expose
    private String storeLatitude = "";
    @SerializedName("store_longitude")
    @Expose
    private String storeLongitude = "";
    @SerializedName("store_image")
    @Expose
    private String storeImage = "";
    @SerializedName("store_status")
    @Expose
    private String storeStatus = "";
    @SerializedName("distance")
    @Expose
    private Double distance = 0.0;
    @SerializedName("delilvery")
    @Expose
    private Object delilvery = new Object();
    @SerializedName("mj_products")
    @Expose
    private String mjProducts = "";
    @SerializedName("store_products")
    @Expose
    private Object storeProducts = new Object();
    @SerializedName("order")
    @Expose
    private Integer order = 0;
    @SerializedName("store_marker")
    @Expose
    private String storeMarker = "";
    @SerializedName("current_usa_date_time")
    @Expose
    private String currentUsaDateTime = "";
    @SerializedName("Store_opens")
    @Expose
    private String storeOpens = "";
    @SerializedName("Store_ends")
    @Expose
    private String storeEnds = "";
    @SerializedName("events_detail")
    @Expose
    private String eventsDetail = "";
    @SerializedName("all_null")
    @Expose
    private Boolean allNull = false;
    @SerializedName("events_description")
    @Expose
    private String eventsDescription  = "";

    @SerializedName("store_team")
    @Expose
    private List<StoreTeam> storeTeam = null;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public String getStoreCountry() {
        return storeCountry;
    }

    public void setStoreCountry(String storeCountry) {
        this.storeCountry = storeCountry;
    }

    public Integer getStoreZipcode() {
        return storeZipcode;
    }

    public void setStoreZipcode(Integer storeZipcode) {
        this.storeZipcode = storeZipcode;
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

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    public String getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(String storeStatus) {
        this.storeStatus = storeStatus;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Object getDelilvery() {
        return delilvery;
    }

    public void setDelilvery(Object delilvery) {
        this.delilvery = delilvery;
    }

    public String getMjProducts() {
        return mjProducts;
    }

    public void setMjProducts(String mjProducts) {
        this.mjProducts = mjProducts;
    }

    public Object getStoreProducts() {
        return storeProducts;
    }

    public void setStoreProducts(Object storeProducts) {
        this.storeProducts = storeProducts;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getStoreMarker() {
        return storeMarker;
    }

    public void setStoreMarker(String storeMarker) {
        this.storeMarker = storeMarker;
    }

    public String getCurrentUsaDateTime() {
        return currentUsaDateTime;
    }

    public void setCurrentUsaDateTime(String currentUsaDateTime) {
        this.currentUsaDateTime = currentUsaDateTime;
    }

    public String getStoreOpens() {
        return storeOpens;
    }

    public void setStoreOpens(String storeOpens) {
        this.storeOpens = storeOpens;
    }

    public String getStoreEnds() {
        return storeEnds;
    }

    public void setStoreEnds(String storeEnds) {
        this.storeEnds = storeEnds;
    }

    public String getEventsDetail() {
        return eventsDetail;
    }

    public void setEventsDetail(String eventsDetail) {
        this.eventsDetail = eventsDetail;
    }

    public Boolean getAllNull() {
        return allNull;
    }

    public void setAllNull(Boolean allNull) {
        this.allNull = allNull;
    }

    public String getEventsDescription() {
        return eventsDescription;
    }

    public void setEventsDescription(String eventsDescription) {
        this.eventsDescription = eventsDescription;
    }

}
