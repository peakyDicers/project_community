package me.kindeep.projectcommunity;

/**
 * Author: admin
 * Date: 2019-09-14
 */
public class Account {

    String firstName;
    String lastName;
    String id;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    String address;

    // need some way to store profile image.

    public Account(String firstName, String lastName, String id, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.address = address;
    }


}
