
package com.mjdistillers.drinkthedrink.model.response.update_profile;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    @Expose
    private List<String> message = new ArrayList<>();
    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("email")
    @Expose
    private String email = "";
    @SerializedName("phone")
    @Expose
    private String phone = "";
    @SerializedName("username")
    @Expose
    private Object username = new Object();
    @SerializedName("role")
    @Expose
    private String role = "";
    @SerializedName("profile_image")
    @Expose
    private String profileImage = "";
    @SerializedName("id")
    @Expose
    private int id = 0;
    @SerializedName("token")
    @Expose
    private String token = "";

    @SerializedName("address")
    @Expose
    private String address = "";
    @SerializedName("city")
    @Expose
    private String city = "";
    @SerializedName("state")
    @Expose
    private String state = new String();
    @SerializedName("country")
    @Expose
    private String country = new String();
    @SerializedName("fav_drink")
    @Expose
    private String favDrink = "";
    @SerializedName("fav_spirit")
    @Expose
    private String favSpirit = "";
    @SerializedName("fav_cocktail")
    @Expose
    private String favCocktail = "";
    @SerializedName("my_status")
    @Expose
    private String myStatus = "";
    @SerializedName("visibility_status")
    @Expose
    private String visibilityStatus = "";
    @SerializedName("img_base_url")
    @Expose
    private String imgBaseUrl = "";
    @SerializedName("profile_folder_name")
    @Expose
    private String profileFolderName = "";
    @SerializedName("drinks_folder_name")
    @Expose
    private String drinksFolderName = "";
    @SerializedName("drink_image_1")
    @Expose
    private String drinkImage1 = "";
    @SerializedName("drink_image_2")
    @Expose
    private String drinkImage2 = "";
    @SerializedName("drink_image_3")
    @Expose
    private String drinkImage3 = "";
    @SerializedName("States")
    @Expose
    private List<State> states = new ArrayList<>();
    @SerializedName("ProvincesAndTerritories")
    @Expose
    private List<ProvincesAndTerritory> provincesAndTerritories = new ArrayList<>();
    @SerializedName("follow_by")
    @Expose
    private Integer followBy = 0;
    @SerializedName("follow_to")
    @Expose
    private Integer followTo = 0;

    @SerializedName("fav_liquor")
    @Expose
    private String fav_liquor= "";

    @SerializedName("fav_alcohol")
    @Expose
    private String fav_alcohol= "";

    @SerializedName("speciality")
    @Expose
    private String speciality= "";

    @SerializedName("work_at")
    @Expose
    private int work_at = 0;

    @SerializedName("bar_name")
    @Expose
    private String barName;

    @SerializedName("store_name")
    @Expose
    private String storeName;

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public int getWork_at() {
        return work_at;
    }

    public void setWork_at(int work_at) {
        this.work_at = work_at;
    }

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

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public void setVisibilityStatus(String visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
    }

    public String getImgBaseUrl() {
        return imgBaseUrl;
    }

    public void setImgBaseUrl(String imgBaseUrl) {
        this.imgBaseUrl = imgBaseUrl;
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

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<ProvincesAndTerritory> getProvincesAndTerritories() {
        return provincesAndTerritories;
    }

    public void setProvincesAndTerritories(List<ProvincesAndTerritory> provincesAndTerritories) {
        this.provincesAndTerritories = provincesAndTerritories;
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
