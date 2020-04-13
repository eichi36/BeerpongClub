package com.example.beerpongclub.Database;

public class User extends DatabaseElement{
    //User class that will be pushed to the database;
    //It must include every Element of the
    private String Username;
    private String EMail;
    private String password;

    public User(String username, String EMail, String password) {
        this.Username = username;
        this.EMail = EMail;
        this.password = password;
        this.PATH="User";
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
