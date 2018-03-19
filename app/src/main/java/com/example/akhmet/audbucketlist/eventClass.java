package com.example.akhmet.audbucketlist;

import java.util.Date;

/**
 * Created by Akhmet on 3/14/2018.
 */

public class eventClass {
    private Date dueDate;
    private String name;
    private Boolean isCompleted;
    private Double longtitude;
    private Double latitude;
    private String description;


    public eventClass(Date dueDateIn, String nameIn)
    {
        this.dueDate=dueDateIn;
        this.name=nameIn;
        this.isCompleted=false;
        this.longtitude=0.0;
        this.latitude=0.0;
        this.description="description";
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public String getDescription() {
        return description;
    }


    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
