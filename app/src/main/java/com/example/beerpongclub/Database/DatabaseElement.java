package com.example.beerpongclub.Database;

import com.google.firebase.database.DatabaseReference;

public abstract class DatabaseElement {
    public DatabaseReference mReff;



    public DatabaseElement() {

    }

    protected  void setmReff(DatabaseReference reff) { this.mReff = reff;}

    public void push() {
        if(mReff != null) {
            mReff.push().setValue(this);
        }
    }

    public DatabaseReference getmReff() { return mReff;}
}
