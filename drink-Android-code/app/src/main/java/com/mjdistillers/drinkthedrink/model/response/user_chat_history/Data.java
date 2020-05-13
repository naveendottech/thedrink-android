
package com.mjdistillers.drinkthedrink.model.response.user_chat_history;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("message")
    @Expose
    private List<String> message = null;

    @SerializedName("user_device_token")
    @Expose
    private String user_device_token = "";

    @SerializedName("follow_device_token")
    @Expose
    private String follow_device_token = "";

    public String getUser_device_token() {
        return user_device_token;
    }

    public void setUser_device_token(String user_device_token) {
        this.user_device_token = user_device_token;
    }

    public String getFollow_device_token() {
        return follow_device_token;
    }

    public void setFollow_device_token(String follow_device_token) {
        this.follow_device_token = follow_device_token;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

}
