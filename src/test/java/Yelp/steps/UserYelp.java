package Yelp.steps;

import Yelp.pageobject.MainPage;
import net.serenitybdd.core.steps.ScenarioActor;
import net.serenitybdd.annotations.Steps;

import java.util.Set;

public class UserYelp extends ScenarioActor {
    String actor;

    @Steps(shared = true)
    MainPage mainPage;

    public void navigatesTo(){
        mainPage.setDefaultBaseUrl("https://www.yelp.com");
        mainPage.open();
    }

    public void searchFor(){
        mainPage.searchRestaurant();
    }

    public void searchRestaurant(String restaurant){
        mainPage.searchTypeRestaurant(restaurant);
    }

    public void filterBy(String filter){
        mainPage.selectFilter(filter);
    }

    public void enterFirstResult(){
        mainPage.selectFirstResults();
        Set<String> handles = mainPage.getDriver().getWindowHandles();
        mainPage.getDriver().switchTo().window(handles.toArray()[1].toString());
    }

    public void viewDetail() throws InterruptedException {
        mainPage.getDetails();
    }
}
