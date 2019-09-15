package me.kindeep.projectcommunity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class requestView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);

        Globals g = (Globals) getApplication();

        if (g.getPosting() != null) {
            Posting posting = g.getPosting();
            ((TextView) findViewById(R.id.message)).setText(posting.message);
            ((TextView) findViewById(R.id.dposted)).setText(posting.dPosted.toString());
            ((TextView) findViewById(R.id.creatorName)).setText(posting.creatorName);
            ((TextView) findViewById(R.id.status)).setText(posting.resolved ? "Resolved" : "Pending");
        }


    }

    public void dial(View v) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:123456789"));
            startActivity(callIntent);
        } catch (Exception e) {
            Log.e("Didnt call", "didnt call");
        }
    }
}
