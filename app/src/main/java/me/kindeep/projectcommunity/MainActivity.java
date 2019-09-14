package me.kindeep.projectcommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openAccount(View view) {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    public void openPostings(View view) {
        Intent intent = new Intent(MainActivity.this, PostingActivity.class);
        startActivity(intent);
    }
}
