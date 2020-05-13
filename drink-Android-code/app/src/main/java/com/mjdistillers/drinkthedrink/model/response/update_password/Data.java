
package com.mjdistillers.drinkthedrink.model.response.update_password;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("message")
    @Expose
    private List<String> message;

    public List<String>  getMessage() {
        return message;
    }

    public void setMessage(List<String>  message) {
        this.message = message;
    }

}
