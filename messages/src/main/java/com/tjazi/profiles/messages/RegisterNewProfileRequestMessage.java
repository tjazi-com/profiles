package com.tjazi.profiles.messages;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */
public class RegisterNewProfileRequestMessage {

    private String userName;
    private String email;
    private String name;
    private String surname;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
