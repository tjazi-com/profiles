package com.tjazi.profiles.messages;

/**
 * Created by Krzysztof Wasiak on 10/10/15.
 */
public class RegisterNewProfileResponseMessage {

    private RegisterNewProfileResponseStatus registerNewProfileResponseStatus;

    public RegisterNewProfileResponseStatus getRegisterNewProfileResponseStatus() {
        return registerNewProfileResponseStatus;
    }

    public void setRegisterNewProfileResponseStatus(RegisterNewProfileResponseStatus registerNewProfileResponseStatus) {
        this.registerNewProfileResponseStatus = registerNewProfileResponseStatus;
    }
}
