package com.example.beerpongclub.Database;

public class FriendRequest {

    private String uid_from;
    private String state; //state == sent || state == declined

    public FriendRequest() {}

    public FriendRequest( String state, String uid) {
        this.state = state;
        this.uid_from = uid;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUid_from() {
        return uid_from;
    }

    public void setUid_from(String uid_from) {
        this.uid_from = uid_from;
    }
}
