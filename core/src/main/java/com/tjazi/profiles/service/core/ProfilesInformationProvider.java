package com.tjazi.profiles.service.core;

import com.tjazi.profiles.messages.GetProfileDetailsRequestMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;

/**
 * Created by Krzysztof Wasiak on 28/10/2015.
 */
public interface ProfilesInformationProvider {

    GetProfileDetailsResponseMessage getProfileDetails(GetProfileDetailsRequestMessage requestMessage);
}
