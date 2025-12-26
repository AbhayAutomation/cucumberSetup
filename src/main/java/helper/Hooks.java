package helper;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestResult;
import utils.ExtentTestManager;

import java.time.Duration;

public class Hooks extends BaseTest {



    @Before
    public void beforeScenario(Scenario scenario) {

        setup();
    }

    public void setup() {
        String browserName = prop.getProperty("browser").toLowerCase();
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOption = new ChromeOptions();
//                chromeOption.addArguments("--incognito");
//                chromeOption.addArguments("--headless");
                chromeOption.addArguments("--ignore-certificate-errors");
                chromeOption.addArguments("--disable-infoBars");
                chromeOption.addArguments("--disable-gpu");
                chromeOption.addArguments("--disable-extensions");
                chromeOption.addArguments("--allow-insecure-localhost");
                chromeOption.addArguments("--disable-notifications");
                chromeOption.addArguments("--disable-web-security");
                chromeOption.addArguments("--disable-popup-blocking");
                chromeOption.setExperimentalOption("useAutomationExtension", false);
                chromeOption.setExperimentalOption("excludeSwitches",
                        java.util.Collections.singletonList("enable-automation"));
                driver = new ChromeDriver(chromeOption);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOption = new EdgeOptions();
//                edgeOption.addArguments("--inPrivate");
                edgeOption.addArguments("--disable-notifications");
                edgeOption.addArguments("--ignore-certificate-errors");
                driver = new EdgeDriver(edgeOption);
                break;

            default:
                throw new IllegalArgumentException("Invalid browser name: " + prop.getProperty("browser"));
        }
        driver.get(prop.getProperty("siteUrl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(prop.getProperty("timeout"))));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(prop.getProperty("timeout"))));
    }

    public void tearDown() {
        if (driver != null) {
            try {
                Thread.sleep(2000); // Adding a short delay
            } catch (InterruptedException e) {
                System.out.println("Browser Null");
            }
            driver.close(); // Properly close the session
        }
    }

    @After
    public void afterScenario() {
//        if (result.getStatus()==ITestResult.FAILURE){
//            String path = takeScreenShot(result.getName());
//            try {
//                ExtentTestManager.getTest().fail("Test Failed - ScreenShot Attached").addScreenCaptureFromPath(path);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        ExtentTestManager.endTest();

        if (driver != null){
            ExtentTestManager.endTest();
        }
    }

}
