package com.tjazi.profiles.messages;

/**
 * Created by Krzysztof Wasiak on 08/11/2015.
 *
 * Message: get profile details based on the input user name / email string.
 * It's up to core profiles functionality to decide whenever passed string is user name or email.
 *
 * Open Issue / TODO: how to make sure that query by user name / email doesn't generate more than 1 answer
 */
public class GetProfileDetailsByUserNameEmailRequestMessage {

    /**
     * User name / email
     */
    private String userNameEmail;

    public String getUserNameEmail() {
        return userNameEmail;
    }

    public void setUserNameEmail(String userNameEmail) {
        this.userNameEmail = userNameEmail;
    }
}
