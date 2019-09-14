package me.kindeep.projectcommunity;

import android.app.Application;

public class Globals extends Application {
    public Account mainUser;


    public Account getMainUser(){
        return this.mainUser;
    }

    public void setMainUser(Account d){
        this.mainUser=d;
    }
}
