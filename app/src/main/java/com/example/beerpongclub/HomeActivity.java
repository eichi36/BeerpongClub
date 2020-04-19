package com.example.beerpongclub;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;

    private TextView textView_displayUsername;
    private TextView textView_displayEmail;
    private CircleImageView mImageViewProfile;
    private UserContainer userContainer;
    private FirebaseUser currentUser;


    private static final String TAG_ACTION_MENU = "ACTION_MENU_ERROR_ HOMEACTIVITY";
    private static final String TAG_DIALOG_USERNAME = "DIALOG_CHANGE_USERNAME";
    private static final String TAG_DIALOG_PASSWORD = "DIALOG_CHANGE_PASSWORD";

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
                R.id.nav_home, R.id.nav_action_settings, R.id.nav_statistics, R.id.nav_manageFriends)
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
            textView_displayUsername.setText(R.string.default_username_home_displayUsername);
            textView_displayEmail.setText(R.string.default_EMail_home_displayEMail);

        }

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
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupNavigationDrawer() {
        //set up the Navigation Drawer with Username and EMail

        userContainer = new UserContainer();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        textView_displayUsername = (TextView)hView.findViewById(R.id.home_displayUsername);
        textView_displayEmail = (TextView)hView.findViewById(R.id.home_displayEMail);
        mImageViewProfile = (CircleImageView)hView.findViewById(R.id.imageView_ProfilePicture);
        textView_displayEmail.setText(currentUser.getEmail());


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                textView_displayUsername.setText(user.getUsername());
                Picasso.get().load(user.getprofile_pic_uri()).into(mImageViewProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userContainer.getRetrieveReff(currentUser.getUid()).addValueEventListener(valueEventListener);



    }

    public void onChangeUsername(View view) {
        //AlertDialog corresponding to the Account Settings Fragment
        //must be within an activity


        //create new AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_change_username, null);

        final EditText editText_changeUsername = (EditText) mView.findViewById(R.id.dialog_changeUsername);


        //setting the AlertDialogs Attributes and Buttons
        mBuilder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText_changeUsername != null) {
                    updateUsername(editText_changeUsername.getText().toString());
                    Log.d(TAG_DIALOG_USERNAME, "onClick positive Button");

                }

            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mBuilder.setView(mView);
        mBuilder.setTitle("Change Username");

        //create and show AlertDialog
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        //adding behavior (Textwatcher, etc) to the AlertDialog
        final Button button_pos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button_pos.setEnabled(false);

        TextWatcher afterChanged = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = editText_changeUsername.getText().toString();
                if(str.length() < 4) {
                    editText_changeUsername.setError(getString(R.string.username_too_short_signIn));
                    button_pos.setEnabled(false);
                }
                if(str.length() >= 25) {
                    editText_changeUsername.setError(getString(R.string.username_too_long_singIn));
                    button_pos.setEnabled(false);
                } else if(str.length() >= 4 && str.length() < 25){
                    button_pos.setEnabled(true);
                }
            }
        };
        if(editText_changeUsername == null) {
            Log.e(TAG_ACTION_MENU, "editText_changeUsername == null");
        }
        editText_changeUsername.addTextChangedListener(afterChanged);

    }


   //<FRAGMENT ACCOUNT SETTINGS
    private void updateUsername(final String username) {

        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    UserContainer userUpdate = new UserContainer(dataSnapshot.getValue(User.class));
                    userUpdate.setUserUsername(username);
                    userUpdate.updateElement();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userContainer.getRetrieveReff(currentUser.getUid()).addListenerForSingleValueEvent(valueEventListener);

    }

    private void updatePassword(final String password) {
        FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(password);
        Log.d(TAG_DIALOG_PASSWORD, "Updatet password successfully");

        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    UserContainer userUpdate = new UserContainer(dataSnapshot.getValue(User.class));
                    userUpdate.setUserPassword(password);
                    userUpdate.updateElement();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userContainer.getRetrieveReff(currentUser.getUid()).addListenerForSingleValueEvent(valueEventListener);


    }

    public void onChangePassword(View view) {
        //onChangePassword from fragment_account_xml
        //searches in the database if a password exist
        //if not then the user is logged in with Google-Sign in -> the only way to change his password is via google
        //if a password has been found the user should be able to change his password



        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!= null) {
                    User user = dataSnapshot.getValue(User.class);

                    if(user.getPassword() == null) {
                        createAlert_GoogleSignIn();
                    } else {
                        createAlert(user.getPassword());
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        UserContainer u = new UserContainer();
        u.getRetrieveReff(currentUser.getUid()).addListenerForSingleValueEvent(valueEventListener);


    }

    private void createAlert(final String password) {
        //creates a Dialog that a user can change his password

        Log.d(TAG_DIALOG_PASSWORD, "password = " + password);

        final String CONFIRM_OLD_PASSWORD = "confirm_old_password";
        final String SELECT_NEW_PASSWORD = "select_new_password";

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);

        final EditText editText_oldPassword = (EditText) mView.findViewById(R.id.old_password_dialog_editText);
        final EditText editText_newPassword = (EditText) mView.findViewById(R.id.new_password_dialog_editText);
        final EditText editText_confirmNewPassword = (EditText) mView.findViewById(R.id.confirm_new_password_dialog_editText);
        final TextView textView_state = (TextView) mView.findViewById(R.id.textView_state);

        editText_newPassword.setEnabled(false);
        editText_confirmNewPassword.setEnabled(false);
        textView_state.setText(CONFIRM_OLD_PASSWORD);



        //setting the AlertDialogs Attributes and Buttons
        mBuilder.setPositiveButton("Apply Changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        mBuilder.setNeutralButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mBuilder.setView(mView);
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        mBuilder.setTitle("Change Password");

        final AlertDialog dialog  = mBuilder.create();
        dialog.show();

        final Button button_pos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button button_neut = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        button_pos.setEnabled(false);
        button_neut.setEnabled(true);

        button_neut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView_state.getText().toString().equals(CONFIRM_OLD_PASSWORD)) {
                    if(editText_oldPassword.getText().toString().equals(password)){
                        editText_oldPassword.setEnabled(false);
                        editText_newPassword.setEnabled(true);
                        editText_confirmNewPassword.setEnabled(true);
                        textView_state.setText(SELECT_NEW_PASSWORD);
                        button_pos.setEnabled(false);
                        button_neut.setText("Back");
                    } else {
                        editText_oldPassword.setError("False Password!");
                    }
                } else if(textView_state.getText().toString().equals(SELECT_NEW_PASSWORD))  {
                    editText_confirmNewPassword.setEnabled(false);
                    editText_newPassword.setEnabled(false);
                    editText_oldPassword.setEnabled(true);
                    button_pos.setEnabled(false);
                    button_neut.setText("Next");

                    textView_state.setText(CONFIRM_OLD_PASSWORD);
                }
            }
        });

        button_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_confirmNewPassword.getText().toString().equals(editText_newPassword.getText().toString())){
                   updatePassword(editText_newPassword.getText().toString());
                   dialog.dismiss();
                } else {
                    editText_confirmNewPassword.setError(getString(R.string.password_missmatch_signIn));
                }
            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = editText_newPassword.getText().toString();

                if(str.length() < 6) {
                    editText_newPassword.setError(getString(R.string.password_too_short_signIn));
                    button_pos.setEnabled(false);
                } else if(str.length() >= 6) {
                    button_pos.setEnabled(true);
                }
            }
        };

        editText_newPassword.addTextChangedListener(textWatcher);

    }

    private void createAlert_GoogleSignIn() {
        //creates and shows the dialog, that a user can change his password only on his google account

        //create new AlertDialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_change_password_google_signin, null);

        //setting the AlertDialogs Attributes and Buttons
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        mBuilder.setView(mView);
        mBuilder.setTitle("Password can not be changed");

        //create and show AlertDialog
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }
    //FRAGMENT ACCOUNT SETTINGS >

}


