package com.tjazi.profiles.client;

import com.tjazi.lib.messaging.rest.RestClient;
import com.tjazi.profiles.messages.GetProfileDetailsRequestMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;
import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */
public class ProfilesClientImpl implements ProfilesClient {

    private RestClient restClient;

    private Logger log = LoggerFactory.getLogger(ProfilesClientImpl.class);

    public ProfilesClientImpl(RestClient restClient){

        if (restClient == null){
            String errorMessage = "restClient is null";

            log.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        this.restClient = restClient;
    }

    public RegisterNewProfileResponseMessage registerNewProfile(
            String userName, String email, String name, String surname) {

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

        RegisterNewProfileRequestMessage requestMessage = new RegisterNewProfileRequestMessage();

        requestMessage.setUserName(userName);
        requestMessage.setEmail(email);
        requestMessage.setName(name);
        requestMessage.setSurname(surname);

        return (RegisterNewProfileResponseMessage) restClient.sendRequestGetResponse(requestMessage, RegisterNewProfileResponseMessage.class);
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

        Object response = restClient.sendRequestGetResponse(requestMessage, GetProfileDetailsResponseMessage.class);

        return (GetProfileDetailsResponseMessage) response;
    }
}
