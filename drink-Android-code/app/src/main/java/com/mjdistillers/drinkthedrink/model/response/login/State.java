
package com.mjdistillers.drinkthedrink.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

public class State {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("abbreviation")
    @Expose
    private String abbreviation = "";
    @SerializedName("full_name")
    @Expose
    private String fullName = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
