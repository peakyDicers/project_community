package me.kindeep.projectcommunity;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Posting {
    public Catagory[] catagories;
    public String message = "";
    public Date dPosted;
    public String id = "";
    public String creatorName = "";
    public String creatorId = "";
    public String title = "Title";
    public boolean resolved;
    public float latitude = 0.1f;
    public float longitude = 0.1f;


    public Posting(Catagory[] catagories, String message, Date dPosted, String creatorName, String creatorId) {
        this.catagories = catagories;
        this.message = message;
        this.dPosted = dPosted;
        this.creatorName = creatorName;
        this.creatorId = creatorId;
        resolved = false;
    }

    public void setLocation(float latitude, float longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Timestamp getTimeStamp(){
        return new Timestamp(dPosted);
    }

    public void setResolved() {
        resolved = true;
    }

    public String getFirstName() {
        return creatorName;
    }

    @Override
    public String toString() {
        return message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
