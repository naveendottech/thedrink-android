
package com.mjdistillers.drinkthedrink.model.response.user_chat_history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserChatHistoryResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;

    protected GetUserChatHistoryResponse(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
    }

    public static final Creator<GetUserChatHistoryResponse> CREATOR = new Creator<GetUserChatHistoryResponse>() {
        @Override
        public GetUserChatHistoryResponse createFromParcel(Parcel in) {
            return new GetUserChatHistoryResponse(in);
        }

        @Override
        public GetUserChatHistoryResponse[] newArray(int size) {
            return new GetUserChatHistoryResponse[size];
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
