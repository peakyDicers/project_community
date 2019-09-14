package me.kindeep.projectcommunity;

public class Catagory {
    String name = "DEFAULE";
    String hexCode = "#FF0000";

    public Catagory(String n, String h){
        name  = n;
        hexCode = h;
    }

    public String getName() {
        return name;
    }

    public String getHexString() {
        return hexCode;
    }



}
