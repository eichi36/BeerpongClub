package com.example.beerpongclub.Database;



public class User{
    //User class that will be pushed to the database;
    //It must include every Element of the
    private String Username;
    private String EMail;
    private String password;
    private String Uid;
    private String profile_pic_uri;



    public User () {}
    public User(String username, String EMail, String password, String Uid, String profilePicUri) {
        this.Username = username;
        this.EMail = EMail;
        this.password = password;
        this.Uid = Uid;
        this.profile_pic_uri = profilePicUri;
    }

    public String getUsername() {
        return Username;
    }

    public void setusername(String username) {
        Username = username;
    }

    public String getEMail() {
        return EMail;
    }

    public void setemail(String EMail) {
        this.EMail = EMail;
    }

    public String getPassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return Uid;
    }

    public void setuid(String uid) {
        Uid = uid;
    }

    public String getprofile_pic_uri() {
        return profile_pic_uri;
    }

    public void setprofile_pic_urii(String profilePicUri) {
        this.profile_pic_uri = profilePicUri;
    }
}
