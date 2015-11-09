package unittests.core_tests;

import com.tjazi.profiles.messages.GetProfileDetailsByUserNameEmailRequestMessage;
import com.tjazi.profiles.messages.GetProfileDetailsByUserNameEmailResponseMessage;
import com.tjazi.profiles.messages.GetProfileDetailsByUserNameEmailResponseStatus;
import com.tjazi.profiles.service.core.ProfilesInformationProvider;
import com.tjazi.profiles.service.core.ProfilesInformationProviderImpl;
import com.tjazi.profiles.service.dao.ProfileDAO;
import com.tjazi.profiles.service.dao.model.ProfileDataDAOModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.print.DocFlavor;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Krzysztof Wasiak on 08/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfilesInformationProvider_ByUserNameEmail_Tests {

    @InjectMocks
    public ProfilesInformationProviderImpl profilesInformationProvider;

    @Mock
    public ProfileDAO profileDAO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getProfileDetailsByUserNameEmail_ExceptionOnNullRequest_Test(){
        thrown.expect(IllegalArgumentException.class);

        profilesInformationProvider.getProfileDetailsByUserNameEmail(null);
    }

    @Test
    public void getProfileDetailsByUserNameEmail_ExceptionOnNullUserNameEmail_Test(){
        thrown.expect(IllegalArgumentException.class);

        profilesInformationProvider.getProfileDetailsByUserNameEmail(
                new GetProfileDetailsByUserNameEmailRequestMessage());
    }

    @Test
    public void getProfileDetailsByUserNameEmail_NoProfileData_Test() {

        final String userNameEmail = "sample@user.name";
        final GetProfileDetailsByUserNameEmailResponseStatus expectedStatus =
                GetProfileDetailsByUserNameEmailResponseStatus.PROFILE_NOT_FOUND;

        GetProfileDetailsByUserNameEmailRequestMessage requestMessage = new GetProfileDetailsByUserNameEmailRequestMessage();
        requestMessage.setUserNameEmail(userNameEmail);

        when(profileDAO.findByUserNameOrEmail(userNameEmail, userNameEmail))
                .thenReturn(Collections.<ProfileDataDAOModel>emptyList());

        // main call
        GetProfileDetailsByUserNameEmailResponseMessage responseMessage =
                profilesInformationProvider.getProfileDetailsByUserNameEmail(requestMessage);

        // verification and assertion
        verify(profileDAO, times(1)).findByUserNameOrEmail(userNameEmail,userNameEmail);

        assertEquals(expectedStatus, responseMessage.getResponseStatus());
        assertEquals(null, responseMessage.getUserName());
        assertEquals(null, responseMessage.getUserEmail());
        assertEquals(null, responseMessage.getName());
        assertEquals(null, responseMessage.getProfileUuid());
        assertEquals(null, responseMessage.getSurname());
    }

    @Test
    public void getProfileDetailsByUserNameEmail_MoreThan1RecordFound_Test() {

        final String userNameEmail = "sample@user.name";
        final GetProfileDetailsByUserNameEmailResponseStatus expectedStatus =
                GetProfileDetailsByUserNameEmailResponseStatus.GENERAL_ERROR;

        GetProfileDetailsByUserNameEmailRequestMessage requestMessage = new GetProfileDetailsByUserNameEmailRequestMessage();
        requestMessage.setUserNameEmail(userNameEmail);

        when(profileDAO.findByUserNameOrEmail(userNameEmail, userNameEmail))
                .thenReturn(Collections.nCopies(2, new ProfileDataDAOModel()));

        // main call
        GetProfileDetailsByUserNameEmailResponseMessage responseMessage =
                profilesInformationProvider.getProfileDetailsByUserNameEmail(requestMessage);

        // verification and assertion
        verify(profileDAO, times(1)).findByUserNameOrEmail(userNameEmail,userNameEmail);

        assertEquals(expectedStatus, responseMessage.getResponseStatus());
        assertEquals(null, responseMessage.getUserName());
        assertEquals(null, responseMessage.getUserEmail());
        assertEquals(null, responseMessage.getName());
        assertEquals(null, responseMessage.getProfileUuid());
        assertEquals(null, responseMessage.getSurname());
    }

    @Test
    public void getProfileDetailsByUserNameEmail_ExceptionOnDaoLayer_Test() {

        final String userNameEmail = "sample@user.name";
        final GetProfileDetailsByUserNameEmailResponseStatus expectedStatus =
                GetProfileDetailsByUserNameEmailResponseStatus.GENERAL_ERROR;

        GetProfileDetailsByUserNameEmailRequestMessage requestMessage = new GetProfileDetailsByUserNameEmailRequestMessage();
        requestMessage.setUserNameEmail(userNameEmail);

        when(profileDAO.findByUserNameOrEmail(userNameEmail, userNameEmail))
                .thenThrow(Exception.class);

        // main call
        GetProfileDetailsByUserNameEmailResponseMessage responseMessage =
                profilesInformationProvider.getProfileDetailsByUserNameEmail(requestMessage);

        // verification and assertion
        verify(profileDAO, times(1)).findByUserNameOrEmail(userNameEmail,userNameEmail);

        assertEquals(expectedStatus, responseMessage.getResponseStatus());
        assertEquals(null, responseMessage.getUserName());
        assertEquals(null, responseMessage.getUserEmail());
        assertEquals(null, responseMessage.getName());
        assertEquals(null, responseMessage.getProfileUuid());
        assertEquals(null, responseMessage.getSurname());
    }

}
