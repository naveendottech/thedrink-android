
package com.mjdistillers.drinkthedrink.model.response.get_stores;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreTeam {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("team_id")
    @Expose
    private Integer teamId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("speciality")
    @Expose
    private String speciality;
    @SerializedName("userFollower")
    @Expose
    private Boolean userFollower;
    @SerializedName("userFollowing")
    @Expose
    private Boolean userFollowing;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Boolean getUserFollower() {
        return userFollower;
    }

    public void setUserFollower(Boolean userFollower) {
        this.userFollower = userFollower;
    }

    public Boolean getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(Boolean userFollowing) {
        this.userFollowing = userFollowing;
    }
}
