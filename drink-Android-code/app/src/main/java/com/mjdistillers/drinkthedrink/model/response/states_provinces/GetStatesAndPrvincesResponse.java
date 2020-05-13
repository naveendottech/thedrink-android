
package com.mjdistillers.drinkthedrink.model.response.states_provinces;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStatesAndPrvincesResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;

    protected GetStatesAndPrvincesResponse(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
    }

    public static final Creator<GetStatesAndPrvincesResponse> CREATOR = new Creator<GetStatesAndPrvincesResponse>() {
        @Override
        public GetStatesAndPrvincesResponse createFromParcel(Parcel in) {
            return new GetStatesAndPrvincesResponse(in);
        }

        @Override
        public GetStatesAndPrvincesResponse[] newArray(int size) {
            return new GetStatesAndPrvincesResponse[size];
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
