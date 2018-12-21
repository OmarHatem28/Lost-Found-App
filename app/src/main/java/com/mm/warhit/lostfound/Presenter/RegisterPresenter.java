package com.mm.warhit.lostfound.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.mm.warhit.lostfound.Model.FireBaseDB;
import com.mm.warhit.lostfound.Model.User;

public class RegisterPresenter {

    FireBaseDB fireBaseDB = new FireBaseDB();
//    RegisterView registerView;

    public void addUserPres(User user, Context context){
        boolean status = fireBaseDB.addUser(user);

        if ( status ){
            SharedPreferences sharedPreferences=context.getSharedPreferences("userData",Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedEditor=sharedPreferences.edit();
            sharedEditor.putString("name",user.getName());
            sharedEditor.putString("email", user.getEmail());
            sharedEditor.putString("password", user.getPassword());
            sharedEditor.putString("phone", user.getPhone());
            sharedEditor.apply();
        }
    }

}
