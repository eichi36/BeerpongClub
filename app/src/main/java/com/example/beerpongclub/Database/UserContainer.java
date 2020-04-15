package com.example.beerpongclub.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nullable;

public  class UserContainer implements  DatabaseInterface{

    private final static String TAG_GET_USER = "Snapshot.GetUser";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mReff_User = database.getReference("User");


    private static final String TAG_UPDATE = "UPDATE_DEBUG";
    private User user_element;

    @Nullable
    private User user_searchedFor;

    public UserContainer(){
        user_searchedFor = new User();
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
    }


    public String getUserByUid(final String id) {
        DatabaseReference getUserReff = mReff_User.child(id);
        final String[] username = new String[1];
        User u;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                setUser_searchedFor(dataSnapshot.getValue(User.class));
                Log.d(TAG_GET_USER, "Test: "+ user_searchedFor.getUsername());
                if(u == null) {
                    Log.e(TAG_GET_USER,"User is Null");
                } else {
                    user_searchedFor.setusername(u.getUsername());
                    Log.d(TAG_GET_USER, u.getUid());
                    Log.d(TAG_GET_USER, u.getEMail());
                    Log.d(TAG_GET_USER, u.getUsername());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        Log.d(TAG_GET_USER, "Return Value: " + user_searchedFor.getUsername());

        getUserReff.addListenerForSingleValueEvent(valueEventListener);
        return "Test";
    }
}
