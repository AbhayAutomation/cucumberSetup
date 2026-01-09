package pageObject;

import helper.BaseTest;
import org.openqa.selenium.By;

public class Hafele_HomePage extends BaseTest {

    By acceptCookiesBtn = By.xpath("//button[@id='onetrust-accept-btn-handler']");
    By searchBox = By.xpath("//input[@id='inputSearchTerm']");
    By searchBtn = By.xpath("//button[@data-searchid='searchButton']");
    By searchResultsText = By.xpath("//h3[@class='shopItemHeadline shop-head-text u-bold']/a");
    By addToCartBtn = By.xpath("//a[@data-testid='AddToCartLink']");
    By noResultsFoundText = By.xpath("//span[@class='resultCount']");
//    By acceptCookiesBtn = By.xpath("//button[@id='onetrust-accept-btn-handler']");

    public void clickOnAcceptCookies(){
        click(acceptCookiesBtn);
    }

    public void searchProduct(String productName){
        clearAndEnter(searchBox, productName);
        click(searchBtn);
    }
    public void verifySearchResults(){
        String text = getElementText(searchResultsText);
        System.out.println(text);
        softAssertTrue(isDisplayed(searchResultsText), "Search results are not displayed");

    }

    public boolean isAddToCartButtonDisplayed(){
       return isDisplayed(addToCartBtn);
    }

    public boolean isNoResultsFoundMessageDisplayed(){
        return isDisplayed(noResultsFoundText);
    }

}
