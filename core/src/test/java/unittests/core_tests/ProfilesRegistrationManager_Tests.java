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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Krzysztof Wasiak on 17/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilesRegistrationManager_Tests {

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
        storedModel.setEmail("sample1@email_not_duplicated.com");
        storedModel.setUserName(userName);

        when(profileDAO.findByUserNameOrEmail(userName, userEmail))
                .thenReturn(Collections.singletonList(storedModel));

        RegisterNewProfileResponseMessage responseMessage = profilesRegistrationManager.registerNewProfile(requestMessage);

        // so we should hit USER_NAME_ALREADY_REGISTERED error
        assertEquals(RegisterNewProfileResponseStatus.USER_NAME_ALREADY_REGISTERED,
                responseMessage.getRegisterNewProfileResponseStatus());
    }

}
