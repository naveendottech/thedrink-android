
package com.mjdistillers.drinkthedrink.model.response.follow_unfollow_to_back_decline_logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFollowUnfollowTO_BackResponse_Decline_Logout {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;

    private Integer requestedFor;

    public Integer getRequestedFor() {
        return requestedFor;
    }

    public void setRequestedFor(Integer requestedFor) {
        this.requestedFor = requestedFor;
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
