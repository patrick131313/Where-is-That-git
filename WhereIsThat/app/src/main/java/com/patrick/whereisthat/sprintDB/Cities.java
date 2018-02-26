package com.patrick.whereisthat.sprintDB;

/**
 * Created by Patrick on 2/25/2018.
 */

public class Cities {

    private String city;
    private String latitude;
    private String longitude;
    public Cities(String city,String latitude,String longitude)
    {
        this.city=city;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
