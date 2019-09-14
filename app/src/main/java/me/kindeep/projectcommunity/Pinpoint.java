package me.kindeep.projectcommunity;

public class Pinpoint {
    int x;
    int y;
    Catagory catagory;
    Posting posting;
    public Pinpoint(Catagory catagoryC, Posting postingC){
        posting = postingC;
        catagory = catagoryC;
    }
    public String viewMessage(){
        return posting.message;
    }






}
