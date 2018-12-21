package com.mm.warhit.lostfound.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.mm.warhit.lostfound.Model.FireBaseDB;
import com.mm.warhit.lostfound.Model.User;

public class RegisterPresenter {

    FireBaseDB fireBaseDB = new FireBaseDB();
//    RegisterView registerView;

    public void addUserPres(User user, Context context){

        user.setPassword(hashFunction(user.getPassword()));

        boolean status = fireBaseDB.addUser(user);

        if ( status ){
            SharedPreferences sharedPreferences=context.getSharedPreferences("userData",Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedEditor=sharedPreferences.edit();
            sharedEditor.putString("name",user.getName());
            sharedEditor.putString("email", user.getEmail());
            sharedEditor.putString("password", user.getPassword());
            sharedEditor.putString("phone", user.getPhone());
            sharedEditor.apply();

//            registerView.addedUser();
        }
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
