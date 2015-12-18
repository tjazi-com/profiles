package com.tjazi.profiles.service.core;

import com.tjazi.profiles.messages.RegisterNewProfileRequestCommand;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */
public interface ProfilesRegistrationManager {

    boolean registerNewProfile(RegisterNewProfileRequestCommand requestMessage);

}
