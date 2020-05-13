
package com.mjdistillers.drinkthedrink.model.response.chat_all_user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    @SerializedName("from_name")
    @Expose
    private String fromName;
    @SerializedName("to_name")
    @Expose
    private String toName;
    @SerializedName("from_profileImage")
    @Expose
    private String fromProfileImage;
    @SerializedName("to_profileImage")
    @Expose
    private String toProfileImage;
    @SerializedName("img_base_url")
    @Expose
    private String imgBaseUrl;
    @SerializedName("profile_folder_name")
    @Expose
    private String profileFolderName;
    @SerializedName("from_id")
    @Expose
    private Integer fromId;
    @SerializedName("to_id")
    @Expose
    private Integer toId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Datum() {
    }

    protected Datum(Parcel in) {
        fromName = in.readString();
        toName = in.readString();
        fromProfileImage = in.readString();
        toProfileImage = in.readString();
        imgBaseUrl = in.readString();
        profileFolderName = in.readString();
        if (in.readByte() == 0) {
            fromId = null;
        } else {
            fromId = in.readInt();
        }
        if (in.readByte() == 0) {
            toId = null;
        } else {
            toId = in.readInt();
        }
        message = in.readString();
        createdAt = in.readString();
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

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromProfileImage() {
        return fromProfileImage;
    }

    public void setFromProfileImage(String fromProfileImage) {
        this.fromProfileImage = fromProfileImage;
    }

    public String getToProfileImage() {
        return toProfileImage;
    }

    public void setToProfileImage(String toProfileImage) {
        this.toProfileImage = toProfileImage;
    }

    public String getImgBaseUrl() {
        return imgBaseUrl;
    }

    public void setImgBaseUrl(String imgBaseUrl) {
        this.imgBaseUrl = imgBaseUrl;
    }

    public String getProfileFolderName() {
        return profileFolderName;
    }

    public void setProfileFolderName(String profileFolderName) {
        this.profileFolderName = profileFolderName;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fromName);
        dest.writeString(toName);
        dest.writeString(fromProfileImage);
        dest.writeString(toProfileImage);
        dest.writeString(imgBaseUrl);
        dest.writeString(profileFolderName);
        if (fromId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(fromId);
        }
        if (toId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(toId);
        }
        dest.writeString(message);
        dest.writeString(createdAt);
    }
}
