package com.example.beerpongclub.ui.AccountSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountSettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AccountSettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Account Settings fragment");
    }
    public LiveData<String> getText() {return mText;}

}
