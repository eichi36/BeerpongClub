package com.example.beerpongclub.ui.ManageFriendsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerpongclub.Database.FriendRequest;
import com.example.beerpongclub.Database.FriendRequestContainer;
import com.example.beerpongclub.R;
import com.example.beerpongclub.ui.ManageFriendsFragment.AddFriends.addFriendsActivity;
import com.example.beerpongclub.ui.ManageFriendsFragment.FriendRequest.FriendRequestAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

public class fragment_manage_friends extends Fragment {

    private FragmentManageFriendsViewModel mViewModel;

    public static fragment_manage_friends newInstance() {
        return new fragment_manage_friends();
    }

    private Button button_addFriends;
    private RecyclerView recyclerView_FriendRequest;
    private FriendRequestContainer friendRequest;
    private FirebaseUser currentUser;
    private FriendRequestAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manage_friends_fragment, container, false);
        button_addFriends = (Button)root.findViewById(R.id.button_addFriends_fragmentmanagefriends);
        mViewModel = new ViewModelProvider(this).get(FragmentManageFriendsViewModel.class);
        button_addFriends.setOnClickListener(button_addFriendsListener);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        setupRecyclerView_FriendRequest(root);
        return root;
    }

    private void setupRecyclerView_FriendRequest(View mView) {
        recyclerView_FriendRequest = mView.findViewById(R.id.recyclerView_showFriendRequests);
        recyclerView_FriendRequest.setHasFixedSize(true);
        recyclerView_FriendRequest.setLayoutManager(new LinearLayoutManager(getContext()));
        friendRequest = new FriendRequestContainer(currentUser.getUid());
        Query query = friendRequest.getUid_to_reff();
        FirebaseRecyclerOptions<FriendRequest> options  = new FirebaseRecyclerOptions.Builder<FriendRequest>()
                .setQuery(query, FriendRequest.class)
                .build();
        adapter = new FriendRequestAdapter(options);
        adapter.setContext(getContext());
        adapter.startListening();
        recyclerView_FriendRequest.setAdapter(adapter);
    }


    View.OnClickListener button_addFriendsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent addFriendsIntent = new Intent(getContext(), addFriendsActivity.class);
            startActivity(addFriendsIntent);
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
