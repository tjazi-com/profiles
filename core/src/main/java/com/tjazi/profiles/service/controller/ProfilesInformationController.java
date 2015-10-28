package com.tjazi.profiles.service.controller;

import com.tjazi.profiles.messages.GetProfileDetailsRequestMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;
import com.tjazi.profiles.service.core.ProfilesInformationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Krzysztof Wasiak on 28/10/2015.
 */

@RestController
@RequestMapping(value = "/profiles")
public class ProfilesInformationController {

    private static Logger log = LoggerFactory.getLogger(ProfilesInformationController.class);

    @Autowired
    private ProfilesInformationProvider profilesInformationProvider;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public GetProfileDetailsResponseMessage getProfileDetails(
            GetProfileDetailsRequestMessage getProfileDetailsRequestMessage) {

        return profilesInformationProvider.getProfileDetails(getProfileDetailsRequestMessage);
    }
}
