/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Dan
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private String userFullName;
    private String email;

    public User(int userId) {
        this.userId = userId;
    }

    public User(String username, String password, String userFullName, String email) {
        this.username = username;
        this.password = password;
        this.userFullName = userFullName;
        this.email = email;
    }

    public User(int userId, String username, String password, String userFullName, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userFullName = userFullName;
        this.email = email;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", userFullName=" + userFullName + ", email=" + email + '}';
    }
    
    

}