package com.tjazi.profiles.messages;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 * This is command, which is send via RabbitMQ to register
 */
public class RegisterNewProfileRequestCommand {

    private UUID profileUuid;
    private String userName;
    private String email;
    private String name;
    private String surname;

    public UUID getProfileUuid() {
        return profileUuid;
    }

    public void setProfileUuid(UUID profileUuid) {
        this.profileUuid = profileUuid;
    }

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
