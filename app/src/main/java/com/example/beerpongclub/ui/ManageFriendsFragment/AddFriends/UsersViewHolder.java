package com.example.beerpongclub.ui.ManageFriendsFragment.AddFriends;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beerpongclub.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersViewHolder extends RecyclerView.ViewHolder {
    private TextView textView_username;
    private TextView textView_email;
    private CircleImageView profile_image;


    View mView;
    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        textView_username = (TextView)  mView.findViewById(R.id.textView_DisplayUsername);
        textView_email = (TextView) mView.findViewById(R.id.textView_displayShorEMail);
        profile_image = mView.findViewById(R.id.circleImageView_ProfilePic);




    }



    public void setDetails(Context context, String username, String EMail, String imageUri) {

        textView_username.setText(username);
        textView_email.setText(EMail);

        Glide.with(context).load(imageUri).into(profile_image);


    }
}

