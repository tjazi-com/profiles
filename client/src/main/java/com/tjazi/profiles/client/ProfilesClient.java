package com.tjazi.profiles.client;

import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;
import org.springframework.stereotype.Service;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

@Service
public interface ProfilesClient {

    RegisterNewProfileResponseMessage RegisterNewProfile(String userName, String email, String name, String surname);
}
