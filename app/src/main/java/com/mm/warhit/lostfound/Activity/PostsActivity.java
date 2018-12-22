package com.mm.warhit.lostfound.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mm.warhit.lostfound.Adapter.PostsAdapter;
import com.mm.warhit.lostfound.Model.Post;
import com.mm.warhit.lostfound.Presenter.PostPresenter;
import com.mm.warhit.lostfound.R;
import com.mm.warhit.lostfound.View.PostView;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PostView {

    NavigationView navigationView;
    PostsAdapter postsAdapter;
    RecyclerView RV;
    PostPresenter postPresenter;
    RecyclerView.LayoutManager layoutManager;
    String email, name, itemDescription, itemSerialNumber;
    EditText ET_description, ET_serialNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        RV = findViewById(R.id.RV);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( isNetworkAvailable(getApplicationContext()) ) {

                    SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
                    email = sharedPreferences.getString("email", "");
                    name = sharedPreferences.getString("name", "");
                    if (!email.equals("")) {

                        final View viewInflated = LayoutInflater.from(PostsActivity.this).inflate(R.layout.post_dialogbox, null);

                        final AlertDialog dialog = new AlertDialog.Builder(PostsActivity.this)
                                .setView(viewInflated)
                                .setTitle("Post Found Item")
                                .setPositiveButton("Post", null) //Set to null. We override the onclick
                                .setNegativeButton("Cancel", null)
                                .create();

                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                            @Override
                            public void onShow(final DialogInterface dialog) {

                                Button ok = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                Button cancel = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                                ok.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        ET_description = viewInflated.findViewById(R.id.item_description);
                                        ET_serialNumber = viewInflated.findViewById(R.id.item_serialNumber);

                                        itemDescription = ET_description.getText().toString();
                                        itemSerialNumber = ET_serialNumber.getText().toString();
                                        if ( validate() ) {
                                            dialog.cancel();

                                            //Todo: add user email instead of name and then query on email to get the name
                                            Post post = new Post();
                                            post.setAuthor(name);
                                            post.setDescription(itemDescription);
                                            post.setSerialNumber(itemSerialNumber);
                                            //Todo: add Spinner
//                                            post.setCategory();
                                            //Todo: add Image Uploading Function
                                            postPresenter.addPost(post);
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Please Check your Input",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                            }
                        });
                        dialog.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Login First", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();
                }
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        postPresenter = new PostPresenter(this, this);
        postPresenter.getPosts();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_posts) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_login) {

        } else if (id == R.id.nav_register) {
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_signOut) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getAllPosts(ArrayList<Post> posts) {
        layoutManager = new LinearLayoutManager(this);
        RV.setLayoutManager(layoutManager);
        postsAdapter = new PostsAdapter(posts, this);
        RV.setAdapter(postsAdapter);
        postsAdapter.notifyDataSetChanged();

    }

    @Override
    public void addMyPost() {
        Toast.makeText(this,"Your Post Has Been Uploaded Successfully.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRetrieveFailure() {
        Toast.makeText(this,"No Posts To View Right Now.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddingFailure() {
        Toast.makeText(this,"Failed To Add Your Post, Please Try Again Later.",Toast.LENGTH_LONG).show();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean validate() {
        boolean valid = true;

        if ( itemDescription.isEmpty() || itemDescription.length() < 4 ) {
            ET_description.setError("at least 4 characters required");
            valid = false;
        } else {
            ET_description.setError(null);
        }

        if (itemSerialNumber.isEmpty() || itemSerialNumber.length() < 4 ) {
            ET_serialNumber.setError("at least 4 characters required");
            valid = false;
        } else {
            ET_serialNumber.setError(null);
        }

        return valid;
    }
}
