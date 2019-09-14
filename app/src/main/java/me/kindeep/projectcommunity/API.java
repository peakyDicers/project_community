package me.kindeep.projectcommunity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
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

    private API(){
        db = FirebaseFirestore.getInstance();
    }

    public static API getInstance(){
        if (instance == null){
            instance = new API();
            instance.listenForPosts();
        }

        return instance;
    }

    private void listenForPosts(){
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


                            Posting p = new Posting(null,
                                    (String)data.get("description"),
                                    ((Timestamp)data.get("date_created")).toDate(),
                                    ((Timestamp)data.get("date_due")).toDate(),
                                    new Account("ian", "222", "123 street")
                            );
                            postings.add(p);
                        }
                        Log.d(TAG, "Current cites in CA: " + cities);
                    }
                });
    }

    public List<Posting> getAllPosts(){

        return postings;

    }

    public void deletePost(String postId){
        DocumentReference docRef = db.collection("posts").document(postId);

// Remove the 'capital' field from the document
        //Map<String,Object> updates = new HashMap<>();
        //updates.put("capital", FieldValue.delete());

    }

    public Account getUser(String userId){
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
                                (String)data.get("displayName"),
                                (String)document.getId(),
                                (String)data.get("address")
                        );
                    } else {
                        account = new Account(null, null, null);
                        Log.d(TAG, "No such document");
                    }
                } else {
                    account = new Account( null, null, null);
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return account;
    }
}
