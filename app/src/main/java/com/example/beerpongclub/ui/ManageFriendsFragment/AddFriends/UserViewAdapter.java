package com.example.beerpongclub.ui.ManageFriendsFragment.AddFriends;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerpongclub.Database.User;
import com.example.beerpongclub.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UserViewAdapter extends FirebaseRecyclerAdapter<User, UsersViewHolder> {
    private Context context;
    private OnItemClickListener listener;



    private static final String TAG_ERROR = "UserViewAdapter ERROR";
    public UserViewAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
    }

    public void setContext(Context context) {
        //setting the Context to load the ImageView
        this.context = context;
    }



    @Override
    protected void onBindViewHolder(@NonNull final UsersViewHolder holder, int position, @NonNull final User model) {
        if(context == null) { Log.e(TAG_ERROR, "Context must be set"); return; }
        holder.setDetails(context, model.getUsername(), model.getEMail(), model.getprofile_pic_uri());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(model, position);
                }
            }
        });
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_displayuser,parent,false);
        return new UsersViewHolder(view);

    }
    public interface OnItemClickListener {
        void onItemClick(User user, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
