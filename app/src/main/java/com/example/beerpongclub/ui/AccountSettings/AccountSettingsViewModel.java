package com.example.beerpongclub.ui.AccountSettings;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.beerpongclub.Database.UserContainer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class AccountSettingsViewModel extends ViewModel {

    private MutableLiveData<String> mUsername;
    private MutableLiveData<String> mEMail;
    private MutableLiveData<String> mImageUri;
    private FirebaseUser  mCurrentUser;
    private DatabaseReference username_reff;

    private static final String TAG_VALUE_EVENT_LISTENER = "VALUE_EVENT";

    public AccountSettingsViewModel() {
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mUsername = new MutableLiveData<>();
        mEMail = new MutableLiveData<>();
        mImageUri = new MutableLiveData<>();
        assert mCurrentUser != null;
        String id = mCurrentUser.getUid();
        assert id != null;
        UserContainer userContainer = new UserContainer(id);
        username_reff = userContainer.getUsername_reff();
        Log.d(TAG_VALUE_EVENT_LISTENER, "ViewModel constructor has been called");


        ValueEventListener username_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                mUsername.setValue(str);
                Log.d(TAG_VALUE_EVENT_LISTENER, "ViewModel onChanged  Username: " + str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ValueEventListener email_listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                mEMail.setValue(str);
                Log.d(TAG_VALUE_EVENT_LISTENER, "ViewModel onChanged  EMail: " + str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ValueEventListener imageUri_listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.getValue(String.class);
                mImageUri.setValue(str);
                Log.d(TAG_VALUE_EVENT_LISTENER, "ViewModel onChanged ImageUri: " + str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        userContainer.getUsername_reff().addValueEventListener(username_Listener);
        userContainer.getEmail_reff().addValueEventListener(email_listener);
        userContainer.getPro_pic_url_reff().addValueEventListener(imageUri_listener);

    }


    public LiveData<String> getUsername() {return mUsername;}
    public LiveData<String> getEMail(){return mEMail;}
    public LiveData<String> getImageUri(){return mImageUri;}

}
