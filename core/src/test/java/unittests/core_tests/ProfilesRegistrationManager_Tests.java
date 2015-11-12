package unittests.core_tests;

import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseStatus;
import com.tjazi.profiles.service.core.ProfilesRegistrationManagerImpl;
import com.tjazi.profiles.service.dao.ProfileDAO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Krzysztof Wasiak on 12/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfilesRegistrationManager_Tests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    public ProfilesRegistrationManagerImpl profilesRegistrationManager;

    @Mock
    public ProfileDAO profileDAO;

    @Test
    public void registerNewProfile_ExceptionOnNullInput() {
        thrown.expect(IllegalArgumentException.class);

        profilesRegistrationManager.registerNewProfile(null);
    }

    @Test
    public void registerNewProfile_GeneralErrorOnDatabaseException() {

        final String userName = "sample user name";
        final String userEmail = "sample user email";

        when(profileDAO.findByUserNameOrUserEmail(userName, userEmail))
                .thenThrow(Exception.class);

        RegisterNewProfileRequestMessage requestMessage = new RegisterNewProfileRequestMessage();
        requestMessage.setUserName(userName);
        requestMessage.setEmail(userEmail);

        // main function call
        RegisterNewProfileResponseMessage responseMessage = profilesRegistrationManager.registerNewProfile(requestMessage);

        // assertion and verification
        verify(profileDAO, times(1)).findByUserNameOrUserEmail(userName, userEmail);

        assertEquals(RegisterNewProfileResponseStatus.GENERAL_REGISTRATION_ERROR, responseMessage.getRegisterNewProfileResponseStatus());
        assertNull(responseMessage.getNewProfileUuid());
    }


}
