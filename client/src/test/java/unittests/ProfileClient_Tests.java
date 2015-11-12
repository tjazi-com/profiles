package unittests;


import com.tjazi.lib.messaging.rest.RestClient;
import com.tjazi.profiles.client.ProfilesClientImpl;
import com.tjazi.profiles.messages.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by Krzysztof Wasiak on 29/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfileClient_Tests {

    private final static String urlProfilesRegisterProfile = "/profiles/registerprofile";
    private final static String urlProfilesProfileDetails = "/profiles/profiledetails";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    public RestClient restClient;

    @Test
    public void constructor_NullRestClient_Test() {

        thrown.expect(IllegalArgumentException.class);

        ProfilesClientImpl profilesClient = new ProfilesClientImpl(null);
    }

    @Test
    public void getProfileDetails_nullProfileUuid_Exception_Test() {

        thrown.expect(IllegalArgumentException.class);

        createProfilesClient().getProfileDetails(null);
    }

    @Test
    public void getProfileDetails_validUuid_Success_Test() {

        final UUID targetProfileUuid = UUID.randomUUID();
        final GetProfileDetailsResponseMessage responseMessage = new GetProfileDetailsResponseMessage();

        ArgumentCaptor<GetProfileDetailsRequestMessage> requestMessageArgumentCaptor =
                ArgumentCaptor.forClass(GetProfileDetailsRequestMessage.class);

        ProfilesClientImpl profilesClient = createProfilesClient();

        when(restClient.sendRequestGetResponse(
                anyString(), anyObject(), eq(GetProfileDetailsResponseMessage.class)))
                .thenReturn(responseMessage);

        // main call
        GetProfileDetailsResponseMessage profileDetailsActual = profilesClient.getProfileDetails(targetProfileUuid);

        // verify calls
        verify(restClient, times(1)).sendRequestGetResponse(
                eq(urlProfilesProfileDetails),
                requestMessageArgumentCaptor.capture(),
                eq(GetProfileDetailsResponseMessage.class));

        assertEquals(targetProfileUuid, requestMessageArgumentCaptor.getValue().getProfileUuid());
        assertEquals(responseMessage, profileDetailsActual);
    }

    @Test
    public void registerNewProfile_NullUserName_Test() {
        thrown.expect(IllegalArgumentException.class);

        createProfilesClient().registerNewProfile(null, "Sample email", null, null);
    }

    @Test
    public void registerNewProfile_emptyUserName_Test() {
        thrown.expect(IllegalArgumentException.class);

        createProfilesClient().registerNewProfile("", "Sample email", null, null);
    }

    @Test
    public void registerNewProfile_NullUserEmail_Test() {
        thrown.expect(IllegalArgumentException.class);

        createProfilesClient().registerNewProfile("Sample user name", null, null, null);
    }

    @Test
    public void registerNewProfile_emptyUserEmail_Test() {
        thrown.expect(IllegalArgumentException.class);

        createProfilesClient().registerNewProfile("Sample user name", "", null, null);
    }

    @Test
    public void registerNewProfile_allDataPassed_Success_Test() {

        final String userName = "SampleUserName";
        final String userEmail = "Sample@user.email.com";
        final String name = "Name";
        final String surname = "Surname";
        final UUID newProfileUuid = UUID.randomUUID();
        final RegisterNewProfileResponseStatus profileResponseStatus = RegisterNewProfileResponseStatus.OK;

        RegisterNewProfileResponseMessage responseMessage = new RegisterNewProfileResponseMessage();
        responseMessage.setNewProfileUuid(newProfileUuid);
        responseMessage.setRegisterNewProfileResponseStatus(profileResponseStatus);

        when(restClient.sendRequestGetResponse(anyString(), anyObject(), eq(RegisterNewProfileResponseMessage.class)))
                .thenReturn(responseMessage);

        // call the tested method
        RegisterNewProfileResponseMessage actualResponseMessage = createProfilesClient().registerNewProfile(userName, userEmail, name, surname);

        // assertion
        assertEquals(responseMessage, actualResponseMessage);
        assertEquals(responseMessage.getNewProfileUuid(), actualResponseMessage.getNewProfileUuid());
        assertEquals(responseMessage.getRegisterNewProfileResponseStatus(), actualResponseMessage.getRegisterNewProfileResponseStatus());

        ArgumentCaptor<RegisterNewProfileRequestMessage> messageCaptor = ArgumentCaptor.forClass(RegisterNewProfileRequestMessage.class);

        verify(restClient, times(1)).sendRequestGetResponse(
                eq(urlProfilesRegisterProfile), messageCaptor.capture(), eq(RegisterNewProfileResponseMessage.class));

        assertEquals(userName, messageCaptor.getValue().getUserName());
        assertEquals(userEmail, messageCaptor.getValue().getEmail());
        assertEquals(name, messageCaptor.getValue().getName());
        assertEquals(surname, messageCaptor.getValue().getSurname());
    }


    private ProfilesClientImpl createProfilesClient() {
        return new ProfilesClientImpl(restClient);
    }

}
