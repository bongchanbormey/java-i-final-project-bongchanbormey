package Model;

import java.io.Serializable;

public class User implements Serializable {
    public String getFullname() {
        return fullname;
    }

    public User(String fullname,  String username, String email, String password, int ordercount) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.ordercount = ordercount;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    private String fullname;
    private int ordercount;
    private String username;
    private String email;
    private String password;
    private boolean isActive;

    public int getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(int ordercount) {
        this.ordercount = ordercount;
    }

    public User() {
        isActive = true; // Set the initial account status to active

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}