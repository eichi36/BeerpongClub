package com.example.beerpongclub.SignIn;

import com.example.beerpongclub.R;

public class SignInChecker {
    private String Email;
    private String password;
    private String password_repeat;
    private String username;

    //Konstanten
    final static int PRE_AT = 0;
    final static int POST_AT = 1;

    public SignInChecker() {
    }

    public void setAtt(String Email, String password, String password_repeat, String username) {
        this.Email = Email;
        this.password = password;
        this.password_repeat = password_repeat;
        this.username = username;
    }

    private Integer isEmailValid() {
        boolean bool = false;
        int  length = this.Email.length();
        int i = 0;
        int state = 0;
        while (i < length) {
            switch (state) {
                case(PRE_AT):
                    if(this.Email.charAt(i) == '@') {
                        state ++;
                    }
                    break;
                case(POST_AT):
                    if(this.Email.charAt(i) == '.') {
                        return null;
                    }
                    break;
                default:
                    //ignore
                    break;

            }
            i++;
        }
        return R.string.invalid_email_signIn;

    }

    private Integer isPasswordValid() {

        if(password.length() < 6) {
            return R.string.password_too_short_signIn;
        }
        return null;
    }

    private Integer isUsernameValid() {
        if(username.length() < 4) {
            return R.string.username_too_short_signIn;
        }
        return null;

    }

    public SignInFormState check_input() {
        Integer Email_valid = isEmailValid();
        Integer Password_valid = isPasswordValid();
        Integer Username_valid = isUsernameValid();
        if(Email_valid == null && Password_valid == null && Username_valid == null) {
            return new SignInFormState(true);
        } else {
            return new SignInFormState(Email_valid, Password_valid, Username_valid);
        }
    }
}
