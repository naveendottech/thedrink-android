
package com.mjdistillers.drinkthedrink.model.response.get_following;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    @Expose
    private List<String> message = new ArrayList<>();
    @SerializedName("follow_to")
    @Expose
    private Integer followTo = 0;
    @SerializedName("UserFollowing")
    @Expose
    private List<UserFollowing> userFollowing = new ArrayList<>();

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public Integer getFollowTo() {
        return followTo;
    }

    public void setFollowTo(Integer followTo) {
        this.followTo = followTo;
    }

    public List<UserFollowing> getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(List<UserFollowing> userFollowing) {
        this.userFollowing = userFollowing;
    }

}
