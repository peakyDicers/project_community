package me.kindeep.projectcommunity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: admin
 * Date: 2019-09-14
 */
public class API {
    private static API instance;
    private FirebaseFirestore db;
    private String TAG = "API: ";

    List<Posting> postings = new ArrayList<Posting>();
    Account account = null;

    private API() {
        db = FirebaseFirestore.getInstance();
    }

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
            instance.listenForPosts();
        }
        return instance;
    }

    public void createPost(Posting p, final View v) {
        Map<String, Object> post = new HashMap<>();
        post.put("creator_id", p.creatorId);
        post.put("creator_name", p.creatorName);
        post.put("date_created", new Timestamp(p.dPosted));
        post.put("description", p.message);
        post.put("latitude", 0);
        post.put("longitude", 0);
        post.put("address", "123 street.");


        db.collection("posts")
                .add(post)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(v.getContext(), "Successfully Posted (:", Toast.LENGTH_SHORT);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(v.getContext(), "Could not post. ):", Toast.LENGTH_SHORT);
                    }
                });
    }

    public List<Catagory> getCategories() {
        List<Catagory> result = new ArrayList<>();
        result.add(new Catagory("Home", "#f1c40f"));
        result.add(new Catagory("Vehicle", "#28b463"));
        result.add(new Catagory("Plumbing", "#138d75"));
        result.add(new Catagory("Cooking", "#2e86c1"));
        result.add(new Catagory("Lawn Care", "#7d3c98"));
        result.add(new Catagory("Child Care", "#cb4335"));
        return result;
    }

    private void listenForPosts() {
        db.collection("posts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        postings.clear();
                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Log.e("wow", doc.toString());
                            Map<String, Object> data = doc.getData();
                            Posting p = new Posting(
                                    null,
                                    (String) data.get("description"),
                                    ((Timestamp)data.get("date_created")).toDate(),
                                    (String)data.get("creator_name"),
                                    (String)data.get("creator_id")
                            );
                            postings.add(p);
                        }
                        Log.d(TAG, "POSTINGS HAS BEEN UPDATED.");
                    }
                });
    }

    @Deprecated
    public List<Posting> getAllPosts() {
//        postings = new ArrayList<Posting>();
//        db.collection("posts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                Map<String, Object> data = document.getData();
//                                Posting p = new Posting(
//                                        null,
//                                        (String) data.get("description"),
//                                        ((Timestamp) data.get("date_created")).toDate(),
//                                        (String)data.get("creator_name"),
//                                        (String)data.get("creator_id")
//                                );
//                                postings.add(p);
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        return null;

    }

    public void deletePost(String postId) {
        DocumentReference docRef = db.collection("posts").document(postId);

// Remove the 'capital' field from the document
        //Map<String,Object> updates = new HashMap<>();
        //updates.put("capital", FieldValue.delete());

    }

    public Account getUser(String userId) {
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> data = document.getData();

                        account = new Account(
                                (String) data.get("displayName"),
                                (String) document.getId(),
                                (String) data.get("address")
                        );
                    } else {
                        account = new Account(null, null, null);
                        Log.d(TAG, "No such document");
                    }
                } else {
                    account = new Account(null, null, null);
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return account;
    }
}
