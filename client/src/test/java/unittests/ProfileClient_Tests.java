package unittests;


import com.tjazi.lib.messaging.rest.RestClient;
import com.tjazi.profiles.client.ProfilesClientImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import static org.junit.Assert.*;


/**
 * Created by Krzysztof Wasiak on 29/10/2015.
 */
public class ProfileClient_Tests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    public RestClient restClient;

    @Test
    public void constructor_NullRestClient_Test() {

        thrown.expect(IllegalArgumentException.class);

        ProfilesClientImpl profilesClient = new ProfilesClientImpl(null);
    }
}
