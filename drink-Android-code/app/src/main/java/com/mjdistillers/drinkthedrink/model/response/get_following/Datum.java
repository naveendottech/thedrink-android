
package com.mjdistillers.drinkthedrink.model.response.get_following;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("email")
    @Expose
    private String email = "";
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt = new Object();
    @SerializedName("phone")
    @Expose
    private String phone = "";
    @SerializedName("username")
    @Expose
    private String username = "";
    @SerializedName("gender")
    @Expose
    private String gender = "";
    @SerializedName("role")
    @Expose
    private String role = "";
    @SerializedName("admin_type")
    @Expose
    private Integer adminType = 0;
    @SerializedName("user_type")
    @Expose
    private String userType = "";
    @SerializedName("fav_notification")
    @Expose
    private String favNotification = "";
    @SerializedName("profile_image")
    @Expose
    private String profileImage = "";
    @SerializedName("api_token")
    @Expose
    private String apiToken = "";
    @SerializedName("created_at")
    @Expose
    private String createdAt = "";
    @SerializedName("updated_at")
    @Expose
    private String updatedAt = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFavNotification() {
        return favNotification;
    }

    public void setFavNotification(String favNotification) {
        this.favNotification = favNotification;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
