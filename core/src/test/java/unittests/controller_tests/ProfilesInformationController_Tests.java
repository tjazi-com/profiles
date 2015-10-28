package unittests.controller_tests;

import com.tjazi.profiles.messages.GetProfileDetailsRequestMessage;
import com.tjazi.profiles.messages.GetProfileDetailsResponseMessage;
import com.tjazi.profiles.service.controller.ProfilesInformationController;
import com.tjazi.profiles.service.core.ProfilesInformationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Krzysztof Wasiak on 28/10/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfilesInformationController_Tests {

    @InjectMocks
    private ProfilesInformationController profilesInformationController;

    @Mock
    private ProfilesInformationProvider profilesInformationProvider;

    @Test
    public void getProfileDetails_PassCallToCore_Test() {

        GetProfileDetailsRequestMessage requestMessage = new GetProfileDetailsRequestMessage();
        GetProfileDetailsResponseMessage responseMessage = new GetProfileDetailsResponseMessage();

        when(profilesInformationProvider.getProfileDetails(any(GetProfileDetailsRequestMessage.class)))
                .thenReturn(responseMessage);

        GetProfileDetailsResponseMessage profileDetailsResponse = profilesInformationController.getProfileDetails(requestMessage);

        // verify calls & returned values

        assertEquals(responseMessage, profileDetailsResponse);
        verify(profilesInformationProvider).getProfileDetails(requestMessage);
    }
}
