
package com.mjdistillers.drinkthedrink.model.response.feedback;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("message")
    @Expose
    private List<String> message = null;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

}
