package pageObject;

import helper.BaseTest;
import org.openqa.selenium.By;

public class Hafele_HomePage extends BaseTest {

    By acceptCookiesBtn = By.xpath("//button[@id='onetrust-accept-btn-handler']");
//    By acceptCookiesBtn = By.xpath("//button[@id='onetrust-accept-btn-handler']");

    public void clickOnAcceptCookies(){
        click(acceptCookiesBtn);
    }
}
