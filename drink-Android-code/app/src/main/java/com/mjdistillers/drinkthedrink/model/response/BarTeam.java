
package com.mjdistillers.drinkthedrink.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarTeam {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("bar_id")
    @Expose
    private Integer barId;
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



    public int position = -1;

    public Boolean isBar = true;

    public Boolean isFollowVisible = false;

    public Boolean isUnfollowVisible = false;

    public String justRevertButton = "";

    public Boolean getBar() {
        return isBar;
    }

    public void setBar(Boolean bar) {
        isBar = bar;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
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

    public BarTeam generateCopy(BarTeam barTeam){
        BarTeam barTeamCopy = new BarTeam();
        barTeamCopy.barId = barTeam.barId;
        barTeamCopy.teamId = barTeam.teamId;
        barTeamCopy.userId = barTeam.userId;
        barTeamCopy.designation = barTeam.designation;
        barTeamCopy.deviceToken = barTeam.deviceToken;
        barTeamCopy.isBar = barTeam.isBar;
        barTeamCopy.isFollowVisible = barTeam.isFollowVisible;
        barTeamCopy.isUnfollowVisible = barTeam.isUnfollowVisible;
        barTeamCopy.justRevertButton = barTeam.justRevertButton;
        barTeamCopy.name = barTeam.name;
        barTeamCopy.position = barTeam.position;
        barTeamCopy.profileImg = barTeam.profileImg;
        barTeamCopy.speciality = barTeam.speciality;
        return barTeamCopy;
    }

}
