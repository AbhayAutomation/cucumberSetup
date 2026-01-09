package helper;

import io.cucumber.java.*;
import io.cucumber.java.Scenario;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.Log;


public class Hooks extends BaseTest {

    @Before
    public void beforeScenario(Scenario scenario) {
        ExtentTestManager.startTest(scenario.getName());
        Log.info("======Starting scenario: " + scenario.getName() + " ======");
        setup();
    }


    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenShotpath = takeScreenShotAs(scenario.getName());
            ExtentTestManager.getTest()
                    .fail("Scenario Failed - ScreenShot Attached")
                    .addScreenCaptureFromPath(screenShotpath);
        } else {
            ExtentTestManager.getTest().pass("Scenario Passed");
            Log.info("Scenario Passed");
        }
        if (driver != null) {
            tearDown();
        }
    }


//    @AfterAll
//    public static void afterAll() {
//        ExtentManager.getReporter().flush();
//    }

}