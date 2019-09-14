package me.kindeep.projectcommunity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import org.w3c.dom.Text;

import java.util.List;

public class CustomTagLayout {

    FlexboxLayout parentFlexBox;
    List<Catagory> categories;

    CustomTagLayout(FlexboxLayout parentFlexBox, List<Catagory> catagories) {
        this.parentFlexBox = parentFlexBox;
        this.categories = catagories;
        update();
    }

    void update() {
        for (Catagory cat : categories) {

            View v = LayoutInflater.from(parentFlexBox.getContext()).inflate(R.layout.tag, parentFlexBox);

            TextView tv = ((TextView) v.findViewById(R.id.tag_name));

            tv.setText(cat.getName());
//            tv.setBackgroundColor(new Color(cat.hexCode));

        }

    }


}
