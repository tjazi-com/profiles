package unittests.controller_tests;

import com.tjazi.profiles.messages.*;
import com.tjazi.profiles.service.controller.ProfilesRegistrationController;
import com.tjazi.profiles.service.core.ProfilesRegistrationManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfilesRegistrationController_Tests {

    @InjectMocks
    private ProfilesRegistrationController profilesRegistrationController;

    @Mock
    private ProfilesRegistrationManager profilesRegistrationManagerMock;

    @Test
    public void registerNewProfileRequestHandler_Test() {

        RegisterNewProfileResponseStatus expectedStatus =
                RegisterNewProfileResponseStatus.USER_EMAIL_ALREADY_REGISTERED_WITH_DIFFERENT_USER;

        RegisterNewProfileResponseMessage sampleResponseMessage = new RegisterNewProfileResponseMessage();
        sampleResponseMessage.setRegisterNewProfileResponseStatus(expectedStatus);

        when(profilesRegistrationManagerMock.registerNewProfile(any(RegisterNewProfileRequestMessage.class)))
                .thenReturn(sampleResponseMessage);

        RegisterNewProfileResponseMessage responseMessage =
                profilesRegistrationController.registerNewProfileRequestHandler(new RegisterNewProfileRequestMessage());

        assertEquals(expectedStatus, responseMessage.getRegisterNewProfileResponseStatus());
    }
}
