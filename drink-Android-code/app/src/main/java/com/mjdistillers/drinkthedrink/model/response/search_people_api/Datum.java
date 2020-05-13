
package com.mjdistillers.drinkthedrink.model.response.search_people_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_latitude")
    @Expose
    private String userLatitude;
    @SerializedName("user_longitude")
    @Expose
    private String userLongitude;
    @SerializedName("visible_bar")
    @Expose
    private String visibleBar;
    @SerializedName("role")
    @Expose
    private Integer role;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("fav_drink")
    @Expose
    private String favDrink;
    @SerializedName("fav_spirit")
    @Expose
    private String favSpirit;
    @SerializedName("fav_cocktail")
    @Expose
    private String favCocktail;
    @SerializedName("my_status")
    @Expose
    private String myStatus;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("visible_for")
    @Expose
    private String visibleFor;
    @SerializedName("visibility_status")
    @Expose
    private String visibilityStatus;
    @SerializedName("follow_by")
    @Expose
    private Integer followBy;
    @SerializedName("follow_to")
    @Expose
    private Integer followTo;
    @SerializedName("initiate_id")
    @Expose
    private Integer initiateId;
    @SerializedName("public_user")
    @Expose
    private Boolean publicUser;
    @SerializedName("requested")
    @Expose
    private Boolean requested;

    @SerializedName("user_blocked_by")
    @Expose
    private int user_blocked_by;

    @SerializedName("user_blocked_id")
    @Expose
    private int user_blocked_id;

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

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }

    public String getVisibleBar() {
        return visibleBar;
    }

    public void setVisibleBar(String visibleBar) {
        this.visibleBar = visibleBar;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFavDrink() {
        return favDrink;
    }

    public void setFavDrink(String favDrink) {
        this.favDrink = favDrink;
    }

    public String getFavSpirit() {
        return favSpirit;
    }

    public void setFavSpirit(String favSpirit) {
        this.favSpirit = favSpirit;
    }

    public String getFavCocktail() {
        return favCocktail;
    }

    public void setFavCocktail(String favCocktail) {
        this.favCocktail = favCocktail;
    }

    public String getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(String myStatus) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getVisibleFor() {
        return visibleFor;
    }

    public void setVisibleFor(String visibleFor) {
        this.visibleFor = visibleFor;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public void setVisibilityStatus(String visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
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

    public Integer getInitiateId() {
        return initiateId;
    }

    public void setInitiateId(Integer initiateId) {
        this.initiateId = initiateId;
    }

    public Boolean getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(Boolean publicUser) {
        this.publicUser = publicUser;
    }

    public Boolean getRequested() {
        return requested;
    }

    public void setRequested(Boolean requested) {
        this.requested = requested;
    }

}
