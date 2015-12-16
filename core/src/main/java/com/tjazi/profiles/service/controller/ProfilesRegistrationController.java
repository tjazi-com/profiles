package com.tjazi.profiles.service.controller;

import com.tjazi.profiles.messages.RegisterNewProfileRequestCommand;
import com.tjazi.profiles.service.core.ProfilesRegistrationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

@EnableBinding(Sink.class)
@MessageEndpoint
public class ProfilesRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(ProfilesRegistrationController.class);

    @Autowired
    private ProfilesRegistrationManager profilesRegistrationManager;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void registerNewProfileRequestHandler(RegisterNewProfileRequestCommand requestMessage) {

        profilesRegistrationManager.registerNewProfile(requestMessage);
    }
}
