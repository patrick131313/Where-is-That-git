package com.patrick.whereisthat.sprintDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Patrick on 2/24/2018.
 */
@Entity
public class Sprint {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="latitude")
    private String latitude;

    @ColumnInfo(name="longitude")
    private String longitude;

    @ColumnInfo(name ="city")
    private String city;

    public Sprint(String city, String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
