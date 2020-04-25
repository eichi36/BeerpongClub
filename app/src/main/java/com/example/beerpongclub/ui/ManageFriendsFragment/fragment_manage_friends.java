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

import com.example.beerpongclub.R;
import com.example.beerpongclub.ui.ManageFriendsFragment.AddFriends.addFriendsActivity;

public class fragment_manage_friends extends Fragment {

    private FragmentManageFriendsViewModel mViewModel;

    public static fragment_manage_friends newInstance() {
        return new fragment_manage_friends();
    }

    private Button button_addFriends;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manage_friends_fragment, container, false);
        button_addFriends = (Button)root.findViewById(R.id.button_addFriends_fragmentmanagefriends);
        mViewModel = new ViewModelProvider(this).get(FragmentManageFriendsViewModel.class);
        button_addFriends.setOnClickListener(button_addFriendsListener);
        return root;
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
