package com.tjazi.profiles.service.core;

import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */
public interface ProfilesRegistrationManager {

    RegisterNewProfileResponseMessage registerNewProfile(RegisterNewProfileRequestMessage requestMessage);

}
