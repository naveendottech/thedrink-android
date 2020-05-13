
package com.mjdistillers.drinkthedrink.model.response.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserRegisterationResponse {

    @SerializedName("status")
    @Expose
    private Boolean status = false;
    @SerializedName("data")
    @Expose
    private RegistrationData registrationData = new RegistrationData();

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public RegistrationData getRegistrationData() {
        return registrationData;
    }

    public void setRegistrationData(RegistrationData registrationData) {
        this.registrationData = registrationData;
    }

}
