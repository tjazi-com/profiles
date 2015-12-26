package com.tjazi.profiles.client;

import com.tjazi.profiles.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

@Service
public class ProfilesClientImpl implements ProfilesClient {

    @Autowired
    private RestTemplate restTemplate;

    private Logger log = LoggerFactory.getLogger(ProfilesClientImpl.class);

    // this name supposed to be decoded by using eureka service
    private final static String PROFILES_SERVICE_NAME = "profiles-service-core";

    private final static String PROFILES_REGISTRATION_URL = "http://" + PROFILES_SERVICE_NAME + "/profiles/register";
    private final static String PROFILES_DETAILS_URL = "http://" + PROFILES_SERVICE_NAME + "/profiles/profiledetails";
    private final static String PROFILES_DETAILS_USERNAME_EMAIL_URL = "http://" + PROFILES_SERVICE_NAME + "/profiles/profiledetails2";

    public boolean registerNewProfile(
            UUID profileUuid,
            String userName, String email, String name, String surname) {

        if (profileUuid == null) {
            String errorMessage = "profileUuid passed is null";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (userName == null || userName.isEmpty()) {
            String errorMessage = "userName passed is either null or empty";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (email == null || email.isEmpty()) {
            String errorMessage = "email passed is either null or empty";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        RegisterNewProfileRequestCommand requestMessage = new RegisterNewProfileRequestCommand();

        requestMessage.setProfileUuid(profileUuid);
        requestMessage.setUserName(userName);
        requestMessage.setEmail(email);
        requestMessage.setName(name);
        requestMessage.setSurname(surname);

        return restTemplate.postForObject(PROFILES_REGISTRATION_URL, requestMessage, boolean.class, (Object) null);
    }

    public GetProfileDetailsResponseMessage getProfileDetails(UUID profileUuid)
    {
        if (profileUuid == null) {
            String errorMessage = "profileUuid is null";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        GetProfileDetailsRequestMessage requestMessage = new GetProfileDetailsRequestMessage();
        requestMessage.setProfileUuid(profileUuid);

        return restTemplate.postForObject(PROFILES_DETAILS_URL, requestMessage,
                GetProfileDetailsResponseMessage.class, (Object) null);
    }

    @Override
    public GetProfileDetailsByUserNameEmailResponseMessage getProfileDetailsByUserNameEmail(String userNameEmail) {

        if (userNameEmail == null || userNameEmail.isEmpty()) {
            String errorMessage = "userNameEmail is null or empty";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        GetProfileDetailsByUserNameEmailRequestMessage requestMessage = new GetProfileDetailsByUserNameEmailRequestMessage();
        requestMessage.setUserNameEmail(userNameEmail);

        return restTemplate.postForObject(PROFILES_DETAILS_USERNAME_EMAIL_URL, requestMessage,
                GetProfileDetailsByUserNameEmailResponseMessage.class, (Object) null);
    }
}
