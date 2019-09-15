package me.kindeep.projectcommunity;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

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
    public BitmapDescriptor getMarkerColour(){

        if(hexCode.equals("#f1c40f")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

        }
        if(hexCode.equals("#28b463")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        }
        if(hexCode.equals("#138d75")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
        }
        if(hexCode .equals("#2e86c1")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        }
        if(hexCode.equals("#7d3c98")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
        }
        if(hexCode.equals("#cb4335")){
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
    }



}
