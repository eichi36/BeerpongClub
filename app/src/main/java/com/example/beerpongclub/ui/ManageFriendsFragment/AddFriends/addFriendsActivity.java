package com.example.beerpongclub.ui.ManageFriendsFragment.AddFriends;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.beerpongclub.Database.FriendRequest;
import com.example.beerpongclub.Database.FriendRequestContainer;
import com.example.beerpongclub.Database.User;
import com.example.beerpongclub.Database.UserContainer;
import com.example.beerpongclub.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class addFriendsActivity extends AppCompatActivity  {

    private EditText editText_searchUsername;
    private CircleImageView button_startSearch;
    private RecyclerView mResultview;
    private Button button_sendLink;
    private UserContainer userContainer = new UserContainer();
    private DatabaseReference mUserReff = userContainer.getmReff_User();
    private UserViewAdapter adapter;
    private FirebaseUser current_user;

    private static final String TAG_SEARCH ="USER SEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        editText_searchUsername = (EditText)findViewById(R.id.editText_searchUser);
        button_startSearch = findViewById(R.id.imageButton_startSearch);
        button_startSearch.setOnClickListener(startSearchListener);
        setupRecyclerView();
        button_sendLink = (Button) findViewById(R.id.button_sendFriendInvitationLink);
        //TODO: button_sendLink.setOnClickListener(sendLinkListener);
        editText_searchUsername.addTextChangedListener(searchWatcher);

    }
    TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(editText_searchUsername.getText().toString() != "") firebaseUserSearch(editText_searchUsername.getText().toString());
        }
    };
    View.OnClickListener startSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            firebaseUserSearch(editText_searchUsername.getText().toString());

        }
    };

    private void setupRecyclerView() {
        mResultview = (RecyclerView)findViewById(R.id.recyclerView_showResult);
        mResultview.setHasFixedSize(true);
        mResultview.setLayoutManager(new LinearLayoutManager(this));

    }





    private void firebaseUserSearch(String searchStr) {
        Log.d(TAG_SEARCH, "firebaseUserSearch called");
        Query query = userContainer.getmReff_User().orderByChild("username").startAt(searchStr).endAt(searchStr + "\uf8ff");
        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new UserViewAdapter(options);
        adapter.setContext(this);
        adapter.startListening();
        mResultview.setAdapter(adapter);



        adapter.setOnItemClickListener(new UserViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user, int position) {
                createShowProfileDialog(user);
            }
        });

    }

    private void createShowProfileDialog(final User user) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(addFriendsActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_show_user, null);

        final ImageView profilePic = mView.findViewById(R.id.dialogaddFriend_ProfilePic);
        final TextView textView_username = mView.findViewById(R.id.textView_DisplayUsername_Dialog);
        final Button button_sendFriendRequest = mView.findViewById(R.id.button_sendFriendRequest);
        final FriendRequestContainer fq = new FriendRequestContainer(new FriendRequest( FriendRequestContainer.getStateSent(), current_user.getUid()),user.getUid());

        button_sendFriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(button_sendFriendRequest.getText().toString().equals(getString(R.string.cancelFriendRequest_buttonDialogAddFriend)) ){
                    fq.deleteElement();
                    Log.d(TAG_SEARCH, "Element removed");
                } else if(button_sendFriendRequest.getText().toString().equals(getString(R.string.send_friend_request_dialog_show_user))){
                    fq.pushElement();
                }

            }
        });

        final ValueEventListener stateFriendRequestListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null && dataSnapshot.hasChild(current_user.getUid())) {
                    if(dataSnapshot.child(current_user.getUid()).child("state").getValue(String.class).equals(FriendRequestContainer.getStateSent())) {
                        button_sendFriendRequest.setText(R.string.cancelFriendRequest_buttonDialogAddFriend);
                    } else {
                        button_sendFriendRequest.setText(R.string.declinedFriendRequest_buttonDialogAddFriend);
                        button_sendFriendRequest.setEnabled(false);
                    }
                } else if(dataSnapshot != null &&!dataSnapshot.hasChild(current_user.getUid()) ) {
                    button_sendFriendRequest.setText(R.string.send_friend_request_dialog_show_user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        fq.getUid_to_reff().addValueEventListener(stateFriendRequestListener);

        Glide.with(getApplicationContext()).load(user.getprofile_pic_uri()).into(profilePic);
        textView_username.setText(user.getUsername());

        mBuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fq.getUid_to_reff().removeEventListener(stateFriendRequestListener);

            }
        });
        mBuilder.setView(mView);
        mBuilder.setTitle(user.getUsername());
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

    }


}
