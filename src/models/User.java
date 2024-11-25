package models;

import utils.Session;

import java.util.List;
import java.util.ArrayList;

public class User {
    private String userId;
    private String password;
    private String name;
    private static User instance;
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    // Constructor

    //getter id
    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}






    @Override
    public String toString() {
        return "User ID: " + userId + "\nName: " + name;
    }
}
