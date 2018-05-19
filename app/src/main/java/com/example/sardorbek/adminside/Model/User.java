package com.example.sardorbek.adminside.Model;

/**
 * Created by sardorbek on 4/18/18.
 */

public class User {
    private String Name,Password,Id,IsStaff;

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public User() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }
}
