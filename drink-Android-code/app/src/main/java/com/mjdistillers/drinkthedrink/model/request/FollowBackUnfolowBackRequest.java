package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowBackUnfolowBackRequest {

    @SerializedName("id")
    @Expose
    private Integer id = 0;

    @SerializedName("follow_id")
    @Expose
    private Long follow_id = 0l;

    @SerializedName("followStatus")
    @Expose
    private Integer followStatus = 0;

    @SerializedName("device_token")
    @Expose
    private Integer device_token = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(Long follow_id) {
        this.follow_id = follow_id;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public Integer getDevice_token() {
        return device_token;
    }

    public void setDevice_token(Integer device_token) {
        this.device_token = device_token;
    }
}
