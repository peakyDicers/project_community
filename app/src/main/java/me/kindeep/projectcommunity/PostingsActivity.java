package me.kindeep.projectcommunity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PostingsActivity extends AppCompatActivity {

    MapView mapView;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postings);


        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false);

                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false)) {

                };
//                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 30;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(PostingsActivity.this));

        // Gets the MapView from the XML layout and creates it
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {


                GoogleMap mMap = googleMap;



                for(Posting p  : API.getInstance().getAllPosts()){
                    Globals g = (Globals)getApplication();
                    if(p.getUser() == g.getMainUser()){
                        LatLng latlng  = new LatLng(p.getUser().x, p.getUser().y);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                    }
                    else{

                        LatLng neighbor = new LatLng (p.getUser().x,p.getUser().y);
                        mMap.addMarker(new MarkerOptions().position(neighbor).title(p.getFirstName()));
                    }
                }

                mapView.onResume();
            }
        });
    }
}


