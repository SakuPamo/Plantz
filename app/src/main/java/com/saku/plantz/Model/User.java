package com.saku.plantz.Model;

public class User {

    private String id;
    private String username;
    private String imageUrl;
    private String Fname;
    private String Lname;
    private String email;

    public User(String id, String username, String imageUrl, String fname, String lname, String email) {
        this.id = id;
        this.username = username;
        this.imageUrl = imageUrl;
        Fname = fname;
        Lname = lname;
        this.email = email;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFname() {
        return Fname;
    }

    public String getLname() {
        return Lname;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
