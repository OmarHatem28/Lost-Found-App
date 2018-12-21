package com.mm.warhit.lostfound.Model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FireBaseDB {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean flag = false;

    public boolean addUser(User obj){
        Map<String, Object> user = new HashMap<>();
        user.put("name", obj.getName());
        user.put("email", obj.getEmail());
        user.put("password", obj.getPassword());
        user.put("phone", obj.getPhone());

        db.collection("users").document(obj.email)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return flag;
    }

}
