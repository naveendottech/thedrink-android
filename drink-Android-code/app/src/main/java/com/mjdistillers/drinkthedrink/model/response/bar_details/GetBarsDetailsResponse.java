
package com.mjdistillers.drinkthedrink.model.response.bar_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBarsDetailsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status = false;
    @SerializedName("data")
    @Expose
    private Bardetilsdata bardetilsdata = new Bardetilsdata();

    public Boolean isFromBars = false;


    public Boolean getFromBars() {
        return isFromBars;
    }

    public void setFromBars(Boolean fromBars) {
        isFromBars = fromBars;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Bardetilsdata getBardetilsdata() {
        return bardetilsdata;
    }

    public void setBardetilsdata(Bardetilsdata bardetilsdata) {
        this.bardetilsdata = bardetilsdata;
    }

}
