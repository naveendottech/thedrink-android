package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowUnfollowPeopleRequest {

    @SerializedName("followStatus")
    @Expose
    private int followStatus = 0;

    @SerializedName("id")
    @Expose
    private int id = 0;

    @SerializedName("follow_id")
    @Expose
    private int follow_id = 0;

    @SerializedName("device_token")
    @Expose
    private String device_token = "";

    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(int follow_id) {
        this.follow_id = follow_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
