package com.unicomg.robustatask.store.models.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys {
    @Expose
    @SerializedName("sunset")
    private int sunset;
    @Expose
    @SerializedName("sunrise")
    private int sunrise;
    @Expose
    @SerializedName("country")
    private String country;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("type")
    private int type;

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
