
package com.mjdistillers.drinkthedrink.model.response.edit_profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEditProfile implements Parcelable {

    @SerializedName("status")
    @Expose
    private Boolean status = false;
    @SerializedName("data")
    @Expose
    private Data data = new Data();

    protected GetEditProfile(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
    }

    public static final Creator<GetEditProfile> CREATOR = new Creator<GetEditProfile>() {
        @Override
        public GetEditProfile createFromParcel(Parcel in) {
            return new GetEditProfile(in);
        }

        @Override
        public GetEditProfile[] newArray(int size) {
            return new GetEditProfile[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
    }
}
