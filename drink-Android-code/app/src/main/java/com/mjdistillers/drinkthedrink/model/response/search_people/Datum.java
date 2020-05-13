
package com.mjdistillers.drinkthedrink.model.response.search_people;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("fav_drink")
    @Expose
    private Object favDrink;
    @SerializedName("my_status")
    @Expose
    private Object myStatus;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;

    @SerializedName("user_blocked_by")
    @Expose
    private int user_blocked_by;

    @SerializedName("user_blocked_id")
    @Expose
    private int user_blocked_id;

    @SerializedName("follow_by")
    @Expose
    private Integer followBy;
    @SerializedName("follow_to")
    @Expose
    private Integer followTo;
    @SerializedName("user_1")
    @Expose
    private Integer user1;
    @SerializedName("user_2")
    @Expose
    private Integer user2;
    @SerializedName("block_id")
    @Expose
    private Integer blockId;
    @SerializedName("public_user")
    @Expose
    private Boolean publicUser;



    public int getUser_blocked_by() {
        return user_blocked_by;
    }

    public void setUser_blocked_by(int user_blocked_by) {
        this.user_blocked_by = user_blocked_by;
    }

    public int getUser_blocked_id() {
        return user_blocked_id;
    }

    public void setUser_blocked_id(int user_blocked_id) {
        this.user_blocked_id = user_blocked_id;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getFavDrink() {
        return favDrink;
    }

    public void setFavDrink(Object favDrink) {
        this.favDrink = favDrink;
    }

    public Object getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(Object myStatus) {
        this.myStatus = myStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Integer getFollowBy() {
        return followBy;
    }

    public void setFollowBy(Integer followBy) {
        this.followBy = followBy;
    }

    public Integer getFollowTo() {
        return followTo;
    }

    public void setFollowTo(Integer followTo) {
        this.followTo = followTo;
    }

    public Integer getUser1() {
        return user1;
    }

    public void setUser1(Integer user1) {
        this.user1 = user1;
    }

    public Integer getUser2() {
        return user2;
    }

    public void setUser2(Integer user2) {
        this.user2 = user2;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Boolean getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(Boolean publicUser) {
        this.publicUser = publicUser;
    }

}
