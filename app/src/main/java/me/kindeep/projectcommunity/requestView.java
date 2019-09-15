package me.kindeep.projectcommunity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class requestView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);

        if(savedInstanceState != null) {
            String postingId = savedInstanceState.getString("posting_id");

            Log.e("id", postingId);
            Toast.makeText(this, postingId, Toast.LENGTH_SHORT).show();

            for(Posting post :API.getInstance().postings) {

            }
        }
        else {
            Log.e("id", "that null");
        }

    }

    public void dial(View v) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:123456789"));
            startActivity(callIntent);
        }
        catch (Exception e) {
            Log.e("Didnt call", "didnt call");
        }
    }
}
