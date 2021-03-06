package com.tjazi.profiles.messages;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 08/11/2015.
 */
public class GetProfileDetailsByUserNameEmailResponseMessage {

    private GetProfileDetailsByUserNameEmailResponseStatus responseStatus;
    private UUID profileUuid;
    private String userName;
    private String userEmail;
    private String name;
    private String surname;

    public GetProfileDetailsByUserNameEmailResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(GetProfileDetailsByUserNameEmailResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
