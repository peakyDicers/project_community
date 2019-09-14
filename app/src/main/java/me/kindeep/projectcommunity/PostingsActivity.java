package me.kindeep.projectcommunity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class PostingsActivity extends AppCompatActivity {

    MapView mapView;

    RecyclerView recyclerView;

    List<Posting> postings;

    void updatePostings() {
        recyclerView.notifyAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postings);


        recyclerView = findViewById(R.id.recyclerView);

        postings = API.getInstance().postings;

        Log.e("size", postings.size() + "");



        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false);
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.posting_tile, parent, false);


                return new PostingHolder(v, (TextView) v.findViewById(R.id.title),(TextView) v.findViewById(R.id.description), (FlexboxLayout) v.findViewById(R.id.skills));

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


