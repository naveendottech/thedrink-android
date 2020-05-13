
package com.mjdistillers.drinkthedrink.model.response.store_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComingEvent {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("event_desc")
    @Expose
    private String eventDesc = "";
    @SerializedName("event_type")
    @Expose
    private String eventType = "";

    @SerializedName("other_type")
    @Expose
    private String otherType = "";

    @SerializedName("start_time")
    @Expose
    private String startTime = "";
    @SerializedName("end_time")
    @Expose
    private String endTime = "";
    @SerializedName("event_image")
    @Expose
    private String eventImage = "";
    @SerializedName("drink_price")
    @Expose
    private String drinkPrice = "";
    @SerializedName("food_price")
    @Expose
    private String foodPrice = "";

    @SerializedName("event_category")
    @Expose
    private String event_category = "";



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(String drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getEvent_category() {
        return event_category;
    }

    public void setEvent_category(String event_category) {
        this.event_category = event_category;
    }

}
