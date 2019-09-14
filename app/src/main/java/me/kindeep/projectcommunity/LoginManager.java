package me.kindeep.projectcommunity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Author: admin
 * Date: 2019-09-14
 */
public class LoginManager {


    /**
     *
     * @param username
     * @param password
     * @return account id (from firebase).
     */
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static LoginManager instance;

    private LoginManager(){
        instance = new LoginManager();
    }

    public static LoginManager getInstance(){
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    public void SignUp(FirebaseUser user){

        Map<String, Object> newUser = new HashMap<>();
        newUser.put("displayName", user.getDisplayName());
        newUser.put("email", user.getEmail());
        newUser.put("phone", user.getPhoneNumber());
        newUser.put("uid", user.getUid());
        newUser.put("photoUrl", user.getPhotoUrl());

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
