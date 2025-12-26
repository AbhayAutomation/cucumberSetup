package pageObject;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.Log;

public class test {

    public By userName = By.xpath("//input[@name='email']");
    public By password = By.xpath("//input[@name='password']");
    public By eyeBtn = By.xpath("//button[@type='button']");
    public By loginBtn = By.xpath("//button[@type='submit']");
    public By getStartedBtn = By.xpath("//a[@href='/sign-up']");
    public By forgotPassBtn = By.xpath("//a[@href='/reset-password']");
    public By needHelpPage = By.xpath("//a[@href='/faqs']");
    public By logo = By.xpath("//a[@aria-label='Logo']");

    @Test
    public void testVal(){
        System.out.println("Step1");
        Log.info("Failed");
        Assert.assertEquals(2,2,"Assert failed values are not equals");

        System.out.println("Step2");
        Assert.assertTrue(false);
        System.out.println("Step5");
    }

    @Test
    public void testVal1(){
        System.out.println("Step3");

        Assert.assertEquals(2,3,"Assert failed values are not equals");

        System.out.println("Step4");
    }

    @Test
    public void softTest(){

        SoftAssert soft = new SoftAssert();

        System.out.println("Step11");
        soft.assertEquals(2,3, "Assert failed Values are not equals");
        System.out.println("Step 12");
        soft.assertTrue(false);
        System.out.println("step 13");

        soft.assertAll();
    }

}
