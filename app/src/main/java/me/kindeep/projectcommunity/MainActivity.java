package me.kindeep.projectcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });

        Button openCreatePostBtn = findViewById(R.id.openCreatePostBtn);
        openCreatePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PostingActivity.class);
                startActivity(i);
            }
        });

        API.getInstance(); //don't remove this. required to start listening for database changes.
    }

    public void openAccount(View view) {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    public void openPostings(View view) {
        Intent intent = new Intent(MainActivity.this, PostingsActivity.class);
        startActivity(intent);
    }
}
