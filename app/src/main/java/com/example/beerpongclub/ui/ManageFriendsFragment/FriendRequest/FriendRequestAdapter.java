package com.example.beerpongclub.ui.ManageFriendsFragment.FriendRequest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    private static final String TAG_INFO = "FriendRequestAdapter INFOR";
    private OnItemClickListener_fq decline_listener;

    public FriendRequestAdapter(@NonNull FirebaseRecyclerOptions<FriendRequest> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final FriendRequestViewHolder holder, final int position, @NonNull final FriendRequest model) {
        if(context == null) { Log.e(TAG_ERROR, "Context must be set"); return;}
        String uid_from = model.getUid_from();
        if(uid_from == null) {
            Log.e(TAG_ERROR, "uid_from == null");
            return;
        }
        UserContainer userContainer = new UserContainer(uid_from);
        holder.setContext(context);
        if(userContainer.getmReff_User().child(uid_from) != null) {
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
        } else {
            Log.i(TAG_INFO, "UID_TO not existing -> the user has no friend requests");
        }

        holder.getButton_decline().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION && decline_listener != null) {
                    decline_listener.onItemClick_decline(model, positon);
                }
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

    public interface OnItemClickListener_fq {
        void onItemClick_decline(FriendRequest friendRequest, int positon);
    }

    public  void setOnItemClickListener_fq(OnItemClickListener_fq listener_fq) {
        this.decline_listener = listener_fq;
    }

}
