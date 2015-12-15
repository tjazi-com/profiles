package unittests;


import com.tjazi.profiles.client.ProfilesClientImpl;
import com.tjazi.profiles.messages.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by Krzysztof Wasiak on 29/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfileClient_Tests {

    private final static String PROFILES_SERVICE_NAME = "profiles-service";

    private final static String urlProfilesRegisterProfile = "http://" + PROFILES_SERVICE_NAME + "/profiles/registerprofile";
    private final static String urlProfilesProfileDetails = "http://" + PROFILES_SERVICE_NAME + "/profiles/profiledetails";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    public RestTemplate restTemplate;

    @InjectMocks
    public ProfilesClientImpl profilesClient;

    @Test
    public void getProfileDetails_nullProfileUuid_Exception_Test() {

        thrown.expect(IllegalArgumentException.class);

        profilesClient.getProfileDetails(null);
    }

    @Test
    public void getProfileDetails_validUuid_Success_Test() {

        final UUID targetProfileUuid = UUID.randomUUID();
        final GetProfileDetailsResponseMessage responseMessage = new GetProfileDetailsResponseMessage();

        ArgumentCaptor<GetProfileDetailsRequestMessage> requestMessageArgumentCaptor =
                ArgumentCaptor.forClass(GetProfileDetailsRequestMessage.class);

        when(restTemplate.postForObject(
                anyString(), anyObject(), eq(GetProfileDetailsResponseMessage.class), eq(null)))
                .thenReturn(responseMessage);

        // main call
        GetProfileDetailsResponseMessage profileDetailsActual = profilesClient.getProfileDetails(targetProfileUuid);

        // verify calls
        verify(restTemplate, times(1)).postForObject(
                eq(urlProfilesProfileDetails),
                requestMessageArgumentCaptor.capture(),
                eq(GetProfileDetailsResponseMessage.class),
                eq(null));

        assertEquals(targetProfileUuid, requestMessageArgumentCaptor.getValue().getProfileUuid());
        assertEquals(responseMessage, profileDetailsActual);
    }

    @Test
    public void registerNewProfile_NullUserName_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile(null, "Sample email", null, null);
    }

    @Test
    public void registerNewProfile_emptyUserName_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile("", "Sample email", null, null);
    }

    @Test
    public void registerNewProfile_NullUserEmail_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile("Sample user name", null, null, null);
    }

    @Test
    public void registerNewProfile_emptyUserEmail_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile("Sample user name", "", null, null);
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

        when(restTemplate.postForObject(anyString(), anyObject(), eq(RegisterNewProfileResponseMessage.class), eq(null)))
                .thenReturn(responseMessage);

        // call the tested method
        RegisterNewProfileResponseMessage actualResponseMessage = profilesClient.registerNewProfile(userName, userEmail, name, surname);

        // assertion
        assertEquals(responseMessage, actualResponseMessage);
        assertEquals(responseMessage.getNewProfileUuid(), actualResponseMessage.getNewProfileUuid());
        assertEquals(responseMessage.getRegisterNewProfileResponseStatus(), actualResponseMessage.getRegisterNewProfileResponseStatus());

        ArgumentCaptor<RegisterNewProfileRequestMessage> messageCaptor = ArgumentCaptor.forClass(RegisterNewProfileRequestMessage.class);

        verify(restTemplate, times(1)).postForObject(
                eq(urlProfilesRegisterProfile), messageCaptor.capture(), eq(RegisterNewProfileResponseMessage.class), eq(null));

        assertEquals(userName, messageCaptor.getValue().getUserName());
        assertEquals(userEmail, messageCaptor.getValue().getEmail());
        assertEquals(name, messageCaptor.getValue().getName());
        assertEquals(surname, messageCaptor.getValue().getSurname());
    }
}
