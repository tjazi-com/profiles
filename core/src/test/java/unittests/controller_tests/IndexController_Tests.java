package unittests.controller_tests;

import com.tjazi.profiles.service.controller.IndexController;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by USER_NAME_ALREADY_REGISTERED on 16/10/15..
 */
public class IndexController_Tests {

    @Test
    public void displayStatusPage_Test(){
        IndexController controller = new IndexController();

        assertEquals("status", controller.displayStatusPage());
    }
}
