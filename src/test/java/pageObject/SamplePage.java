package pageObject;

import org.openqa.selenium.By;

import helper.BaseTest;
import utils.DataGeneratorUtil;
import utils.Log;

public class SamplePage extends BaseTest {

    public By userName = By.xpath("//input[@name='email']");
    public By password = By.xpath("//input[@name='password']");
    public By loginBtn = By.xpath("//button[@type='submit']");
    public By logoText = By.xpath("");
    public By firstName = By.xpath("");
    public By lastName = By.xpath("");
    public By email = By.xpath("");
    public By phoneNumber = By.xpath("");
    public By address = By.xpath("");


    public void urlLaunch() {
//        setup();
    }
    public void login(){
        clearAndEnter(userName, "");
        clearAndEnter(password, "");
        click(loginBtn);
    }

    public void validateLogin(){
        hardAssertTrue(waitForElement(logoText).isDisplayed(),"Text Mismatched");
        Log.info("Login Successfully");
    }

    public void registration(){
        clearAndEnter(userName, DataGeneratorUtil.getFullName());
        clearAndEnter(email, DataGeneratorUtil.getEmail());
        clearAndEnter(phoneNumber, DataGeneratorUtil.getRandomPhone());
        clearAndEnter(address, DataGeneratorUtil.getRandomAddress());


    }
}
