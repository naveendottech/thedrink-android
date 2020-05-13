
package com.mjdistillers.drinkthedrink.model.response.login;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginData {

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
    @SerializedName("address")
    @Expose
    private String address = "";
    @SerializedName("city")
    @Expose
    private String city = "";
    @SerializedName("state")
    @Expose
    private String state = "";
    @SerializedName("country")
    @Expose
    private String country = "";
    @SerializedName("fav_drink")
    @Expose
    private String favDrink = "";
    @SerializedName("fav_spirit")
    @Expose
    private String favSpirit = "";
    @SerializedName("fav_cocktail")
    @Expose
    private String favCocktail = "";
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
    @SerializedName("follow_by")
    @Expose
    private Integer followBy = 0;
    @SerializedName("follow_to")
    @Expose
    private Integer followTo = 0;
    @SerializedName("States")
    @Expose
    private List<State> states = new ArrayList<>();
    @SerializedName("ProvincesAndTerritories")
    @Expose
    private List<ProvincesAndTerritory> provincesAndTerritories = new ArrayList<>();

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
    private Integer workAt = 0;

    public Integer getWorkAt() {
        return workAt;
    }

    public void setWorkAt(Integer workAt) {
        this.workAt = workAt;
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

}
