package me.kindeep.projectcommunity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {


    Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        account = new Account("John", "doe", "123 street");
        Globals g = (Globals)getApplication();
        g.setMainUser(account);



        if (account == null) {
            Toast.makeText(AccountActivity.this, "Did no worko", Toast.LENGTH_SHORT).show();
        }

        ((TextView) findViewById(R.id.name)).setText(account.getFirstName() + " " + account.getLastName());

        ((TextView) findViewById(R.id.address)).setText(account.getAddress());


    }

}
