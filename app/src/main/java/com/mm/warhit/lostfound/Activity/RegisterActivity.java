package com.mm.warhit.lostfound.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mm.warhit.lostfound.Model.User;
import com.mm.warhit.lostfound.Presenter.RegisterPresenter;
import com.mm.warhit.lostfound.R;
import com.squareup.picasso.Picasso;

public class RegisterActivity extends AppCompatActivity {

    RegisterPresenter registerPresenter = new RegisterPresenter();
    ImageView imageView;
    boolean show_hide = false;
    EditText _passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _passwordText = findViewById(R.id.input_password);
        imageView = findViewById(R.id.reg_img);
        Picasso.get().load(R.drawable.lostandfound).into(imageView);

//        User user = new User();
//        user.setName("Omar");
//        user.setEmail("omar@gmail.com");
//        user.setPassword("159357");
//        user.setPhone("010");
//
//        registerPresenter.addUserPres(user, this);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void showHide(View view) {

        show_hide = !show_hide;

        if ( show_hide ){
            _passwordText.setTransformationMethod(null);
        } else {
            _passwordText.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

}
