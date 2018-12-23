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
import com.mm.warhit.lostfound.Presenter.RegisterPresenter;
import com.mm.warhit.lostfound.R;
import com.mm.warhit.lostfound.View.RegisterView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    RegisterPresenter registerPresenter;
    ImageView imageView;
    boolean show_hide = false;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_phone) EditText _phoneText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _passwordText = findViewById(R.id.input_password);
        imageView = findViewById(R.id.reg_img);
        Picasso.get().load(R.drawable.lostandfound).into(imageView);

        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( isNetworkAvailable(getApplicationContext()) ) {
                    signup();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Registration Failed, Please Check Your Internet Connection and Try again.",Toast.LENGTH_LONG).show();
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void signup() {

        if (!validate()) {
            onFailure();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Creating Account...");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        User user = new User();
        user.setName(_nameText.getText().toString());
        user.setEmail(_emailText.getText().toString());
        user.setPassword(_passwordText.getText().toString());
        user.setPhone(_phoneText.getText().toString());

        registerPresenter = new RegisterPresenter(user,this, this);
        registerPresenter.checkExistence();

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

        if ( !check.isEmpty() ){
            Toast.makeText(this,"This Email Is Registered Before.",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            _signupButton.setEnabled(true);
            return;
        }

        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent intent=new Intent(this,PostsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this,"Registration Failed, Please Check Your Internet Connection.",Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String phone = _phoneText.getText().toString();

        if (name.isEmpty() || name.length() < 4 || !name.matches("[a-zA-Z 0-9]+")) {
            _nameText.setError("at least 4 characters and no special characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || !password.matches("[a-zA-Z 0-9]+")) {
            _passwordText.setError("at least 4 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if ( phone.isEmpty() || phone.length() < 4 ) {
            _phoneText.setError("at least 4 numbers");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        return valid;
    }
}
