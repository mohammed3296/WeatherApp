package com.unicomg.robustatask.store.local;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Image {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "place")
    private String place;

    public int getId() {
        return id;
    }

    public Image(String url, String place) {
        this.url = url;
        this.place = place;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}