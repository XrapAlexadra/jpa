package com.github.xrapalexandra.kr.model;


public class User {

    private Integer Id;
    private String login;
    private String pass;
    private Role role;
    private UserAddress address;

    public User() {
    }

    public User(String login, Role role, String pass) {
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + Id +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", role=" + role +
                '}';
    }
}
