
package com.mjdistillers.drinkthedrink.model.response.store_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStoresDetailResponse {

    @SerializedName("status")
    @Expose
    private Boolean status = false;
    @SerializedName("data")
    @Expose
    private Data data= new Data();







    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
