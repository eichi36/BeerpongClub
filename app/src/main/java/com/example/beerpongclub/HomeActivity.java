package com.example.beerpongclub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.beerpongclub.Database.User;
import com.example.beerpongclub.Database.UserContainer;
import com.example.beerpongclub.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;

    private TextView textView_displayUsername;
    private TextView textView_displayEmail;
    private UserContainer userContainer;
    private FirebaseUser currentUser;


    private static final String TAG_ACTION_MENU = "ACTION_MENU_ERROR_ HOMEACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View hView = navigationView.getHeaderView(0);
        textView_displayUsername = (TextView)hView.findViewById(R.id.home_displayUsername);
        textView_displayEmail = (TextView)hView.findViewById(R.id.home_displayEMail);

        if(textView_displayUsername == null) {
            Log.e(TAG_ACTION_MENU, "displayUsername == null");

        } else {
            Log.d(TAG_ACTION_MENU, "displayUsername exists");
            textView_displayUsername.setText("IT WORKS");


        }

        //onSupportNavigateUp();
       /*
        User testUser = new User();
        UserContainer testContainer= new UserContainer();
        currentUser = mAuth.getCurrentUser();

        textView_displayEmail = (TextView)findViewById(R.id.home_displayUsername);
        textView_displayEmail.setText("Test");


        if(currentUser != null) {
        testContainer.getRetrieveReff(mAuth.getCurrentUser().getUid());


        //textView_displayEmail.setText(currentUser.getEmail());
        textView_displayEmail = (TextView) findViewById(R.id.home_displayUsername);
        //textView_displayEmail.setText(currentUser.getEmail());



            ValueEventListener valEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    textView_displayUsername.setText(user.getUsername());
                    Log.d(TAG_ACTION_MENU, "getUsername() has been called");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            testContainer.getRetrieveReff(currentUser.getUid()).addValueEventListener(valEventListener);
        }

*/
    }
    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        } else {
            setupNavigationDrawer();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();
        switch(ID) {
            case  R.id.action_logout:
                mAuth.signOut();
                Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            default:
                Log.e(TAG_ACTION_MENU, "Switch case -> default onOptionsItemSelected");

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
/*
        userContainer = new UserContainer();
        textView_displayEmail = (TextView)findViewById(home_displayEMail);
        textView_displayUsername = (TextView) findViewById(R.id.home_displayUsername);
        textView_displayEmail.setText(currentUser.getEmail());


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textView_displayUsername.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userContainer.getRetrieveReff(currentUser.getUid()).addValueEventListener(valueEventListener);

*/
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupNavigationDrawer() {
        userContainer = new UserContainer();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        textView_displayUsername = (TextView)hView.findViewById(R.id.home_displayUsername);
        textView_displayEmail = (TextView)hView.findViewById(R.id.home_displayEMail);

        textView_displayEmail.setText(currentUser.getEmail());


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textView_displayUsername.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userContainer.getRetrieveReff(currentUser.getUid()).addValueEventListener(valueEventListener);



    }
}
