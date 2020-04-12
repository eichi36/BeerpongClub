package com.example.beerpongclub;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignInViewModel extends ViewModel {

    private MutableLiveData<SignInFormState> signinFormState = new MutableLiveData<>();
    private SignInChecker signinchecker = new SignInChecker();


    public void signInDataChanged(String email, String password, String password_repeat, String username) {
        signinchecker.setAtt(email, password, password_repeat, username);
        signinFormState.setValue(signinchecker.check_input());
    }

    LiveData<SignInFormState> getSignInFormState() {return signinFormState;}

}