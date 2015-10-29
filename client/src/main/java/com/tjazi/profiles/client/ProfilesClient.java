package com.tjazi.profiles.client;

import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

public interface ProfilesClient {

    RegisterNewProfileResponseMessage RegisterNewProfile(String userName, String email, String name, String surname);
    GetProfileDetailsResponseMessage GetProfileDetails(UUID profileUuid);
}
