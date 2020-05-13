package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMessageRequest {

    @SerializedName("from")
    @Expose
    Integer from = 0;


    @SerializedName("to")
    @Expose
    Integer to = 0;


    @SerializedName("user_id")
    @Expose
    Integer user_id = 0;


    @SerializedName("follow_id")
    @Expose
    Integer follow_id = 0;

    @SerializedName("message")
    @Expose
    String message = "";

    String image_str = "";


    @SerializedName("device_token")
    @Expose
    String device_token = "";

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }


    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(Integer follow_id) {
        this.follow_id = follow_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getImage_str() {
        return image_str;
    }

    public void setImage_str(String image_str) {
        this.image_str = image_str;
    }
}
