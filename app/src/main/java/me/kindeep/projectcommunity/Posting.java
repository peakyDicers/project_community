package me.kindeep.projectcommunity;

import android.accounts.Account;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class Posting{
    Catagory [] catagories;
    String message;
    Date dPosted;
    Date dDue;
    boolean resolved;
    Account accountPosted;

    public Posting(Catagory [] c, String messageC, Date dPostedC, Date dDueC, Account accountPostedC){
        catagories = c;
        message = messageC;
        dPosted = dPostedC;
        dDue = dDueC;
        resolved = false;
        accountPosted = accountPostedC;

    }
    public void setResolved() {
        resolved = true;
    }






}
