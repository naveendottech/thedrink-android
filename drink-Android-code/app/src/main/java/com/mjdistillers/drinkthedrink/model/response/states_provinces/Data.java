
package com.mjdistillers.drinkthedrink.model.response.states_provinces;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("States")
    @Expose
    private List<com.mjdistillers.drinkthedrink.model.response.edit_profile.State> states = null;
    @SerializedName("ProvincesAndTerritories")
    @Expose
    private List<com.mjdistillers.drinkthedrink.model.response.edit_profile.ProvincesAndTerritory> provincesAndTerritories = null;

    public List<com.mjdistillers.drinkthedrink.model.response.edit_profile.State> getStates() {
        return states;
    }

    public void setStates(List<com.mjdistillers.drinkthedrink.model.response.edit_profile.State> states) {
        this.states = states;
    }

    public List<com.mjdistillers.drinkthedrink.model.response.edit_profile.ProvincesAndTerritory> getProvincesAndTerritories() {
        return provincesAndTerritories;
    }

    public void setProvincesAndTerritories(List<com.mjdistillers.drinkthedrink.model.response.edit_profile.ProvincesAndTerritory> provincesAndTerritories) {
        this.provincesAndTerritories = provincesAndTerritories;
    }

}
