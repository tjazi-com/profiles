package unittests.core_tests;

import com.tjazi.profiles.messages.RegisterNewProfileRequestMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseMessage;
import com.tjazi.profiles.messages.RegisterNewProfileResponseStatus;
import com.tjazi.profiles.service.core.ProfilesRegistrationManagerImpl;
import com.tjazi.profiles.service.dao.ProfileDAO;
import com.tjazi.profiles.service.dao.model.ProfileDataDAOModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Krzysztof Wasiak on 17/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilesInformationProvider_ByProfileUuid_Tests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ProfileDAO profileDAO;

    @InjectMocks
    private ProfilesRegistrationManagerImpl profilesRegistrationManager;

    @Test
    public void ExceptionOnNullInput_Test() {

        thrown.expect(IllegalArgumentException.class);

        profilesRegistrationManager.registerNewProfile(null);
    }

    @Test
    public void DuplicateUserName_Test() {

        RegisterNewProfileRequestMessage requestMessage = new RegisterNewProfileRequestMessage();

        final String userName = "Sample user name";
        final String userEmail = "sample@email.com";

        requestMessage.setEmail(userEmail);
        requestMessage.setUserName(userName);

        ProfileDataDAOModel storedModel = new ProfileDataDAOModel();
        storedModel.setUserEmail("sample1@email_not_duplicated.com");
        storedModel.setUserName(userName);

        when(profileDAO.findByUserNameOrEmail(userName, userEmail))
                .thenReturn(Collections.singletonList(storedModel));

        RegisterNewProfileResponseMessage responseMessage = profilesRegistrationManager.registerNewProfile(requestMessage);

        // so we should hit USER_NAME_ALREADY_REGISTERED error
        assertEquals(RegisterNewProfileResponseStatus.USER_NAME_ALREADY_REGISTERED,
                responseMessage.getRegisterNewProfileResponseStatus());
    }

    @Test
    public void DuplicateUserEmail_Test() {

        RegisterNewProfileRequestMessage requestMessage = new RegisterNewProfileRequestMessage();

        final String userName = "Sample user name";
        final String userEmail = "sample@email.com";

        requestMessage.setEmail(userEmail);
        requestMessage.setUserName(userName);

        ProfileDataDAOModel storedModel = new ProfileDataDAOModel();
        storedModel.setUserEmail("sample@email.com");
        storedModel.setUserName("Sample user name not duplicated");

        when(profileDAO.findByUserNameOrEmail(userName, userEmail))
                .thenReturn(Collections.singletonList(storedModel));

        RegisterNewProfileResponseMessage responseMessage = profilesRegistrationManager.registerNewProfile(requestMessage);

        // so we should hit USER_NAME_ALREADY_REGISTERED error
        assertEquals(RegisterNewProfileResponseStatus.USER_EMAIL_ALREADY_REGISTERED_WITH_DIFFERENT_USER,
                responseMessage.getRegisterNewProfileResponseStatus());

        assertNull(responseMessage.getNewProfileUuid());
    }

    @Test
    public void ExceptionWhenSavingData_Test() {
        RegisterNewProfileRequestMessage requestMessage = new RegisterNewProfileRequestMessage();

        final String userName = "Sample user name";
        final String userEmail = "sample@email.com";

        requestMessage.setEmail(userEmail);
        requestMessage.setUserName(userName);

        when(profileDAO.findByUserNameOrEmail(userName, userEmail))
                .thenReturn(null);

        when(profileDAO.save((ProfileDataDAOModel) any(ProfileDataDAOModel.class)))
                .thenThrow(Exception.class);

        RegisterNewProfileResponseMessage responseMessage = profilesRegistrationManager.registerNewProfile(requestMessage);

        assertEquals(RegisterNewProfileResponseStatus.GENERAL_REGISTRATION_ERROR,
                responseMessage.getRegisterNewProfileResponseStatus());

        assertNull(responseMessage.getNewProfileUuid());
    }

    @Test
    public void SuccessStatusWhenDataSavedNoExceptions_Test() {

        RegisterNewProfileRequestMessage requestMessage = new RegisterNewProfileRequestMessage();

        final String userName = "Sample user name";
        final String userEmail = "sample@email.com";

        requestMessage.setEmail(userEmail);
        requestMessage.setUserName(userName);

        when(profileDAO.findByUserNameOrEmail(userName, userEmail))
                .thenReturn(new ArrayList<ProfileDataDAOModel>());

        RegisterNewProfileResponseMessage responseMessage = profilesRegistrationManager.registerNewProfile(requestMessage);

        assertEquals(RegisterNewProfileResponseStatus.OK,
                responseMessage.getRegisterNewProfileResponseStatus());

        assertNotNull(responseMessage.getNewProfileUuid());
    }
}
