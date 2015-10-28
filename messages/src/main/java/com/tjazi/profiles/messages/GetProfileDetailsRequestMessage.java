package com.tjazi.profiles.messages;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 28/10/2015.
 *
 * Get detailed information about single profile by its UUID (GUID)
 */
public class GetProfileDetailsRequestMessage {

    private UUID profileUuid;

    public UUID getProfileUuid() {
        return profileUuid;
    }

    public void setProfileUuid(UUID profileUuid) {
        this.profileUuid = profileUuid;
    }
}
