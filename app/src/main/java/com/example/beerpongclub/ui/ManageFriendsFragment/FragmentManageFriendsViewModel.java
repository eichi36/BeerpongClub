package com.example.beerpongclub.ui.ManageFriendsFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FragmentManageFriendsViewModel extends ViewModel {

    private MutableLiveData<String> mTest;

    public FragmentManageFriendsViewModel() {
        mTest = new MutableLiveData<>();
        mTest.setValue("Test");
    }
    public LiveData<String> getTest() {return mTest;}
}
