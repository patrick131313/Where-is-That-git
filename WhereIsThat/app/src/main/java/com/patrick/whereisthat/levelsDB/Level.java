package com.patrick.whereisthat.levelsDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Patrick on 2/13/2018.
 */

@Entity

public class Level {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="level")
    private String level;

    @ColumnInfo(name="photo")
    private String photo;

    @ColumnInfo(name="latitude")
    private String latitude;

    @ColumnInfo(name="longitude")
    private String longitude;

    @ColumnInfo(name ="city")
    private String city;

    @ColumnInfo(name="hint")
    private String hint;

    public Level(String level, String photo, String latitude, String longitude, String city, String hint) {
        this.level = level;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.hint = hint;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getId() {

        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getPhoto() {
        return photo;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getHint() {
        return hint;
    }
}
