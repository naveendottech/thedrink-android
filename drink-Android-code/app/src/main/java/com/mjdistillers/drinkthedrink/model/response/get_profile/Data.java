
package com.mjdistillers.drinkthedrink.model.response.get_profile;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    @Expose
    private List<String> message = null;
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
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("user_latitude")
    @Expose
    private String userLatitude;
    @SerializedName("user_longitude")
    @Expose
    private String userLongitude;
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
    @SerializedName("work_at")
    @Expose
    private Integer workAt;
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
    @SerializedName("visibility_status")
    @Expose
    private String visibilityStatus;
    @SerializedName("visible_for")
    @Expose
    private String visibleFor;
    @SerializedName("visible_bar")
    @Expose
    private String visibleBar;
    @SerializedName("follow_by")
    @Expose
    private Integer followBy;
    @SerializedName("follow_to")
    @Expose
    private Integer followTo;
    @SerializedName("drink_image_1")
    @Expose
    private String drinkImage1;
    @SerializedName("drink_image_2")
    @Expose
    private String drinkImage2;
    @SerializedName("drink_image_3")
    @Expose
    private String drinkImage3;
    @SerializedName("profile_folder_name")
    @Expose
    private String profileFolderName;
    @SerializedName("drinks_folder_name")
    @Expose
    private String drinksFolderName;

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
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

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
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

    public Integer getWorkAt() {
        return workAt;
    }

    public void setWorkAt(Integer workAt) {
        this.workAt = workAt;
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

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public void setVisibilityStatus(String visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
    }

    public String getVisibleFor() {
        return visibleFor;
    }

    public void setVisibleFor(String visibleFor) {
        this.visibleFor = visibleFor;
    }

    public String getVisibleBar() {
        return visibleBar;
    }

    public void setVisibleBar(String visibleBar) {
        this.visibleBar = visibleBar;
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

    public String getDrinkImage1() {
        return drinkImage1;
    }

    public void setDrinkImage1(String drinkImage1) {
        this.drinkImage1 = drinkImage1;
    }

    public String getDrinkImage2() {
        return drinkImage2;
    }

    public void setDrinkImage2(String drinkImage2) {
        this.drinkImage2 = drinkImage2;
    }

    public String getDrinkImage3() {
        return drinkImage3;
    }

    public void setDrinkImage3(String drinkImage3) {
        this.drinkImage3 = drinkImage3;
    }

    public String getProfileFolderName() {
        return profileFolderName;
    }

    public void setProfileFolderName(String profileFolderName) {
        this.profileFolderName = profileFolderName;
    }

    public String getDrinksFolderName() {
        return drinksFolderName;
    }

    public void setDrinksFolderName(String drinksFolderName) {
        this.drinksFolderName = drinksFolderName;
    }

}
