package com.tjazi.profiles.messages;

/**
 * Created by Krzysztof Wasiak on 08/11/2015.
 */
public enum  GetProfileDetailsByUserNameEmailResponseStatus {

    /**
     * Unknown status / not set. Should never happen.
     */
    UNKNOWN,

    /**
     * All went fine. GetProfileDetailsResponseMessage contains valid profile data.
     */
    OK,

    /**
     * Profile with the given user name / email has not been found
     */
    PROFILE_NOT_FOUND,

    /**
     * General error with extracting profile details
     */
    GENERAL_ERROR
}
