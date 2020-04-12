package com.example.beerpongclub.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isUserNameValid;
    private boolean isPasswordValid;
    private boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
        this.isUserNameValid = false;
        this.isPasswordValid = false;

    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;

    }

    /*LoginFormState(@Nullable  boolean isUserNameValid, @Nullable boolean isPasswordValid)  {
        //constructor which takes bool UserNameValid and bool PasswordValid as arguments
        //this constructor is necessary for typing the Username and the Password in 2 different steps of a process
        // example: @LoginActivity: PasswordEditText is NOT visible, only UsernameEditText -> PasswordEditText will be visible if the User has entered a correct E-Mail

        //with this constructor you can handle the Password and the Username separately
        this.isUserNameValid = isUserNameValid;
        this.isPasswordValid = isPasswordValid;
        if(isUserNameValid && isPasswordValid) {
            this.isDataValid = true;
        } else {
            this.isDataValid = false;
        }
    }*/

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }

    boolean isUserNameValid(){ return this.isUserNameValid;}

    boolean isPasswordValid() { return this.isPasswordValid;}
}
