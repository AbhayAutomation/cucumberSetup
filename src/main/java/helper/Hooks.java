package helper;

import io.cucumber.java.*;
import io.cucumber.java.Scenario;
import utils.ExtentLogger;
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
            String screenshotPath = takeScreenShotAs(scenario.getName());

            ExtentTestManager.getTest()
                    .fail("Scenario Failed")
                    .addScreenCaptureFromPath(screenshotPath);
        } else {
            ExtentLogger.info("Scenario Passed");
        }

        if (driver != null) {
            driver.quit();
        }
    }


    @AfterAll
    public static void afterAll() {
//        ExtentManager.getReporter().flush();
    }

}