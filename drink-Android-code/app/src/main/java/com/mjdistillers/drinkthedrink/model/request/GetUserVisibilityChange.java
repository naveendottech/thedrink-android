package com.mjdistillers.drinkthedrink.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserVisibilityChange {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("visibility_status")
    @Expose
    String visibility_status;

    @SerializedName("user_latitude")
    @Expose
    Double user_latitude;

    @SerializedName("user_longitude")
    @Expose
    Double user_longitude;

    @SerializedName("visible_for")
    @Expose
    String visible_for = "public";

    Boolean isChecked;

    public String getVisible_for() {
        return visible_for;
    }

    public void setVisible_for(String visible_for) {
        this.visible_for = visible_for;
    }

    public Double getUser_latitude() {
        return user_latitude;
    }

    public void setUser_latitude(Double user_latitude) {
        this.user_latitude = user_latitude;
    }

    public Double getUser_longitude() {
        return user_longitude;
    }

    public void setUser_longitude(Double user_longitude) {
        this.user_longitude = user_longitude;
    }


    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisibility_status() {
        return visibility_status;
    }

    public void setVisibility_status(String visibility_status) {
        this.visibility_status = visibility_status;
    }


}
