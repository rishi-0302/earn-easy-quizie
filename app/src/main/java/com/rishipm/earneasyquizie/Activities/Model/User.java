package com.rishipm.earneasyquizie.Activities.Model;

public class User {

    private String name;
    private String email;
    private String password;


    private String profile;
    private String referCode;
    private long coins = 500;


    public User() {
    }

    public User(String name, String email, String password, String referCode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.referCode = referCode;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}

