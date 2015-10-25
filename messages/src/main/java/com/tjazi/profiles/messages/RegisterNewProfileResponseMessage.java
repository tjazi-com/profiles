package com.tjazi.profiles.messages;

import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */
public class RegisterNewProfileResponseMessage {

    private RegisterNewProfileResponseStatus registerNewProfileResponseStatus;
    private UUID newProfileUuid;

    public RegisterNewProfileResponseStatus getRegisterNewProfileResponseStatus() {
        return registerNewProfileResponseStatus;
    }

    public void setRegisterNewProfileResponseStatus(RegisterNewProfileResponseStatus registerNewProfileResponseStatus) {
        this.registerNewProfileResponseStatus = registerNewProfileResponseStatus;
    }

    public UUID getNewProfileUuid() {
        return newProfileUuid;
    }

    public void setNewProfileUuid(UUID newProfileUuid) {
        this.newProfileUuid = newProfileUuid;
    }
}
