package com.example.beerpongclub.SignIn;

import androidx.annotation.Nullable;


public class SignInFormState {
    //this class saves the error of every input entry
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer usernameError;

    private boolean isDataValid;

    public SignInFormState(@Nullable Integer emailError, @Nullable Integer passwordError, @Nullable Integer usernameErroro) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.usernameError = usernameErroro;
        this.isDataValid = false;
    }

    public SignInFormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.usernameError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
