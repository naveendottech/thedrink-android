
package com.mjdistillers.drinkthedrink.model.response.update_cocktail;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    @Expose
    private List<String> message = new ArrayList<>();
    @SerializedName("img_base_url")
    @Expose
    private String imgBaseUrl = "";
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

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getImgBaseUrl() {
        return imgBaseUrl;
    }

    public void setImgBaseUrl(String imgBaseUrl) {
        this.imgBaseUrl = imgBaseUrl;
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

}
