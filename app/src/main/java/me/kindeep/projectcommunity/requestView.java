package me.kindeep.projectcommunity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class requestView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);
        final Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //will use:  Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("+1" + savedInstanceState));
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("+1" + "5199810360"));
                startActivity(intent);
            }
        });
    }
}
