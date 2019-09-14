package me.kindeep.projectcommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class PostingsActivity extends AppCompatActivity {

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
                return 3;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(PostingsActivity.this));
    }
}
