package me.kindeep.projectcommunity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class PostingsActivity extends AppCompatActivity {
    private static int MAP_REQUEST_CODE = 100;

    RecyclerView recyclerView;
    MapView mapView;

    //below
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    List<Posting> postings;


    ArrayList<Catagory> filter_categories;

    CustomTagLayout tag_container;


    void updatePostings() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void removeTagFromFilter(View v) {
        Log.d("Waut", v.toString());
        TextView tv = ((View) v.getParent().getParent()).findViewById(R.id.tag_name);
        int index = 0;
        for (Catagory val : filter_categories) {
            if (val.getName() != null && val.getName().equals(tv.getText().toString())) {
                filter_categories.remove(index);
                break;
            }
            index ++;
        }

        Log.d("Size", filter_categories.size() + "");
        tag_container.update();
    }


    public void addFilterCategory(View v) {

        Intent i = new Intent(PostingsActivity.this,CategorySelectActivity.class);

        startActivity(i);

        Toast.makeText(PostingsActivity.this, "Add a category", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //below
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postings);

        findViewById(R.id.category_filter);

        filter_categories = new ArrayList<>();
        filter_categories.add(new Catagory("Wut", "#ff00ff"));
        filter_categories.add(new Catagory("Wut", "#ff00ff"));
        filter_categories.add(new Catagory("Wut", "#ff00ff"));
        filter_categories.add(new Catagory("Wut", "#ff00ff"));
        filter_categories.add(new Catagory("Wut", "#ff00ff"));
        filter_categories.add(new Catagory("Wut", "#ff00ff"));

        tag_container = new ExtendedCustomTagLayout((FlexboxLayout) findViewById(R.id.category_filter), filter_categories, R.layout.tag_closeable);

        recyclerView = findViewById(R.id.recyclerView);
        mapView = findViewById(R.id.mapView);
        checkAndRequstMapPermissions();

        postings = API.getInstance().postings;
        setLocations();

        Log.e("size", postings.size() + "");


        // Gets the MapView from the XML layout and creates it
        mapView.onCreate(savedInstanceState);
        setupMapView();


        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false);
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false);


                return new PostingHolder(v, (TextView) v.findViewById(R.id.title), (TextView) v.findViewById(R.id.description), (FlexboxLayout) v.findViewById(R.id.skills));

            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                PostingHolder hol = (PostingHolder) holder;

                hol.title.setText(postings.get(position).title);

                hol.desc.setText(postings.get(position).message);

                ArrayList<Catagory> list = new ArrayList<>();
                list.add(new Catagory("Wut", "#ff00ff"));
                list.add(new Catagory("Wut", "#ff00ff"));
                list.add(new Catagory("Wut", "#ff00ff"));
                list.add(new Catagory("Wut", "#ff00ff"));
                list.add(new Catagory("Wut", "#ff00ff"));
                list.add(new Catagory("Wut", "#ff00ff"));

                new CustomTagLayout(hol.skills, list);
            }

            @Override
            public int getItemCount() {
                return postings.size();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(PostingsActivity.this));
    }

    private void checkAndRequstMapPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MAP_REQUEST_CODE);
        }
    }

    private void setLocations() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentLocation = location;


                            Globals g = (Globals) getApplication();

                            if (g != null && g.getMainUser() != null) {
                                g.getMainUser().setCoords(location.getLatitude(), location.getLongitude());
                            }

                            setupMapView();
                        }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO: Delete testing code
                        if (e != null) {
                            mapView = null;
                        }
                    }
                });
    }

    private void setupMapView() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMap mMap = googleMap;

                if (currentLocation != null){
                    LatLng atlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()); //1E6
                    float zoomLevel = 16.0f;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(atlng, zoomLevel));
                    mapView.onResume();
                }


                for (Posting p : API.getInstance().getAllPosts() != null? API.getInstance().getAllPosts() : new ArrayList<Posting>()) {
                    Globals g = (Globals) getApplication();
                    if (p.getUser() != g.getMainUser()) {
                        LatLng neighbor = new LatLng(p.getUser().x, p.getUser().y);
                        mMap.addMarker(new MarkerOptions().position(neighbor).title(p.getFirstName()) .snippet(p.toString()));
                        //TODO: TOSTRING AND TRAVEL DISTANCE

                    }

                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == MAP_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setLocations();
            } else {
                // permission denied, do nothing
            }
        }
    }

    private class PostingHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        FlexboxLayout skills;

        public PostingHolder(@NonNull View itemView, TextView title, TextView desc, FlexboxLayout skills) {
            super(itemView);
            this.title = title;
            this.desc = desc;
            this.skills = skills;
        }
    }
}






