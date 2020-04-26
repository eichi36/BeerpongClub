package com.example.beerpongclub.ui.ManageFriendsFragment.FriendRequest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.beerpongclub.Database.FriendRequest;
import com.example.beerpongclub.Database.UserContainer;
import com.example.beerpongclub.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FriendRequestAdapter extends FirebaseRecyclerAdapter<FriendRequest, FriendRequestViewHolder> {
    private Context context;

    private static final String TAG_ERROR = "FriendRequestAdapter ERROR";

    public FriendRequestAdapter(@NonNull FirebaseRecyclerOptions<FriendRequest> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final FriendRequestViewHolder holder, int position, @NonNull FriendRequest model) {
        if(context == null) { Log.e(TAG_ERROR, "Context must be set"); return;}
        String uid_from = model.getUid_from();
        UserContainer userContainer = new UserContainer(uid_from);
        holder.setContext(context);
        userContainer.getUsername_reff().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.setUsername(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userContainer.getEmail_reff().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.setEmail(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        userContainer.getPro_pic_url_reff().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.setUri(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_displayfriendrequest, parent, false);
        return new FriendRequestViewHolder(view);
    }
    public void setContext(Context context) {
        this.context  = context;
    }


}
