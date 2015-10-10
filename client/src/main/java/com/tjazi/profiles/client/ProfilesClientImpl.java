package com.tjazi.profiles.client;

import com.tjazi.lib.messaging.rest.RestClient;
import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */
public class ProfilesClientImpl implements ProfilesClient {

    private RestClient restClient;

    public ProfilesClientImpl(RestClient restClient){

        if (restClient == null){
            throw new IllegalArgumentException("'restClient' is null.");
        }

        this.restClient = restClient;
    }

    public RegisterNewProfileResponseMessage RegisterNewProfile(
            String userName, String email, String name, String surname) {

        RegisterNewProfileRequestMessage requestMessage = new RegisterNewProfileRequestMessage();

        requestMessage.setUserName(userName);
        requestMessage.setEmail(email);
        requestMessage.setName(name);
        requestMessage.setSurname(surname);

        return (RegisterNewProfileResponseMessage) restClient.sendRequestGetResponse(requestMessage, RegisterNewProfileResponseMessage.class);
    }
}
