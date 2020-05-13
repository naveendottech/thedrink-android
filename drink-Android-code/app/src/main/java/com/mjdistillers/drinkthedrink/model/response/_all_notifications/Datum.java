
package com.mjdistillers.drinkthedrink.model.response._all_notifications;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mjdistillers.drinkthedrink.adapters.RetrofitNullToEmptyString;

import java.io.Serializable;

public class Datum implements Parcelable {

    @SerializedName("notifications")
    @Expose
    private String notifications;
    @SerializedName("follow_by")
    @Expose
    private Integer followId;
    @SerializedName("follow_name")
    @Expose
    private String followName;
    @SerializedName("follow_email")
    @Expose
    private String followEmail;
    @SerializedName("follow_username")
    @Expose
    private String followUsername;
    @SerializedName("follow_role")
    @Expose
    private String followRole;
    @SerializedName("follow_profile_image")
    @Expose
    private String followProfileImage;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getFollowDeviceToken() {
        return followDeviceToken;
    }

    public void setFollowDeviceToken(String followDeviceToken) {
        this.followDeviceToken = followDeviceToken;
    }

    @SerializedName("follow_device_token")
    @Expose
    private String followDeviceToken;




    private int position = 0;

    private boolean isAcceptedOrDeclined;

    public boolean isAccepted() {
        return isAcceptedOrDeclined;
    }

    public void setAccepted(boolean acceptedOrDeclined) {
        isAcceptedOrDeclined = acceptedOrDeclined;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getFollowEmail() {
        return followEmail;
    }

    public void setFollowEmail(String followEmail) {
        this.followEmail = followEmail;
    }

    public String getFollowUsername() {
        return followUsername;
    }

    public void setFollowUsername(String followUsername) {
        this.followUsername = followUsername;
    }

    public String getFollowRole() {
        return followRole;
    }

    public void setFollowRole(String followRole) {
        this.followRole = followRole;
    }

    public String getFollowProfileImage() {
        return followProfileImage;
    }

    public void setFollowProfileImage(String followProfileImage) {
        this.followProfileImage = followProfileImage;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    protected Datum(Parcel in) {
        notifications = in.readString();
        if (in.readByte() == 0) {
            followId = null;
        } else {
            followId = in.readInt();
        }
        followName = in.readString();
        followEmail = in.readString();
        followUsername = in.readString();
        followRole = in.readString();
        followProfileImage = in.readString();
        updatedAt = in.readString();
        position = in.readInt();
        isAcceptedOrDeclined = in.readByte() != 0;
        followDeviceToken = in.readString();

    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(notifications);
        if (followId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(followId);
        }
        dest.writeString(followName);
        dest.writeString(followEmail);
        dest.writeString(followUsername);
        dest.writeString(followRole);
        dest.writeString(followProfileImage);
        dest.writeString(updatedAt);
        dest.writeInt(position);
        dest.writeByte((byte) (isAcceptedOrDeclined ? 1 : 0));
        dest.writeString(followDeviceToken);
    }
}
