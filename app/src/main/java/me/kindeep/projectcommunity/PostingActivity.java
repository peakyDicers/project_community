package me.kindeep.projectcommunity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class PostingActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private float latitude = 0;
    private float longitude = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        final Button button = findViewById(R.id.createPostBtn);

        //gps.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude = (float)location.getLatitude();
                            longitude = (float)location.getLongitude();
                        }
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                //get date.
                CalendarView cal = findViewById(R.id.calendarView);
                Timestamp timeStamp = new Timestamp(new Date(cal.getDate()));

                //get description.
                EditText description = findViewById(R.id.descriptionText);

                //build the post.
                Posting p = new Posting(
                        null,
                        description.getText().toString(),
                        new Date(cal.getDate()),
                        auth.getCurrentUser().getDisplayName(),
                        auth.getCurrentUser().getUid()
<<<<<<< Updated upstream
                );
=======
                        );
>>>>>>> Stashed changes

                    p.setLocation(latitude, longitude);
                API.getInstance().createPost(p, v);
            }
        });
    }

    public void cancelPost(View v) {
        Intent i = new Intent(PostingActivity.this, PostingsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
