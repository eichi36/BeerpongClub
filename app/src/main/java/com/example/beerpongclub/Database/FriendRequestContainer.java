package com.example.beerpongclub.Database;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FriendRequestContainer implements DatabaseInterface {
    private FriendRequest friendRequest_element;
    private String UID_TO; // = Uid of the user which receives the friendRequest


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mReff = database.getReference("FriendRequest");
    private DatabaseReference uid_from_reff;
    private DatabaseReference uid_to_reff;
    private DatabaseReference state_reff;

    private static final String STATE_SENT ="SENT";
    private static final String STATE_DECLINED = "DECLINED";


    private static final  String TAG_ERROR ="FRIEND_REQUEST_CONTAINER";


    public FriendRequestContainer(String UID_TO) {
        this.friendRequest_element = new FriendRequest(null, UID_TO);
        this.UID_TO = UID_TO;
        setupReffs();
    }

    public FriendRequestContainer(FriendRequest friendRequest, String UID_TO) {
        this.friendRequest_element = friendRequest;
        this.UID_TO= UID_TO;
        setupReffs();

    }
    private void setupReffs() {
        if(UID_TO != null) {
            uid_to_reff = mReff.child(UID_TO);

            uid_from_reff = uid_to_reff.child(friendRequest_element.getUid_from());
            state_reff = uid_from_reff.child("state");
        } else {
            Log.e(TAG_ERROR, "UID == null; Set an Uid first");
        }


    }

    public String getUID_TO() {
        return UID_TO;
    }

    public void setUID_TO(String UID_TO) {
        this.UID_TO = UID_TO;
    }

    public FriendRequest getFriendRequest_element() {
        return friendRequest_element;
    }

    public DatabaseReference getmReff() {
        return mReff;
    }

    public DatabaseReference getUid_from_reff() {
        return uid_from_reff;
    }

    public DatabaseReference getState_reff() {
        return state_reff;
    }

    public DatabaseReference getUid_to_reff() {
        return uid_to_reff;
    }

    public void setState(String state) {
        friendRequest_element.setState(state);
    }

    public static String getStateSent() {
        return STATE_SENT;
    }

    public static String getStateDeclined() {
        return STATE_DECLINED;
    }



    public FriendRequestContainer(){}
    @Override
    public void pushElement() {
        uid_from_reff.setValue(friendRequest_element);
    }

    @Override
    public void updateElement() {
        if(this.UID_TO == null) {
            Log.e(TAG_ERROR, "Uid is not set; You must set an UID first");
            return;
        } else {
            if(friendRequest_element.getState() != null) {
                state_reff.setValue(friendRequest_element.getState());
            }
        }
    }

    @Override
    public void deleteElement() {
        if(UID_TO == null) {Log.e(TAG_ERROR, "Uid is not set; You must set an UID first");return;}

        uid_from_reff.removeValue();

    }


}
