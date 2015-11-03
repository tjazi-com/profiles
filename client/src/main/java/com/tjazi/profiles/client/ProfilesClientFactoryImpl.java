package com.tjazi.profiles.client;

import com.tjazi.lib.messaging.rest.RestClientImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */

@Component
public class ProfilesClientFactoryImpl implements ProfilesClientFactory {

    public ProfilesClient createProfilesClient(URI targetUri) {

        if (targetUri == null) {
            throw new IllegalArgumentException("'targetUri' is null.");
        }

        return new ProfilesClientImpl(new RestClientImpl(targetUri));
    }
}
