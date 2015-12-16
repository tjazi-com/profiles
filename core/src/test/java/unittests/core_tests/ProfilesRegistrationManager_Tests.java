package unittests.core_tests;

import com.tjazi.profiles.service.core.ProfilesRegistrationManagerImpl;
import com.tjazi.profiles.service.dao.ProfileDAO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Krzysztof Wasiak on 12/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class ProfilesRegistrationManager_Tests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    public ProfilesRegistrationManagerImpl profilesRegistrationManager;

    @Mock
    public ProfileDAO profileDAO;

    @Test
    public void registerNewProfile_ExceptionOnNullInput() {
        thrown.expect(IllegalArgumentException.class);

        profilesRegistrationManager.registerNewProfile(null);
    }


}
