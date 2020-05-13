
package com.mjdistillers.drinkthedrink.model.response.front_ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFrontAdsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status = false;


    @SerializedName("data")
    @Expose
    private FrontAdsData data = new FrontAdsData();

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public FrontAdsData getData() {
        return data;
    }

    public void setData(FrontAdsData frontAdsdata) {

        this.data = frontAdsdata;
    }

}
