package unittests.controller_tests;

import com.tjazi.profiles.service.controller.ProfilesRegistrationCommandEndpoint;
import com.tjazi.profiles.service.core.ProfilesRegistrationManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfilesRegistrationCommandEndpoint_Tests {

    @InjectMocks
    private ProfilesRegistrationCommandEndpoint profilesRegistrationController;

    @Mock
    private ProfilesRegistrationManager profilesRegistrationManagerMock;

    @Test
    public void registerNewProfileRequestHandler_Test() {

        assertTrue(true);
    }
}
