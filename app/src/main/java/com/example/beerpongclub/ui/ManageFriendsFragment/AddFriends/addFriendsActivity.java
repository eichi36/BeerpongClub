package com.example.beerpongclub.ui.ManageFriendsFragment.AddFriends;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerpongclub.Database.User;
import com.example.beerpongclub.Database.UserContainer;
import com.example.beerpongclub.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class addFriendsActivity extends AppCompatActivity {

    private EditText editText_searchUsername;
    private CircleImageView button_startSearch;
    private RecyclerView mResultview;
    private Button button_sendLink;
    private UserContainer userContainer = new UserContainer();
    private DatabaseReference mUserReff = userContainer.getmReff_User();

    private static final String TAG_SEARCH ="USER SEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        editText_searchUsername = (EditText)findViewById(R.id.editText_searchUser);
        button_startSearch = findViewById(R.id.imageButton_startSearch);
        button_startSearch.setOnClickListener(startSearchListener);
        mResultview = (RecyclerView)findViewById(R.id.recyclerView_showResult);
        mResultview.setHasFixedSize(true);
        mResultview.setLayoutManager(new LinearLayoutManager(this));
        button_sendLink = (Button) findViewById(R.id.button_sendFriendInvitationLink);
        button_sendLink.setOnClickListener(sendLinkListener);
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

    View.OnClickListener sendLinkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //to be add
        }
    };

    private void firebaseUserSearch(String searchStr) {
        Log.d(TAG_SEARCH, "firebaseUserSearch called");
        Query query = userContainer.getmReff_User().orderByChild("username").startAt(searchStr).endAt(searchStr + "\uf8ff");
        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();


        FirebaseRecyclerAdapter<User, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(options ) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull final User model) {

                holder.setDetails(getApplicationContext(), model.getUsername(), model.getEMail(), model.getprofile_pic_uri());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), model.getUsername(), Toast.LENGTH_LONG).show();
                    }
                });

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_displayuser,parent,false);
                return new UsersViewHolder(view);
            }
            @Override
            public void onDataChanged() {
                Log.d(TAG_SEARCH, "onDataChanged");

            }
            @Override
            public void onError(DatabaseError e) {
                Log.e(TAG_SEARCH, e.getDetails());
            }
        };
        firebaseRecyclerAdapter.startListening();
        mResultview.setAdapter(firebaseRecyclerAdapter);

    }
}
