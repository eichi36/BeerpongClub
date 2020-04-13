package com.example.beerpongclub.Database;



public class User{
    //User class that will be pushed to the database;
    //It must include every Element of the
    private String Username;
    private String EMail;
    private String password;
    private String Uid;



    public User(String username, String EMail, String password, String Uid) {
        this.Username = username;
        this.EMail = EMail;
        this.password = password;
        this.Uid = Uid;
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

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }


}
