package me.kindeep.projectcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CategorySelectActivity extends AppCompatActivity {

//    String[] country = {};

    Bundle bundle;
    String[] catnames;
    Spinner spinner;
    int pos_selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = savedInstanceState;
        setContentView(R.layout.activity_category_select);

        spinner = findViewById(R.id.spinner);

        final Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CategorySelectActivity.this,PostingActivity.class);
                startActivity(i);
            }
        });
//        String[] stringArray = Arrays.copyOf(API.getInstance().getCategories().toArray(), API.getInstance().getCategories().size(), String[].class);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos_selected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        catnames = new String[API.getInstance().getCategories().size()];
        int index = 0;
        for (Catagory value : API.getInstance().getCategories()) {
            catnames[index] = value.getName();
            index++;
        }

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, catnames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

    }

    public void doneSelection(View v) {

        Intent i = new Intent(CategorySelectActivity.this, PostingsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Globals g = (Globals) getApplication();

        if (g.getLastUsedFilter() == null) {
            Toast.makeText(CategorySelectActivity.this, "is null... Size of cats ", Toast.LENGTH_SHORT).show();
            g.setLastUsedFilter(new ArrayList<Catagory>());
        }

        Catagory toAdd = API.getInstance().getCategories().get(this.pos_selected);
//        if (!g.getLastUsedFilter().contains(toAdd)) {

        Log.e("aaaaa", "Adding to add " + toAdd);


        boolean contains = false;
        for (Catagory val : g.getLastUsedFilter()) {
            if (val.getName().equals(toAdd.getName())) {
                contains = true;
                break;
            }

        }

        if (!contains) g.getLastUsedFilter().add(toAdd);
        Log.e("whoooohola", "ahmm... " + g.getLastUsedFilter().size());
//        }

//        this.bundle.putStringArrayList("categories_filter", new ArrayList<>(Arrays.asList(catnames)));


//        this.bundle.putInt("selected_category_index", this.pos_selected);
        startActivity(i, this.bundle);
    }

    public void cancelSelection(View v) {
        Intent i = new Intent(CategorySelectActivity.this, PostingsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i, this.bundle);
    }

}
