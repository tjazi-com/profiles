package com.tjazi.profiles.service.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tjazi.profiles.messages.RegisterNewProfileRequestCommand;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "registerNewProfileRequestHandlerFallback")
    public boolean registerNewProfileRequestHandler(
            @RequestBody RegisterNewProfileRequestCommand requestMessage) throws Exception {

        try
        {
            // any exception here will be fallen-back to the fallback method
            return profilesRegistrationManager.registerNewProfile(requestMessage);
        }
        catch (Exception ex) {
            log.error("Got unhandled exception: " + ex.toString());
            throw ex;
        }
    }

    /**
     * Fallback method when profile registration fails.
     * @param requestMessage Profile registration request message
     * @return False - always.
     */
    public boolean registerNewProfileRequestHandlerFallback(RegisterNewProfileRequestCommand requestMessage) {
        log.error("registerNewProfileRequestHandlerFallback called, something went wrong with profile registration");

        return false;
    }
}
