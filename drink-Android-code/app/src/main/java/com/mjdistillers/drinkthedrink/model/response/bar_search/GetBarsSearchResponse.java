
package com.mjdistillers.drinkthedrink.model.response.bar_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBarsSearchResponse {

    @SerializedName("status")
    @Expose
    private Boolean status = false;
    @SerializedName("data")
    @Expose
    private Data data = new Data();

    @SerializedName("search")
    @Expose
    private String search = "";


    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

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
