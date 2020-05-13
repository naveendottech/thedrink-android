
package com.mjdistillers.drinkthedrink.model.response.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RegistrationData {

    @SerializedName("message")
    @Expose
    private List<String> message = new ArrayList<>();
    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("token")
    @Expose
    private String token = "";
    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("email")
    @Expose
    private String email = "";
    @SerializedName("username")
    @Expose
    private String username = "";
    @SerializedName("phone")
    @Expose
    private String phone = "";
    @SerializedName("profile_image")
    @Expose
    private String profileImage = "";
    @SerializedName("role")
    @Expose
    private String role = "";
    @SerializedName("role_name")
    @Expose
    private String roleName = "";
    @SerializedName("visibility_status")
    @Expose
    private String visibilityStatus = "";
    @SerializedName("my_status")
    @Expose
    private String myStatus = "";
    @SerializedName("follow_by")
    @Expose
    private Integer followBy = 0;
    @SerializedName("follow_to")
    @Expose
    private Integer followTo = 0;

    @SerializedName("address")
    @Expose
    private String address = "";

    @SerializedName("drink_image_1")
    @Expose
    private String drink_image_1 = "";

    @SerializedName("drink_image_2")
    @Expose
    private String drink_image_2 = "";

    @SerializedName("drink_image_3")
    @Expose
    private String drink_image_3 = "";

    @SerializedName("fav_liquor")
    @Expose
    private String fav_liquor= "";

    @SerializedName("fav_alcohol")
    @Expose
    private String fav_alcohol= "";

    @SerializedName("speciality")
    @Expose
    private String speciality= "";

    public String getFav_liquor() {
        return fav_liquor;
    }

    public void setFav_liquor(String fav_liquor) {
        this.fav_liquor = fav_liquor;
    }

    public String getFav_alcohol() {
        return fav_alcohol;
    }

    public void setFav_alcohol(String fav_alcohol) {
        this.fav_alcohol = fav_alcohol;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDrink_image_1() {
        return drink_image_1;
    }

    public void setDrink_image_1(String drink_image_1) {
        this.drink_image_1 = drink_image_1;
    }

    public String getDrink_image_2() {
        return drink_image_2;
    }

    public void setDrink_image_2(String drink_image_2) {
        this.drink_image_2 = drink_image_2;
    }

    public String getDrink_image_3() {
        return drink_image_3;
    }

    public void setDrink_image_3(String drink_image_3) {
        this.drink_image_3 = drink_image_3;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public void setVisibilityStatus(String visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
    }

    public String getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(String myStatus) {
        this.myStatus = myStatus;
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

}
