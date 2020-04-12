package com.example.beerpongclub.ui.login;

import androidx.annotation.Nullable;

public class LogInChecker {
    //username = email @this context
    private String username;
    private String password;

    private static final int PRE_AT = 0;
    private static final int POST_AT = 1;

    public LogInChecker() {
        this.username = null;
        this.password = null;
    }

    public LogInChecker(@Nullable String username, @Nullable String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean check_username() {

        boolean bool = false;
        int  length = this.username.length();
        int i = 0;
        int state = 0;
        while (i < length) {
            switch (state) {
                case(PRE_AT):
                    if(this.username.charAt(i) == '@') {
                        state ++;
                    }
                    break;
                case(POST_AT):
                    if(this.username.charAt(i) == '.') {
                        return true;
                    }
                    break;
                default:
                    //ignore
                    break;

            }
            i++;
        }
        return false;
    }
}
