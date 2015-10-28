package com.tjazi.profiles.messages;

/**
 * Created by Krzysztof Wasiak on 28/10/2015.
 *
 * Status of the profile extraction via GetProfileDetailsRequestMessage
 */
public enum GetProfileDetailsResponseStatus {

    /**
     * All went fine. GetProfileDetailsResponseMessage contains valid profile data.
     */
    OK,

    /**
     * Profile with the given UUID doesn't exist
     */
    PROFILE_UUID_NOT_REGISTERED,

    /**
     * General error with extracting profile details
     */
    GENERAL_ERROR,

    /**
     * Unknown status. Should never happen.
     */
    UKNOWN
}
