package helper;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;


import org.testng.Assert;
//import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;
import utils.ExtentTestManager;

public class BaseTest {

    public static WebDriver driver;
    public static Properties prop;
    public static WebDriverWait wait;
    SoftAssert soft = new SoftAssert();

    static {
        FileInputStream file;
        try {
            file = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/resources/env.properties");
            prop = new Properties();
            prop.load(file);
        } catch (Exception e) {
            System.err.println("File Path is not correct");
        }
    }

//    @Before
//    public void beforeScenario(Scenario scenario) {
//        setup();
//    }
//
//    public void setup() {
//        String browserName = prop.getProperty("browser").toLowerCase();
//        switch (browserName) {
//            case "chrome":
//                WebDriverManager.chromedriver().setup();
//                ChromeOptions chromeOption = new ChromeOptions();
////                chromeOption.addArguments("--incognito");
////                chromeOption.addArguments("--headless");
//                chromeOption.addArguments("--ignore-certificate-errors");
//                chromeOption.addArguments("--disable-infoBars");
//                chromeOption.addArguments("--disable-gpu");
//                chromeOption.addArguments("--disable-extensions");
//                chromeOption.addArguments("--allow-insecure-localhost");
//                chromeOption.addArguments("--disable-notifications");
//                chromeOption.addArguments("--disable-web-security");
//                chromeOption.addArguments("--disable-popup-blocking");
//                chromeOption.setExperimentalOption("useAutomationExtension", false);
//                chromeOption.setExperimentalOption("excludeSwitches",
//                        java.util.Collections.singletonList("enable-automation"));
//                driver = new ChromeDriver(chromeOption);
//                break;
//
//            case "edge":
//                WebDriverManager.edgedriver().setup();
//                EdgeOptions edgeOption = new EdgeOptions();
////                edgeOption.addArguments("--inPrivate");
//                edgeOption.addArguments("--disable-notifications");
//                edgeOption.addArguments("--ignore-certificate-errors");
//                driver = new EdgeDriver(edgeOption);
//                break;
//
//            default:
//                throw new IllegalArgumentException("Invalid browser name: " + prop.getProperty("browser"));
//        }
//        driver.get(prop.getProperty("siteUrl"));
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(prop.getProperty("timeout"))));
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(prop.getProperty("timeout"))));
//    }
//
//    public void tearDown() {
//        if (driver != null) {
//            try {
//                Thread.sleep(2000); // Adding a short delay
//            } catch (InterruptedException e) {
//                System.out.println("Browser Null");
//            }
//            driver.close(); // Properly close the session
//        }
//    }
//
//    @After
//    public void afterScenario(ITestResult result) {
////        if (result.getStatus()==ITestResult.FAILURE){
////            String path = takeScreenShot(result.getName());
////            try {
////                ExtentTestManager.getTest().fail("Test Failed - ScreenShot Attached").addScreenCaptureFromPath(path);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////        ExtentTestManager.endTest();
//
//        if (driver != null){
//            ExtentTestManager.endTest();
//        }
//    }

    /************** ScreenShot *** @return******************/
    public String takeScreenShot(String ssName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(src, new File(System.getProperty("user.dir") + "/Screenshots/test" + "/" + ssName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ssName;
    }

    /************** DropDown methods ******************/
    public void selectValueFromSelectDd(By locator, String value, String type) {
        Select s = new Select(waitForElement(locator));
        switch (type) {
            case "visibleText":
                s.selectByVisibleText(value);
                break;
            case "byValue":
                s.selectByValue(value);
                break;
            case "byIndex":
                s.selectByIndex(Integer.parseInt(value));
                break;
            default:
                throw new IllegalArgumentException("Invalid Select Type");
        }
    }

    public void selectByBootStrapDD(By locator, String value) {
        List<WebElement> list = driver.findElements(locator);
        for (WebElement ele : list) {
            if (ele.getText().equals(value)) {
                click(By.xpath(getElementXpath(ele)));
                break;
            }
        }
    }

    /************** utility to get Xpath of webElement (used in BootStrap dropdown ******************/

    private String getElementXpath(WebElement element) {
        return element.toString().split(" -> ")[1].replace("]", "");
    }


    public void click(By locator) {
        try {
            waitForElement(locator).click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", waitForElement(locator));
        }
    }

    public void clearAndEnter(By locator, String value) {
        try {
            waitForElement(locator).clear();
            waitForElement(locator).sendKeys(value);
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value= arguments[1]);", waitForElement(locator), value);
        }
    }

    public boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /************** Window Handles method ******************/
    public void windowHandle(By locator) {
        String parentID = driver.getWindowHandle();
        System.out.println("Parent Window: " + parentID);
        click(locator);
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String windowsIDs : allWindowIDs) {
            System.out.println("TabIDs: " + windowsIDs);
            if (!parentID.equals(windowsIDs)) {
                driver.switchTo().window(windowsIDs);
            }
        }
    }

    public void switchWindow(String actualTitle) {
        Set<String> allWindowIds = driver.getWindowHandles();
        for (String window : allWindowIds) {
            driver.switchTo().window(window);
            if (!driver.getTitle().contains(actualTitle)) {
                continue;
            }
            break;
        }
    }

    /************** ExplicitWait method ******************/

    public WebElement waitForElement(By locator, long timeout) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }

    public WebElement waitForElement(By locator) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }

    /************** Alert Handle ******************/
    public void handleAlert(String type) {
        Alert a = driver.switchTo().alert();
        switch (type) {
            case "accept":
                a.accept();
                break;
            case "cancel":
                a.dismiss();
                break;
            case "enterText":
                a.sendKeys(type);
            default:
                throw new IllegalArgumentException("Invalid Alert Type");
        }
    }

    public void mouseHover(By locator) {
        Actions a = new Actions(driver);
        a.moveToElement(waitForElement(locator)).build().perform();
    }

    /************** get element text ******************/
    public String getElementText(By locator) {
        return waitForElement(locator).getText();
    }

    public String getWindowTitle() {
        return driver.getTitle();
    }

    /************** Switch Frame ******************/
    public void switchToFrame(String id) {
        driver.switchTo().frame(id);
    }

    /************** Soft Assertion ******************/
    public void softAssertEquals(Object actual, Object expected, String message) {
        soft.assertEquals(actual, expected, message);
        soft.assertAll();
    }

    public void softAssertTrue(boolean condition, String message) {
        soft.assertTrue(condition, message);
        soft.assertAll();
    }

    /************** Soft Assertion ******************/
    public void hardAssertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    public void hardAssertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }


}