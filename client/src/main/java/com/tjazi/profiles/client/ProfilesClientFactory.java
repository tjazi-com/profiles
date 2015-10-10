package com.tjazi.profiles.client;

import java.net.URI;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */
public interface ProfilesClientFactory {

    ProfilesClient createProfilesClient(URI targetUri);
}
