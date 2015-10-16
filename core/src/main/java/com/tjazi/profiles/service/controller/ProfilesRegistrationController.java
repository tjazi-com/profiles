package com.tjazi.profiles.service.controller;

import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;
import com.tjazi.profiles.service.core.ProfilesRegistrationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

@RestController
@RequestMapping(value = "/profiles")
public class ProfilesRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(ProfilesRegistrationController.class);

    @Autowired
    private ProfilesRegistrationManager profilesRegistrationManager;

    @RequestMapping(value = "/newprofile", method = RequestMethod.POST)
    public RegisterNewProfileResponseMessage registerNewProfileRequestHandler(
            @RequestBody RegisterNewProfileRequestMessage requestMessage) {

        return profilesRegistrationManager.registerNewProfile(requestMessage);
    }
}
