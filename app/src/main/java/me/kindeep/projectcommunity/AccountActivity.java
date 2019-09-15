package me.kindeep.projectcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

public class AccountActivity extends AppCompatActivity {

    FlexboxLayout skillsFlex;
    FlexboxLayout interestsFlex;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        account = new Account("John", "doe", "123 street");
        Globals g = (Globals) getApplication();
        g.setMainUser(account);

        skillsFlex = findViewById(R.id.skills);
        interestsFlex = findViewById(R.id.interests);

        new CustomTagLayout(skillsFlex, account.getSkills());
        new CustomTagLayout(interestsFlex, account.getInterests());

        if (account == null) {
            Toast.makeText(AccountActivity.this, "Did no worko", Toast.LENGTH_SHORT).show();
        }

        ((TextView) findViewById(R.id.name)).setText(account.getFirstName() + " " + account.getLastName());

        ((TextView) findViewById(R.id.address)).setText(account.getAddress());
        final Button button = findViewById(R.id.buttonPic);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void closePage(View v) {
        Intent i = new Intent(AccountActivity.this, PostingsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}


