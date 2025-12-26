package pageObject;

import helper.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utils.Log;

public class LoginPage extends BaseTest {

    By userName = By.xpath("//input[@name='email']");
    By password = By.xpath("//input[@name='password']");
    By eyeBtn = By.xpath("//button[@type='button']");
    By loginBtn = By.xpath("//button[@type='submit']");
    By getStartedBtn = By.xpath("//a[@href='/sign-up']");
    By forgotPassBtn = By.xpath("//a[@href='/reset-password']");
    By needHelpPage = By.xpath("//a[@href='/faqs']");
    By logo = By.xpath("//h4[contains(text(),'Welcome back')]");
    By leadPageHeading = By.xpath("//h6[contains(text(),'Leads')]");
    By leadPage= By.xpath("//span[contains(text(),'Leads')]/ancestor::a");


    @Test
    public void urlLaunch(){

//        setup();
    }
    @Test
    public void userLogin(){
        clearAndEnter(userName, "praveen.dangi@redvisionglobal.com");
        clearAndEnter(password, "Redv@123");
        click(loginBtn);
    }
    @Test
    public void validateLogo(){

        hardAssertTrue(isDisplayed(logo), "logo is not displayed");
        Log.info("Login Successfully");
        String text = getElementText(logo);
        System.out.println("welcome Text: "+ text);
    }

    @Test
    public void validateGoTOLeadPage(){
        click(leadPage);
        hardAssertTrue(isDisplayed(leadPageHeading), "heading incorrect");
    }


}
