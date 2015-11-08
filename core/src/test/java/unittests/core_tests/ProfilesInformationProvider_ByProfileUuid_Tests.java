package unittests.core_tests;

import com.tjazi.profiles.messages.GetProfileDetailsRequestMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseStatus;
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

import java.util.Collections;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Krzysztof Wasiak on 28/10/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilesInformationProvider_ByProfileUuid_Tests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    public ProfilesInformationProviderImpl profilesInformationProvider;

    @Mock
    public ProfileDAO profileDAO;

    @Test
    public void getProfileDetails_NullInput_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesInformationProvider.getProfileDetails(null);
    }

    @Test
         public void getProfileDetails_ExtractDataFromDaoSuccess_Test() {

        final UUID profileUuid = UUID.randomUUID();
        final String userName = "sample user name 1";
        final String userEmail = "sample@email.com";
        final String name = "sample name";
        final String surname = "sample surname";

        GetProfileDetailsRequestMessage requestMessage = new GetProfileDetailsRequestMessage();
        requestMessage.setProfileUuid(profileUuid);

        ProfileDataDAOModel resultDaoModel = new ProfileDataDAOModel();
        resultDaoModel.setProfileUuid(profileUuid);
        resultDaoModel.setUserName(userName);
        resultDaoModel.setUserEmail(userEmail);
        resultDaoModel.setName(name);
        resultDaoModel.setSurname(surname);
        resultDaoModel.setId(2427491);

        when(profileDAO.findByProfileUuid(profileUuid))
                .thenReturn(Collections.singletonList(resultDaoModel));

        GetProfileDetailsResponseMessage profileDetails = profilesInformationProvider.getProfileDetails(requestMessage);

        // validate calls and returned values

        assertEquals(GetProfileDetailsResponseStatus.OK, profileDetails.getResponseStatus());
        assertEquals(userName, profileDetails.getUserName());
        assertEquals(userEmail, profileDetails.getUserEmail());
        assertEquals(name, profileDetails.getName());
        assertEquals(surname, profileDetails.getSurname());
        assertEquals(profileUuid, profileDetails.getProfileUuid());

        verify(profileDAO).findByProfileUuid(profileUuid);
    }

    @Test
    public void getProfileDetails_ExtractDataFromDao_DaoException_Test() {

        final UUID profileUuid = UUID.randomUUID();

        GetProfileDetailsRequestMessage requestMessage = new GetProfileDetailsRequestMessage();
        requestMessage.setProfileUuid(profileUuid);

        when(profileDAO.findByProfileUuid(profileUuid))
                .thenThrow(Exception.class);

        GetProfileDetailsResponseMessage profileDetails = profilesInformationProvider.getProfileDetails(requestMessage);

        // validate calls and returned values
        assertEquals(GetProfileDetailsResponseStatus.GENERAL_ERROR, profileDetails.getResponseStatus());

        verify(profileDAO).findByProfileUuid(profileUuid);
    }

    @Test
    public void getProfileDetails_ExtractDataFromDao_NoDataOrNullResponse_Test() {

        final UUID profileUuid = UUID.randomUUID();

        GetProfileDetailsRequestMessage requestMessage = new GetProfileDetailsRequestMessage();
        requestMessage.setProfileUuid(profileUuid);

        when(profileDAO.findByProfileUuid(profileUuid))
                .thenReturn(Collections.<ProfileDataDAOModel>emptyList());

        GetProfileDetailsResponseMessage profileDetails = profilesInformationProvider.getProfileDetails(requestMessage);

        // validate calls and returned values
        assertEquals(GetProfileDetailsResponseStatus.PROFILE_UUID_NOT_REGISTERED, profileDetails.getResponseStatus());

        verify(profileDAO).findByProfileUuid(profileUuid);
    }

    @Test
    public void getProfileDetails_ExtractDataFromDao_MoreThan1ResultRecord_Test() {

        final UUID profileUuid = UUID.randomUUID();

        GetProfileDetailsRequestMessage requestMessage = new GetProfileDetailsRequestMessage();
        requestMessage.setProfileUuid(profileUuid);

        when(profileDAO.findByProfileUuid(profileUuid))
                .thenReturn(Collections.nCopies(2, new ProfileDataDAOModel()));

        GetProfileDetailsResponseMessage profileDetails = profilesInformationProvider.getProfileDetails(requestMessage);

        // validate calls and returned values
        assertEquals(GetProfileDetailsResponseStatus.GENERAL_ERROR, profileDetails.getResponseStatus());

        verify(profileDAO).findByProfileUuid(profileUuid);
    }

}
