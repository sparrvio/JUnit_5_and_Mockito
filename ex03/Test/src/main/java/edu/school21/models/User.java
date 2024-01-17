package edu.school21.models;

public class User {
    private long id;
    private final String login;
    private final String password;
    private boolean status;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public boolean isStatus() {
        return status;
    }

    public User(String login, String password, boolean status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }
    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
}
