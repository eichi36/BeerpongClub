package com.example.beerpongclub.Database;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.annotation.Nullable;

public  class UserContainer implements  DatabaseInterface{

    private final static String TAG_GET_USER = "Snapshot.GetUser";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mReff_User = database.getReference("User");
    private DatabaseReference username_reff;
    private DatabaseReference email_reff;
    private DatabaseReference password_reff;
    private DatabaseReference pro_pic_url_reff;

    private static final String TAG_UPDATE = "UPDATE_DEBUG";
    private static final String TAG_USERCONTAINER_ERROR ="USERCONATINER ERROR";
    private User user_element;

    @Nullable
    private User user_searchedFor;

    public UserContainer(){}

    public UserContainer(String uid) {
        user_element = new User(null, null, null, uid, null);
        if(uid == null) {
            Log.e(TAG_USERCONTAINER_ERROR, "uid == null");
        } else {
            username_reff = mReff_User.child(user_element.getUid()).child("username");
            email_reff = mReff_User.child(user_element.getUid()).child("email");
            password_reff = mReff_User.child(user_element.getUid()).child("password");
            pro_pic_url_reff = mReff_User.child(user_element.getUid()).child("profile_pic_uri");
        }
    }

    public UserContainer(User user_element) {
        this.user_element = user_element;
    }

    public User getUser_element() {
        return user_element;
    }

    public void setUser_element(User user_element) {
        this.user_element = user_element;
    }

    @Override
    public void pushElement() {
        mReff_User.child(user_element.getUid()).setValue(user_element);
    }

    public void setUser_searchedFor(@Nullable User user){this.user_searchedFor = user;}

    public User getUser_searchedFor(){return this.user_searchedFor;}

    public void setUserUsername(String str) { user_element.setusername(str);}

    public void setUserPassword(String str) { user_element.setpassword(str);}

    public static String getTagGetUser() {
        return TAG_GET_USER;
    }
    public DatabaseReference getmReff_User() {return mReff_User;}

    public DatabaseReference getUsername_reff() {
        return username_reff;
    }

    public DatabaseReference getEmail_reff() {
        return email_reff;
    }

    public DatabaseReference getPassword_reff() {
        return password_reff;
    }

    public DatabaseReference getPro_pic_url_reff() {
        return pro_pic_url_reff;
    }

    public DatabaseReference getRetrieveReff(String id){
        DatabaseReference getUserReff = mReff_User.child(id);
        return getUserReff;
    }

    @Override
    public void updateElement() {
        String key = user_element.getUid();

        //Map<String, Object> childUpdates = new HashMap<>();
        if(user_element.getEMail() != null) {
            mReff_User.child(user_element.getUid()).child("email").setValue(user_element.getEMail());
            Log.d(TAG_UPDATE, user_element.getEMail());
        }
        if(user_element.getUid() != null) {
            mReff_User.child(user_element.getUid()).child("uid").setValue(user_element.getUid());
            Log.d(TAG_UPDATE, user_element.getUid());
        }
        if(user_element.getPassword() != null) {
            mReff_User.child(user_element.getUid()).child("password").setValue(user_element.getPassword());
            Log.d(TAG_UPDATE, user_element.getPassword());
        }
        if(user_element.getUsername() != null) {
            mReff_User.child(user_element.getUid()).child("username").setValue(user_element.getUsername());
            Log.d(TAG_UPDATE, user_element.getUsername());
        }
        if(user_element.getprofile_pic_uri() != null) {
            mReff_User.child(user_element.getUid()).child("profile_pic_uri").setValue(user_element.getprofile_pic_uri());
            Log.d(TAG_UPDATE, user_element.getprofile_pic_uri());
        }
    }





}
