package helper;

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
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.Log;

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

    /************** BROWSER SETUP ******************/
    public void setup() {
        String browserName = prop.getProperty("browser").toLowerCase();
        Log.info("Starting Browser:" + browserName);
        try {
            switch (browserName) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOption = new ChromeOptions();
                    chromeOption.addArguments("--incognito");
//                    chromeOption.addArguments("--headless");
                    chromeOption.addArguments("--ignore-certificate-errors");
                    chromeOption.addArguments("--disable-infoBars");
                    chromeOption.addArguments("--disable-gpu");
                    chromeOption.addArguments("--disable-extensions");
                    chromeOption.addArguments("--allow-insecure-localhost");
                    chromeOption.addArguments("--disable-notifications");
                    chromeOption.addArguments("--disable-web-security");
                    chromeOption.addArguments("--disable-popup-blocking");
                    chromeOption.setExperimentalOption("useAutomationExtension", false);
                    chromeOption.setExperimentalOption("excludeSwitches", java.util.Collections.singletonList("enable-automation"));
                    driver = new ChromeDriver(chromeOption);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOption = new EdgeOptions();
                    edgeOption.addArguments("--inPrivate");
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
        } catch (Exception e) {
            Log.error("Browser setup failed: " + e.getMessage());
            System.out.println("Browser could not be launched, Please check the issue: " + e.getMessage());
            throw e;
        }
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


    /************** SCREENSHOT UTILITY ******************/
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

    public String takeScreenShotAs(String ssName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/Screenshots/" + "/" + ssName + ".png";
        File dest = new File(path);
        try {
            FileHandler.copy(src, dest);
        } catch (IOException e) {
            Log.error("Failed to take screenshot: " + e.getMessage());
        }
        return path;
    }

    /************** CENTRALIZED ERROR HANDLER ******************/
    private void handleException(String action, Exception e, By locator) {
        String message = "Exception during: " + action + " | Locator: " + locator + " |Error: " + e.getMessage();
        Log.error(message);
        ExtentTestManager.getTest().fail(message);

        //Attach screenshot
        String screenShotPath = takeScreenShotAs("Error_" + System.currentTimeMillis());
        try {
            ExtentTestManager.getTest().addScreenCaptureFromPath(screenShotPath);
        } catch (Exception ignored) {
        }
    }

    /****** utilityToGet Xpath of webElement (used in BootStrap dropdown) *******/

    private String getElementXpath(WebElement element) {
        return element.toString().split(" -> ")[1].replace("]", "");
    }

    /************** DROPDOWN METHODS ******************/

    public void selectByBootStrapDD(By locator, String value) {
        Log.info("Selecting bootstrap dropdown value: "  +value);
        ExtentTestManager.getTest().info("Select bootstrap dropdown value → "  +value);
        List<WebElement> list = driver.findElements(locator);
        for (WebElement ele : list) {
            if (ele.getText().equals(value)) {
                click(By.xpath(getElementXpath(ele)));
                break;
            }
        }
    }

//    public void selectValueFromSelectDd(By locator, String value, String type) {
//
//        Select s = new Select(waitForElement(locator));
//        Log.info("open dropdown: " + locator + "to select value: " + value + " by type: " + type);
//        ExtentTestManager.getTest().info("Select value: " + value + " by type: " + type + " from dropdown: " + locator);
//        switch (type) {
//            case "visibleText":
//                s.selectByVisibleText(value);
//                break;
//            case "byValue":
//                s.selectByValue(value);
//                break;
//            case "byIndex":
//                s.selectByIndex(Integer.parseInt(value));
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid Select Type");
//        }
//    }

    public void selectDropdownByVisibleText(By locator, String text) {
        Select s = new Select(waitForElement(locator));
        Log.info("Selecting Text from dropDown: " + text + " locator: " + locator);
        ExtentTestManager.getTest().info("Select DropDown By Text → " + text);
        try {
            new Select(driver.findElement(locator)).selectByVisibleText(text);
        } catch (Exception e) {
            handleException("selectDropdownByVisibleText", e, locator);
            throw e;
        }
    }

    public void selectDropdownByValue(By locator, String value) {
        Select s = new Select(waitForElement(locator));
        Log.info("Selecting Value from dropDown: " + value + " locator: " + locator);
        ExtentTestManager.getTest().info("Select DropDown By Value → " + value);
        try {
            new Select(driver.findElement(locator)).selectByValue(value);
        } catch (Exception e) {
            handleException("selectDropdownByValue", e, locator);
            throw e;
        }
    }

    public void selectDropdownByIndex(By locator, int index) {
        Select s = new Select(waitForElement(locator));
        Log.info("Selecting Index from dropDown: " + index + " locator: " + locator);
        ExtentTestManager.getTest().info("Select DropDown By Index → " + index);
        try {
            new Select(driver.findElement(locator)).selectByIndex(index);
        } catch (Exception e) {
            handleException("selectDropdownByIndex", e, locator);
            throw e;
        }
    }

    /************** CLEAR AND ENTER AND CLICKS METHODS ******************/
    public void click(By locator) {
        Log.info("Clicking on element: " + locator);
        ExtentTestManager.getTest().info("Click: " + locator);
        try {
            waitForElement(locator).click();
        } catch (Exception firstException) {
            try {
                Log.warn("Normal click failed, trying JS click: " + locator);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", waitForElement(locator));
            } catch (Exception e) {
                handleException("click", e, locator);
                throw e;
            }
        }
    }

    public void clearAndEnter(By locator, String value) {
        Log.info("Typing in: " + locator + " | Text: " + value);
        ExtentTestManager.getTest().info("Type: " + value + " into " + locator);
        try {
            WebElement ele = waitForElement(locator, 15);
            ele.clear();
            ele.sendKeys(value);
        } catch (Exception firstException) {
            try {
                Log.info("Normal sendkeys failed, setting value using js: " + locator);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].value= arguments[1]);", waitForElement(locator), value);

            } catch (Exception e) {
                handleException("clearAndEnter", e, locator);
                throw e;
            }

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

    public void switchWindow(String title) {
        Log.info("Switching window to title: "+title);
        ExtentTestManager.getTest().info("Swith window -> "+title);
        Set<String> allWindowIds = driver.getWindowHandles();
        for (String window : allWindowIds) {
            driver.switchTo().window(window);
            if (!driver.getTitle().contains(title)) {
                continue;
            }
            break;
        }
    }

    /************** ExplicitWait method ******************/
    public WebElement waitForElement(By locator, long timeout) {
        try {
            Log.info("Waiting for: "+ locator);
            ExtentTestManager.getTest().info("Wait For Element → "+locator);
            wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            return wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
        }catch (Exception e){
            handleException("waitForElement",e,locator);
            throw e;
        }
    }

    public WebElement waitForElement(By locator) {
        return waitForElement(locator, 10);
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
        Log.info("Hovering on: " + locator);
        ExtentTestManager.getTest().info("Mouse Hover -> "+locator);
        try {
            Actions a = new Actions(driver);
            a.moveToElement(waitForElement(locator)).build().perform();
        } catch (Exception e){
            handleException("mouseHover",e,locator);
            throw e;
        }
    }

    /************** get element text ******************/
    public String getElementText(By locator) {
        Log.info("Getting text from element: " + locator);
        ExtentTestManager.getTest().info("Read text from: " + locator);
        try {
            return driver.findElement(locator).getText();
        } catch (Exception e) {
            handleException("getElementText", e, locator);
            throw e;
        }
    }

    public String getWindowTitle() {
        return driver.getTitle();
    }

    /*********** Is Displayed ************/
    public boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /************** Switch Frame ******************/
    public void switchToFrame(By locator) {
        Log.info("Switching To Frame: "+locator);
        ExtentTestManager.getTest().info("Switch To Frame → "+locator);
        try{
            driver.switchTo().frame(waitForElement(locator));
        } catch (Exception e){
            handleException("switchToFrame",e,locator);
            throw e;
        }
    }

    /************** ASSERTIONS ******************/
    public void softAssertEquals(Object actual, Object expected, String message) {
        Log.info("Soft Assert Equals: " + message);
        soft.assertEquals(actual, expected, message);
        soft.assertAll();
    }

    public void softAssertTrue(boolean condition, String message) {
        Log.info("Soft Assert True: " + message);
        soft.assertTrue(condition, message);
        soft.assertAll();
    }

    public void hardAssertEquals(Object actual, Object expected, String message) {
        Log.info("Hard Assert Equals: " + message);
        Assert.assertEquals(actual, expected, message);
    }

    public void hardAssertTrue(boolean condition, String message) {
        Log.info("Hard Assert True: " + message);
        Assert.assertTrue(condition, message);
    }


}