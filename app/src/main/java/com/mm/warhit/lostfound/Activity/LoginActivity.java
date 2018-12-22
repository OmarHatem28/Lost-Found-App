package com.mm.warhit.lostfound.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mm.warhit.lostfound.Model.User;
import com.mm.warhit.lostfound.Presenter.LoginPresenter;
import com.mm.warhit.lostfound.Presenter.RegisterPresenter;
import com.mm.warhit.lostfound.R;
import com.mm.warhit.lostfound.View.LoginView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    LoginPresenter loginPresenter;
    ImageView imageView;
    boolean show_hide = false;

    @BindView(R.id.input_email_login) EditText _emailText;
    @BindView(R.id.input_password_login) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_register) TextView _loginLink;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _passwordText = findViewById(R.id.input_password_login);
        imageView = findViewById(R.id.login_img);
        Picasso.get().load(R.drawable.lostandfound).into(imageView);

        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if ( isNetworkAvailable(getApplicationContext()) ) {
                    login();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login Failed, Please Check Your Internet Connection and Try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void login() {

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        User user = new User();
        user.setEmail(_emailText.getText().toString());
        user.setPassword(_passwordText.getText().toString());

        loginPresenter = new LoginPresenter(user,this, this);
        loginPresenter.checkExistence();

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

    @Override
    public void onSuccess(String check) {
        if (check.equals("Doesn\'t Exist")) {
            Toast.makeText(this,"This Email Is Not Registered.",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            _loginButton.setEnabled(true);
            return;

        } else if (check.equals("Incorrect Password")) {
            Toast.makeText(this,"This Password Is Incorrect.",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            _loginButton.setEnabled(true);
            return;
        } else if (check.isEmpty()) {
            Intent intent=new Intent(this,PostsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(this,"Login Failed, Please Check Your Internet Connection.",Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }
}
