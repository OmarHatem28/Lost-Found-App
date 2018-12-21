package com.mm.warhit.lostfound.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mm.warhit.lostfound.Model.User;
import com.mm.warhit.lostfound.View.RegisterView;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user;
    Context context;
    RegisterView registerView;

    public RegisterPresenter(User user, Context context, RegisterView registerView){
        this.user = user;
        this.context = context;
        this.registerView = registerView;
    }

    public void checkExistence(){
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        registerView.onSuccess("Exist");
                    } else {
                        addUserPres();
                    }
                } else {
                    registerView.onFailure();
                }
            }
        });
    }

    public void addUserPres(){

        user.setPassword(hashFunction(user.getPassword()));

        Map<String, Object> userHM = new HashMap<>();
        userHM.put("name", user.getName());
        userHM.put("email", user.getEmail());
        userHM.put("password", user.getPassword());
        userHM.put("phone", user.getPhone());

        db.collection("users").document(user.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        saveData();
                        registerView.onSuccess("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        registerView.onFailure();
                    }
                });
    }

    public void saveData(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor=sharedPreferences.edit();
        sharedEditor.putString("name",user.getName());
        sharedEditor.putString("email", user.getEmail());
        sharedEditor.putString("password", user.getPassword());
        sharedEditor.putString("phone", user.getPhone());
        sharedEditor.apply();
    }

    private String hashFunction(String pass) {

        int ascii;
        char chr;
        String hashed = "";
        for ( int i=0;i<pass.length();i++){
            ascii = pass.charAt(i);
            ascii += 8;
            chr = (char) ascii;
            hashed += chr;
        }
        return hashed;
    }

}
