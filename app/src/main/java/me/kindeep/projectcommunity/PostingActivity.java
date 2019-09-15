package me.kindeep.projectcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;


public class PostingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        final Button button = findViewById(R.id.createPostBtn);

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
                );


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
