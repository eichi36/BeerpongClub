package com.example.beerpongclub.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public  class UserContainer implements  DatabaseInterface{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mReff_User = database.getReference("User");

    private User user_element;

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
}
