package unittests;

import com.tjazi.profiles.client.ProfilesClientFactoryImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Krzysztof Wasiak on 30/10/2015.
 */
public class ProfilesClientFactory_Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createProfileClient_nullServiceUri_Test() {

        ProfilesClientFactoryImpl profilesClientFactory = new ProfilesClientFactoryImpl();

        thrown.expect(IllegalArgumentException.class);

        profilesClientFactory.createProfilesClient(null);
    }
}
