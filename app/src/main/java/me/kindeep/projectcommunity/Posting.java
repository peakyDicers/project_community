package me.kindeep.projectcommunity;



import java.util.Date;

public class Posting{
    public Catagory [] catagories;
    public String message;
    public Date dPosted;
    public Date dDue;
    public boolean resolved;
    public Account accountPosted;


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
    public Account getUser(){return accountPosted; }
    public String getFirstName() {return accountPosted.getFirstName();}






}
