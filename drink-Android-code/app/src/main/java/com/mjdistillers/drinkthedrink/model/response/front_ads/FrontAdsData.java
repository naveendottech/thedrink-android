
package com.mjdistillers.drinkthedrink.model.response.front_ads;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrontAdsData {

    @SerializedName("images")
    @Expose
    private List<String> images = new ArrayList<>();
    @SerializedName("link")
    @Expose
    private List<String> link = new ArrayList<>();

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getLink() {
        return link;
    }

    public void setLink(List<String> link) {
        this.link = link;
    }

}
