
package com.mjdistillers.drinkthedrink.model.response.user_chat_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("to")
    @Expose
    private Integer to;
    @SerializedName("chat")
    @Expose
    private String chat;
    @SerializedName("date")
    @Expose
    private String date;

    public String getImg_base_url() {
        return img_base_url;
    }

    public void setImg_base_url(String img_base_url) {
        this.img_base_url = img_base_url;
    }

    public String getMessage_image() {
        return message_image;
    }

    public void setMessage_image(String message_image) {
        this.message_image = message_image;
    }


    @SerializedName("img_base_url")
    @Expose
    String img_base_url = "";


    @SerializedName("message_image")
    @Expose
    String message_image = "";

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
