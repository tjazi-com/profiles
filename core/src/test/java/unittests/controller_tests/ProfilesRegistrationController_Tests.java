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

        assertTrue(true);
    }
}
