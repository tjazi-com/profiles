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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by Krzysztof Wasiak on 29/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfileClient_Tests {

    private final static String PROFILES_SERVICE_NAME = "profiles-service";

    private final static String PROFILES_REGISTRATION_URL = "http://" + PROFILES_SERVICE_NAME + "/profiles/register";
    private final static String urlProfilesRegisterProfile = "http://" + PROFILES_SERVICE_NAME + "/profiles/registerprofile";
    private final static String urlProfilesProfileDetails = "http://" + PROFILES_SERVICE_NAME + "/profiles/profiledetails";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    public RestTemplate restTemplate;

    @Mock
    public MessageChannel messageChannel;

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
                anyString(), anyObject(), eq(GetProfileDetailsResponseMessage.class), (Object)eq(null)))
                .thenReturn(responseMessage);

        // main call
        GetProfileDetailsResponseMessage profileDetailsActual = profilesClient.getProfileDetails(targetProfileUuid);

        // verify calls
        verify(restTemplate, times(1)).postForObject(
                eq(urlProfilesProfileDetails),
                requestMessageArgumentCaptor.capture(),
                eq(GetProfileDetailsResponseMessage.class),
                (Object) eq(null));

        assertEquals(targetProfileUuid, requestMessageArgumentCaptor.getValue().getProfileUuid());
        assertEquals(responseMessage, profileDetailsActual);
    }

    @Test
    public void registerNewProfile_NullProfileUuid_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile(null, "sample user name", "Sample email", "", "");
    }

    @Test
    public void registerNewProfile_NullUserName_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile(UUID.randomUUID(), null, "Sample email", null, null);
    }

    @Test
    public void registerNewProfile_emptyUserName_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile(UUID.randomUUID(), "", "Sample email", null, null);
    }

    @Test
    public void registerNewProfile_NullUserEmail_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile(UUID.randomUUID(), "Sample user name", null, null, null);
    }

    @Test
    public void registerNewProfile_emptyUserEmail_Test() {
        thrown.expect(IllegalArgumentException.class);

        profilesClient.registerNewProfile(UUID.randomUUID(), "Sample user name", "", null, null);
    }

    @Test
    public void registerNewProfile_allDataPassed_Success_Test() {

        final String userName = "SampleUserName";
        final String userEmail = "Sample@user.email.com";
        final String name = "Name";
        final String surname = "Surname";
        final UUID newProfileUuid = UUID.randomUUID();

        when(restTemplate.postForObject(
                eq(PROFILES_REGISTRATION_URL), anyObject(), eq(boolean.class), (Object)eq(null)))
                .thenReturn(true);

        // call the tested method
        profilesClient.registerNewProfile(newProfileUuid, userName, userEmail, name, surname);

        // assertion

        ArgumentCaptor<RegisterNewProfileRequestCommand> messageCaptor = ArgumentCaptor.forClass(RegisterNewProfileRequestCommand.class);

        verify(restTemplate, times(1)).postForObject(
                eq(PROFILES_REGISTRATION_URL),
                messageCaptor.capture(),
                eq(boolean.class),
                (Object) eq(null));

        RegisterNewProfileRequestCommand sentCommand = messageCaptor.getValue();

        assertEquals(newProfileUuid, sentCommand.getProfileUuid());
        assertEquals(userName, sentCommand.getUserName());
        assertEquals(userEmail, sentCommand.getEmail());
        assertEquals(name, sentCommand.getName());
        assertEquals(surname, sentCommand.getSurname());
    }
}
