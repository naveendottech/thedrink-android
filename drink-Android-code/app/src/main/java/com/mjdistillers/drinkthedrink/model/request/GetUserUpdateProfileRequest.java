package com.mjdistillers.drinkthedrink.model.request;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetUserUpdateProfileRequest extends BaseObservable {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("token")
    @Expose
    String token;

    @SerializedName("name")
    @Expose
    String name = "";

    @SerializedName("email")
    @Expose
    String email = "";

    @SerializedName("phone")
    @Expose
    String phone = "";

    @SerializedName("username")
    @Expose
    String username = "";

    @SerializedName("role")
    @Expose
    int role;

    @SerializedName("address")
    @Expose
    String address = "";

    @SerializedName("city")
    @Expose
    String city = "";

    @SerializedName("state")
    @Expose
    String state = "";

    @SerializedName("country")
    @Expose
    String country = "";

    @SerializedName("my_status")
    @Expose
    String my_status= "";

    @SerializedName("work_at")
    @Expose
    Integer work_at;

    @SerializedName("fav_spirit")
    @Expose
    String fav_spirit= "";

    @SerializedName("fav_cocktail")
    @Expose
    String fav_cocktail= "";

//    @SerializedName("token")
//    @Expose
    String streetAdress= "";

    @SerializedName("fav_drink")
    @Expose
    String favouriteDrink= "";

    @SerializedName("visibility_status")
    @Expose
    int visibiltiy_status;

    @SerializedName("fav_liquor")
    @Expose
    String fav_liquore;


    @SerializedName("fav_alcohol")
    @Expose
    String fav_alcohol;


    @SerializedName("speciality")
    @Expose
    String special;

    @SerializedName("alcohol_online")
    @Expose
    String alcohol_online;

    @SerializedName("outing_day")
    @Expose
    String outing_day;

    public String getAlcohol_online() {
        return alcohol_online;
    }

    public void setAlcohol_online(String alcohol_online) {
        this.alcohol_online = alcohol_online;
    }

    public String getOuting_day() {
        return outing_day;
    }

    public void setOuting_day(String outing_day) {
        this.outing_day = outing_day;
    }

    public String getFav_liquore() {
        return fav_liquore;
    }

    public void setFav_liquore(String fav_liquore) {
        this.fav_liquore = fav_liquore;
    }

    public String getFav_alcohol() {
        return fav_alcohol;
    }

    public void setFav_alcohol(String fav_alcohol) {
        this.fav_alcohol = fav_alcohol;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    String fileToUpload= "";

    public String getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(String fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public int getVisibiltiy_status() {
        return visibiltiy_status;
    }

    public void setVisibiltiy_status(int visibiltiy_status) {
        this.visibiltiy_status = visibiltiy_status;
    }


    @Bindable
    public String getStreetAdress() {
        return streetAdress;
    }

    public void setStreetAdress(String streetAdress) {
        this.streetAdress = streetAdress;
        notifyPropertyChanged(BR.streetAdress);
    }


    @Bindable
    public String getFavouriteDrink() {
        return favouriteDrink;
    }

    public void setFavouriteDrink(String favouriteDrink) {
        this.favouriteDrink = favouriteDrink;
        notifyPropertyChanged(BR.favouriteDrink);
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

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;

    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        notifyPropertyChanged(BR.city);
    }

    @Bindable
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }

    @Bindable
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        notifyPropertyChanged(BR.country);
    }

    @Bindable
    public String getMy_status() {
        return my_status;
    }

    public void setMy_status(String my_status) {
        this.my_status = my_status;
        notifyPropertyChanged(BR.my_status);
    }

    @Bindable
    public Integer getWork_at() {
        return work_at;
    }

    public void setWork_at(Integer work_at) {
        this.work_at = work_at;
        notifyPropertyChanged(BR.work_at);
    }

    @Bindable
    public String getFav_spirit() {
        return fav_spirit;
    }

    public void setFav_spirit(String fav_spirit) {
        this.fav_spirit = fav_spirit;
        notifyPropertyChanged(BR.fav_spirit);
    }

    public String getFav_cocktail() {
        return fav_cocktail;
    }

    public void setFav_cocktail(String fav_cocktail) {
        this.fav_cocktail = fav_cocktail;
    }

}
