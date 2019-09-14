package me.kindeep.projectcommunity;

import android.app.Application;

import java.util.List;

public class Globals extends Application {
    public Account mainUser;
    public List<Catagory> last_used_filter;

    public Account getMainUser(){
        return this.mainUser;
    }

    public void setMainUser(Account d){
        this.mainUser=d;
    }

    public List<Catagory> getLastUsedFilter() {
        return last_used_filter;
    }

    public void setLastUsedFilter(List<Catagory> categories) {
        last_used_filter = categories;
    }
}
