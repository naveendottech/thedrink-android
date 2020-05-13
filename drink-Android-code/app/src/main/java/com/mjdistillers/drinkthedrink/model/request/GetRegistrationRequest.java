package com.mjdistillers.drinkthedrink.model.request;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRegistrationRequest extends BaseObservable {


    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("username")
    @Expose
    String username = null;


    @SerializedName("role")
    @Expose
    int role;

    @SerializedName("password")
    @Expose
    String password = null;

    @SerializedName("email")
    @Expose
    String email = null;

    @SerializedName("device_token")
    @Expose
    private String device_token;

    @SerializedName("phone")
    @Expose
    private String phoneNumber;

    @SerializedName("fav_liquor")
    @Expose
    private String favoriteLiquor;

    @SerializedName("fav_alcohol")
    @Expose
    private String favoriteAlcohol;

    @SerializedName("work_at")
    @Expose
    private Integer work_at;

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

    @SerializedName("speciality")
    @Expose
    private String speciality;

    public String getFavoriteAlcohol() {
        return favoriteAlcohol;
    }

    public void setFavoriteAlcohol(String favoriteAlcohol) {
        this.favoriteAlcohol = favoriteAlcohol;
    }

    public Integer getWork_at() {
        return work_at;
    }

    public void setWork_at(Integer work_at) {
        this.work_at = work_at;
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
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

    @Bindable
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
        notifyPropertyChanged(BR.speciality);
    }

    @Bindable
    public String getFavoriteLiquor() {
        return favoriteLiquor;
    }

    public void setFavoriteLiquor(String favoriteLiquor) {
        this.favoriteLiquor = favoriteLiquor;
        notifyPropertyChanged(BR.favoriteLiquor);
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(BR.phoneNumber);
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
        notifyPropertyChanged(BR.role);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }



}