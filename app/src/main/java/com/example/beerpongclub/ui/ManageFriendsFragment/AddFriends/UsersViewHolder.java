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
    View mView;
    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    public void setDetails(Context context, String username, String EMail, String imageUri) {

        TextView textView_username = (TextView)  mView.findViewById(R.id.textView_DisplayUsername);
        TextView textView_email = (TextView) mView.findViewById(R.id.textView_displayShorEMail);

        CircleImageView profile_image = mView.findViewById(R.id.circleImageView_ProfilePic);

        textView_username.setText(username);
        textView_email.setText(EMail);

        Glide.with(context).load(imageUri).into(profile_image);


    }
}
