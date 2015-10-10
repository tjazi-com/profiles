package com.tjazi.profiles.messages;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */
public enum RegisterNewProfileResponseStatus {

    OK,
    USER_NAME_ALREADY_REGISTERED,
    USER_EMAIL_ALREADY_REGISTERED_WITH_DIFFERENT_USER,
    GENERAL_REGISTRATION_ERROR,

    UNKNOWN
}
