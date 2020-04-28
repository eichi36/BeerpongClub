package com.example.beerpongclub.ui.ManageFriendsFragment.FriendRequest;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beerpongclub.Database.FriendRequestContainer;
import com.example.beerpongclub.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestViewHolder extends RecyclerView.ViewHolder {
    private TextView textView_username;
    private TextView textView_email;
    private CircleImageView profile_image;
    private Context context;

    private FriendRequestContainer friendRequest;
    private ImageView button_accept;
    private ImageView button_decline;

    public View mView;
    public FriendRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        textView_username = (TextView)  mView.findViewById(R.id.textView_DisplayUsername);
        textView_email = (TextView) mView.findViewById(R.id.textView_displayShortEMail);
        profile_image = mView.findViewById(R.id.circleImageView_ProfilePic);
        button_accept = mView.findViewById(R.id.button_acceptFriendRequest);
        button_decline = mView.findViewById(R.id.button_declineFriendRequset);

        button_accept.setOnClickListener(new View.OnClickListener() {

            //TODO: Replace with Database Interaction
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Accept", Toast.LENGTH_SHORT).show();
            }
        });

        button_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Decline", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public ImageView getButton_accept() {return  this.button_accept;}
    public ImageView getButton_decline(){return  this.button_decline;}

    public void setUsername (String username) {
        textView_username.setText(username);
    }

    public void setEmail (String Email) {
        textView_email.setText(Email);
    }

    public void setUri (String Uri) {
        Glide.with(context).load(Uri).into(profile_image);
    }
    public void setDetails(final Context context, String username, String EMail, String imageUri) {
        textView_username.setText(username);
        textView_email.setText(EMail);

        Glide.with(context).load(imageUri).into(profile_image);


    }
    public void setContext(Context context) {this.context = context;}


}
