package com.mm.warhit.lostfound.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mm.warhit.lostfound.Model.Post;
import com.mm.warhit.lostfound.Model.User;
import com.mm.warhit.lostfound.Presenter.RegisterPresenter;
import com.mm.warhit.lostfound.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RegisterPresenter registerPresenter = new RegisterPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = new User();
        user.setName("Omar");
        user.setEmail("omar@gmail.com");
        user.setPassword("159357");
        user.setPhone("010");

        registerPresenter.addUserPres(user, this);
    }
}
