package com.example.mawuli.chasers.util;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class User extends RealmObject {

    public User() {

    }

    @PrimaryKey
    private String id;
    private String email;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;

    }

}