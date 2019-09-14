package me.kindeep.projectcommunity;

import java.util.List;

/**
 * Author: admin
 * Date: 2019-09-14
 */
public class Account {

    public String displayName;
    public String phoneNumber;
    public String id;
    public double x;
    public double y;
    public List<Catagory> skill;
    public List<Catagory> interests;

    public String getFirstName() { return displayName; }

    public String getLastName() { return ""; }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    String address;

    // need some way to store profile image.

    public Account(String displayName, String id, String address){
        this.displayName = displayName;
        this.id = id;
        this.address = address;
    }
    public void setCoords(double la, double lo){
        x = lo;
        y = la;
    }
}
