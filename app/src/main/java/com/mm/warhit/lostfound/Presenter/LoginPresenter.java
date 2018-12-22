package com.mm.warhit.lostfound.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mm.warhit.lostfound.Model.User;
import com.mm.warhit.lostfound.View.LoginView;
import com.mm.warhit.lostfound.View.RegisterView;

import java.util.Map;

public class LoginPresenter {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user;
    Context context;
    LoginView loginView;

    public LoginPresenter(User user, Context context, LoginView loginView){
        this.user = user;
        this.context = context;
        this.loginView = loginView;
    }

    public void checkExistence(){
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        getUser();
                    } else {
                        loginView.onSuccess("Doesn\'t Exist");
                    }
                } else {
                    loginView.onFailure();
                }
            }
        });
    }

    public void getUser() {
        user.setPassword(hashFunction(user.getPassword()));
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        if (task.isSuccessful()) {
                            Map<String, Object> userHM = doc.getData();
                            if (user.getPassword() == userHM.get("password")) {
                                user.setName((String) userHM.get("name"));
                                user.setPhone((String) userHM.get("phone"));
                                saveData();
                                loginView.onSuccess("");
                            } else {
                                loginView.onSuccess("Incorrect Password");
                            }
                        } else {
                            loginView.onFailure();
                        }
                    }
                });
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

    public void saveData(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedEditor=sharedPreferences.edit();
        sharedEditor.putString("name",user.getName());
        sharedEditor.putString("email", user.getEmail());
        sharedEditor.putString("password", user.getPassword());
        sharedEditor.putString("phone", user.getPhone());
        sharedEditor.apply();
    }
}
