package me.kindeep.projectcommunity;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class ExtendedCustomTagLayout extends CustomTagLayout {
    ExtendedCustomTagLayout(FlexboxLayout parentFlexBox, List<Catagory> catagories) {
        super(parentFlexBox, catagories);
    }

    ExtendedCustomTagLayout(FlexboxLayout parentFlexBox, List<Catagory> catagories, int inflate_from) {
        super(parentFlexBox, catagories, inflate_from);
    }

    @Override
    void update() {
        super.update();
        View v = LayoutInflater.from(parentFlexBox.getContext()).inflate(R.layout.add_tag, this.parentFlexBox, false );

        this.parentFlexBox.addView(v);
    }
}
