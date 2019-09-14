package me.kindeep.projectcommunity;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import org.w3c.dom.Text;

import java.util.List;

public class CustomTagLayout {

    FlexboxLayout parentFlexBox;
    List<Catagory> categories;
    int inflate_from;

    CustomTagLayout(FlexboxLayout parentFlexBox, List<Catagory> catagories) {
        this(parentFlexBox, catagories, R.layout.tag);
        update();
    }

    CustomTagLayout(FlexboxLayout parentFlexBox, List<Catagory> catagories, int inflate_from) {
        this.inflate_from = inflate_from;
        this.parentFlexBox = parentFlexBox;
        this.categories = catagories;
        update();
    }

    void update() {
        parentFlexBox.removeAllViewsInLayout();
        for (Catagory cat : categories) {
            View v = LayoutInflater.from(parentFlexBox.getContext()).inflate(inflate_from, parentFlexBox, false);

            TextView tv = ((TextView) v.findViewById(R.id.tag_name));

            tv.setText(cat.getName());
            v.setBackgroundColor(Color.parseColor(cat.getHexString()));
            parentFlexBox.addView(v);
        }
        Log.d("WHAT SIZE", categories.size() + "");
    }


}
