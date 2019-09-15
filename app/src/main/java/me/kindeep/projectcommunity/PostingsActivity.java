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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostingsActivity extends AppCompatActivity {
    private static int MAP_REQUEST_CODE = 100;

    RecyclerView recyclerView;
    MapView mapView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //below
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    List<Posting> postings;


    List<Catagory> filter_categories;

    CustomTagLayout tag_container;


    void updatePostings() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    void saveFilterSettings() {
        Globals g = (Globals) getApplication();

        if( this.filter_categories != null) {
            if(g.getLastUsedFilter() != null && g.getLastUsedFilter().size() > this.filter_categories.size()) {
                // not a good fix.
                return;
            }
            g.setLastUsedFilter(this.filter_categories);
        }
    }

    void recoverFilterSettings() {
        Globals g = (Globals) getApplication();
        if (g.getLastUsedFilter() != null) {
            this.filter_categories = g.getLastUsedFilter();
        }
        else {
            filter_categories = new ArrayList<>();
        }
    }

    public void removeTagFromFilter(View v) {

        TextView tv = ((View) v.getParent().getParent()).findViewById(R.id.tag_name);
        int index = 0;
        for (Catagory val : filter_categories) {
            if (val.getName() != null && val.getName().equals(tv.getText().toString())) {
                filter_categories.remove(index);
                break;
            }
            index ++;
        }


        tag_container.update();
    }


    public void addFilterCategory(View v) {

        Intent i = new Intent(PostingsActivity.this,CategorySelectActivity.class);

        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postings);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        recoverFilterSettings();

        findViewById(R.id.category_filter);

        listenForPosts();

        tag_container = new ExtendedCustomTagLayout((FlexboxLayout) findViewById(R.id.category_filter), filter_categories, R.layout.tag_closeable);

        recyclerView = findViewById(R.id.recyclerView);
        mapView = findViewById(R.id.mapView);
        checkAndRequstMapPermissions();

        postings = API.getInstance().postings;
        setLocations();

        // Gets the MapView from the XML layout and creates it
        mapView.onCreate(savedInstanceState);
        setupMapView();


        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false);
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        Intent i = new Intent(PostingsActivity.this, requestView.class);

                        Bundle b = new Bundle();
                        b.putString("posting_id", postings.get(itemPosition).getId());
                        startActivity(i);
                    }
                });

                return new PostingHolder(v,  (TextView) v.findViewById(R.id.description), (FlexboxLayout) v.findViewById(R.id.skills), (TextView) v.findViewById(R.id.bytext));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                PostingHolder hol = (PostingHolder) holder;

                hol.creator.setText(postings.get(position).creatorName);
                hol.desc.setText(postings.get(position).message);

                new CustomTagLayout(hol.skills, API.getInstance().getCategories());
            }

            @Override
            public int getItemCount() {
                return postings.size();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(PostingsActivity.this));
    }

    private void listenForPosts() {
        db.collection("posts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        String TAG = "LOL";
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        postings.clear();
                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Map<String, Object> data = doc.getData();
                            Posting p = new Posting(
                                    null,
                                    (String) data.get("description"),
                                    ((Timestamp)data.get("date_created")).toDate(),
                                    (String)data.get("creator_name"),
                                    (String)data.get("creator_id")
                            );

                            //set location.
                            float latitude = ((Double)data.get("latitude")).floatValue();
                            float longitude = ((Double)data.get("longitude")).floatValue();
                            p.setLocation(latitude, longitude);
                            postings.add(p);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }

                        Log.d(TAG, "POSTINGS HAS BEEN UPDATED.");
                    }
                });
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


                for (Posting p : API.getInstance().postings) {
                    Location loc2 = new Location("");
                    loc2.setLatitude(p.latitude);
                    loc2.setLongitude(p.longitude);

                    if (currentLocation != null){
                        float distanceInMeters = currentLocation.distanceTo(loc2);
                        LatLng neighbor = new LatLng(p.latitude, p.longitude);

                        //use commented if catagories are always available.
                        //mMap.addMarker(new MarkerOptions().position(neighbor).icon(p.catagories[0].getMarkerColour()).title(p.getFirstName()) .snippet(""+distanceInMeters+"km away\n"+p.toString()));
                        mMap.addMarker(new MarkerOptions().position(neighbor).title(p.getFirstName()) .snippet(""+distanceInMeters+"km away\n"+p.toString()));
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
        TextView creator;
        TextView desc;
        FlexboxLayout skills;

        public PostingHolder(@NonNull View itemView, TextView desc, FlexboxLayout skills, TextView creator) {
            super(itemView);
            this.desc = desc;
            this.skills = skills;
            this.creator = creator;
        }
    }

    @Override
    protected void onDestroy() {
        Log.e("Filter", "saved");
        saveFilterSettings();
        super.onDestroy();
    }

    public void openUserProfile(View v) {
        Intent i = new Intent(PostingsActivity.this,AccountActivity.class);
        startActivity(i);
    }

    public void openCreatePosting(View v) {
        Intent i = new Intent(PostingsActivity.this,PostingActivity.class);
        startActivity(i);
    }


}






