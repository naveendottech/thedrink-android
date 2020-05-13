
package com.mjdistillers.drinkthedrink.model.response.search_people;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("totalResult")
    @Expose
    private Integer totalResult;
    @SerializedName("loginUserId")
    @Expose
    private String loginUserId;
    @SerializedName("message")
    @Expose
    private List<String> message = null;
    @SerializedName("profile_folder_name")
    @Expose
    private String profileFolderName;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Integer totalResult) {
        this.totalResult = totalResult;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getProfileFolderName() {
        return profileFolderName;
    }

    public void setProfileFolderName(String profileFolderName) {
        this.profileFolderName = profileFolderName;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
