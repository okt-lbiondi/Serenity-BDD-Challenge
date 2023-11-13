package Yelp.pageobject;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.actions.Scroll;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPage extends PageObject {
    @FindBy(xpath="//*[@name='find_desc']")
    WebElementFacade findButton;
    @FindBy (xpath="//menu[contains(@class, 'suggestions-list')]//*[text()='Restaurants']")
    WebElementFacade restaurantsItem;
    @FindBy (xpath = "//input[@id='search_description']")
    WebElementFacade searchBox;
    @FindBy (xpath="//form[@id='header_find_form']//button[@value='submit']")
    WebElementFacade searchButton;
    @FindBy (xpath="//p[contains(text(), 'Neighborhoods')]//following::a[1]")
    WebElementFacade seeAllNeighborhood;
    @FindBy (xpath="//span[contains(text(), 'Search')]/parent::button")
    WebElementFacade searchFilterButton;
    @FindBy (xpath="//*[contains(text(), 'Phone number')]//following-sibling::p")
    WebElementFacade restaurantPhone;
    @FindBy (xpath="//*[contains(text(),'Get Directions')]//parent::p//following-sibling::p")
    WebElementFacade restaurantAddress;
    @FindBy (xpath="//span[contains(text(),'Yelp Sort')]/parent::button")
    WebElementFacade reviewSortByButton;
    @FindBy (xpath="//*[contains(text(),'Oldest First')]//ancestor::button")
    WebElementFacade oldestFirstButton;

    static String xpathRestaurantName="//*[contains(@class,'ResultsContainer')]//descendant::h3";
    static Integer count;
    static List<WebElementFacade> myListOfRestaurants;
    static List<WebElementFacade> myReviewsOfRestaurant;

    public void searchRestaurant(){
        findButton.click();
        withTimeoutOf(10,TimeUnit.SECONDS).waitFor(restaurantsItem).waitUntilClickable();
        restaurantsItem.click();
    }

    public void searchTypeRestaurant(String restaurant){
        searchBox.clear();
        searchBox.click();
        searchBox.sendKeys(restaurant);
        searchButton.click();
        myListOfRestaurants=withTimeoutOf(20, TimeUnit.SECONDS).findAll(By.xpath(xpathRestaurantName));
        numSearchPerPage(myListOfRestaurants);
        Serenity.recordReportData().withTitle("Total Results").andContents("First Page Total Results is: "+ count);
    }

    public void selectFilter(String neighborhood){
        Scroll.to(seeAllNeighborhood);
        seeAllNeighborhood.click();
        String filterXpath="//span[contains(text(),'"+neighborhood+"')]//parent::div//parent::div//preceding::div[2]";
        WebElement filterNeighborhood=withTimeoutOf(10,TimeUnit.SECONDS).find(By.xpath(filterXpath));
        filterNeighborhood.click();
        searchFilterButton.click();
    }

    public void selectFirstResults(){
        myListOfRestaurants=withTimeoutOf(10,TimeUnit.SECONDS).findAll(By.xpath(xpathRestaurantName));
        for (WebElementFacade inputElement : myListOfRestaurants){
            if (inputElement.getText().contains(".")){
                Serenity.recordReportData().withTitle("Restaurant Name").andContents(inputElement.getText());
                inputElement.click();
                break;
            }
        }
    }

    public void getDetails() throws InterruptedException {
        String phone="Restaurant's phone: "+restaurantPhone.getText();
        String address="Restauran's address: "+restaurantAddress.getText();
        //Scroll.to(reviewSortByButton);
        reviewSortByButton.click();
        oldestFirstButton.click();
        Thread.sleep(10000);
        String xpathReviews="//p[contains(@class,'comment')]";
        myReviewsOfRestaurant=withTimeoutOf(10,TimeUnit.SECONDS).findAll(By.xpath(xpathReviews));
        String firstReview=  "FIRST REVIEW: \n" + myReviewsOfRestaurant.get(0).getText();
        String secondReview= "SECOND REVIEW: \n" + myReviewsOfRestaurant.get(1).getText();
        String thirdReview=  "THIRD REVIEW: \n" + myReviewsOfRestaurant.get(2).getText();
        String details= phone + "\n" + address + "\n\n" +
                firstReview + "\n\n" + secondReview + "\n\n" + thirdReview;
         Serenity.recordReportData().withTitle("Details and Reviews").andContents(details);
    }

    public void numSearchPerPage(List<WebElementFacade> myList){
        count=0;
        for (WebElementFacade inputElement : myList){
            if (inputElement.getText().contains(".")){
                count++;
            }
        }
    }
}
