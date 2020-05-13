
package com.mjdistillers.drinkthedrink.model.response.get_followers;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    @Expose
    private List<String> message = new ArrayList<>();
    @SerializedName("follow_by")
    @Expose
    private Integer followBy = 0;
    @SerializedName("UserFollowers")
    @Expose
    private List<UserFollower> userFollowers = new ArrayList<>();

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public Integer getFollowBy() {
        return followBy;
    }

    public void setFollowBy(Integer followBy) {
        this.followBy = followBy;
    }

    public List<UserFollower> getUserFollowers() {
        return userFollowers;
    }

    public void setUserFollowers(List<UserFollower> userFollowers) {
        this.userFollowers = userFollowers;
    }

}
