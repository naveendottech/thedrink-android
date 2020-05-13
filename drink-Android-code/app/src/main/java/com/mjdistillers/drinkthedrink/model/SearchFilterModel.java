package com.mjdistillers.drinkthedrink.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mjdistillers.drinkthedrink.App;
import com.mjdistillers.drinkthedrink.R;

public class SearchFilterModel implements Parcelable {

    int id;
    int user_1 = 0;
    int user_2 = 0;
    String distance = "";
    Double distanceDouble = 0.0;
    int dollor;
    String imageUri;
    String name;
    String from;
    String person_status;
    double miles;
    String searchKeyword;
    private Integer followBy;
    private Integer followTo;
    private Integer initiateId;
    private Boolean publicUser;
    private Boolean requested;
    int isFromBlock;
    int user_blocked_by;
    int user_blocked_id;
     int user_id;

    String deviceToken = "";
    Integer position = -1;

    String followBtnText = App.app.getString(R.string.follow);
    String blockBtnText = App.app.getString(R.string.block);


    protected SearchFilterModel(Parcel in) {
        id = in.readInt();
        distance = in.readString();
        if (in.readByte() == 0) {
            distanceDouble = null;
        } else {
            distanceDouble = in.readDouble();
        }
        dollor = in.readInt();
        imageUri = in.readString();
        name = in.readString();
        from = in.readString();
        user_blocked_by = in.readInt();
        user_blocked_id = in.readInt();
        user_id = in.readInt();
        person_status = in.readString();
        miles = in.readDouble();
        searchKeyword = in.readString();
        isFromBlock = in.readInt();

        user_1 = in.readInt();
        user_2 = in.readInt();

        if (in.readByte() == 0) {
            followBy = null;
        } else {
            followBy = in.readInt();
        }
        if (in.readByte() == 0) {
            followTo = null;
        } else {
            followTo = in.readInt();
        }
        if (in.readByte() == 0) {
            initiateId = null;
        } else {
            initiateId = in.readInt();
        }
        byte tmpPublicUser = in.readByte();
        publicUser = tmpPublicUser == 0 ? null : tmpPublicUser == 1;
        byte tmpRequested = in.readByte();
        requested = tmpRequested == 0 ? null : tmpRequested == 1;
        deviceToken = in.readString();
        if (in.readByte() == 0) {
            position = null;
        } else {
            position = in.readInt();
        }
        followBtnText = in.readString();
        blockBtnText = in.readString();
    }

    public static final Creator<SearchFilterModel> CREATOR = new Creator<SearchFilterModel>() {
        @Override
        public SearchFilterModel createFromParcel(Parcel in) {
            return new SearchFilterModel(in);
        }

        @Override
        public SearchFilterModel[] newArray(int size) {
            return new SearchFilterModel[size];
        }
    };

    public int getUser_blocked_by() {
        return user_blocked_by;
    }

    public void setUser_blocked_by(int user_blocked_by) {
        this.user_blocked_by = user_blocked_by;
    }

    public int getIsFromBlock() {
        return isFromBlock;
    }

    public void setIsFromBlock(int isFromBlock) {
        this.isFromBlock = isFromBlock;
    }

    public String getBlockBtnText() {
        return blockBtnText;
    }

    public void setBlockBtnText(String blockBtnText) {
        this.blockBtnText = blockBtnText;
    }


    public int getUser_1() {
        return user_1;
    }

    public void setUser_1(int user_1) {
        this.user_1 = user_1;
    }

    public int getUser_2() {
        return user_2;
    }

    public void setUser_2(int user_2) {
        this.user_2 = user_2;
    }


    public Integer getFollowBy() {
        return followBy;
    }

    public void setFollowBy(Integer followBy) {
        this.followBy = followBy;
    }

    public Integer getFollowTo() {
        return followTo;
    }

    public void setFollowTo(Integer followTo) {
        this.followTo = followTo;
    }

    public Integer getInitiateId() {
        return initiateId;
    }

    public void setInitiateId(Integer initiateId) {
        this.initiateId = initiateId;
    }

    public Boolean getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(Boolean publicUser) {
        this.publicUser = publicUser;
    }

    public Boolean getRequested() {
        return requested;
    }

    public void setRequested(Boolean requested) {
        this.requested = requested;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public SearchFilterModel() {
    }


    public String getFollowBtnText() {
        return followBtnText;
    }

    public void setFollowBtnText(String followBtnText) {
        this.followBtnText = followBtnText;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }


    public String getPerson_status() {
        return person_status;
    }

    public void setPerson_status(String person_status) {
        this.person_status = person_status;
    }


    public Double getDistanceDouble() {
        return distanceDouble;
    }

    public void setDistanceDouble(Double distanceDouble) {
        this.distanceDouble = distanceDouble;
    }


    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getDollor() {
        return dollor;
    }

    public void setDollor(int dollor) {
        this.dollor = dollor;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public int getUser_blocked_id() {
        return user_blocked_id;
    }

    public void setUser_blocked_id(int user_blocked_id) {
        this.user_blocked_id = user_blocked_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(distance);
        if (distanceDouble == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(distanceDouble);
        }

        dest.writeInt(user_id);
        dest.writeInt(user_blocked_by);
        dest.writeInt(user_blocked_id);
        dest.writeInt(user_1);
        dest.writeInt(user_2);
        dest.writeInt(dollor);
        dest.writeInt(isFromBlock);
        dest.writeString(imageUri);
        dest.writeString(name);
        dest.writeString(from);
        dest.writeString(person_status);
        dest.writeDouble(miles);
        dest.writeString(searchKeyword);
        if (followBy == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(followBy);
        }
        if (followTo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(followTo);
        }
        if (initiateId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(initiateId);
        }
        dest.writeByte((byte) (publicUser == null ? 0 : publicUser ? 1 : 2));
        dest.writeByte((byte) (requested == null ? 0 : requested ? 1 : 2));
        dest.writeString(deviceToken);
        if (position == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(position);
        }
        dest.writeString(followBtnText);
        dest.writeString(blockBtnText);
    }


}
