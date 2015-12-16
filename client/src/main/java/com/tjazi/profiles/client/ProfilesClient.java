package com.tjazi.profiles.client;

import com.tjazi.profiles.messages.GetProfileDetailsByUserNameEmailResponseMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

public interface ProfilesClient {

    void registerNewProfile(UUID profileUuid, String userName, String email, String name, String surname);
    GetProfileDetailsResponseMessage getProfileDetails(UUID profileUuid);
    GetProfileDetailsByUserNameEmailResponseMessage getProfileDetailsByUserNameEmail(String userNameEmail);
}
