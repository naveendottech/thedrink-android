
package com.mjdistillers.drinkthedrink.model.response.update_password;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUpdatePasswordResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("0")
    @Expose
    private Integer _0;

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

    public Integer get0() {
        return _0;
    }

    public void set0(Integer _0) {
        this._0 = _0;
    }

}
