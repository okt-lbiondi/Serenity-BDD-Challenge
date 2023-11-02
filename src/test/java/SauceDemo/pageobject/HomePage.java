package SauceDemo.pageobject;

import cucumber.api.java.eo.Do;
import jnr.ffi.annotations.In;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HomePage extends PageObject {

    @FindBy(id = "inventory_container")
    WebElementFacade inventoryContainer;

    @FindBy(className = "bm-burger-button")
    WebElementFacade menuButton;

    @FindBy(className = "bm-menu-wrap")
    WebElementFacade menuListContainer;

    @FindBy(id = "about_sidebar_link")
    WebElementFacade aboutListItem;

    @FindBy(className = "product_sort_container")
    WebElementFacade productSort;

    @FindBy(className = "active_option")
    WebElementFacade productSortActiveOption;

    @FindBy(className = "shopping_cart_badge")
    static WebElementFacade cartBadge;

    @FindBy(className = "shopping_cart_link")
    static WebElementFacade cartLink;

    public static List<String> inventoryItemNames = new ArrayList<>();
    public static List<Double> inventoryItemPrices = new ArrayList<>();

    public boolean isInventoryContainerVisible(){
        return inventoryContainer.isVisible();
    }

    public void openMenu() {
        menuButton.click();
        withTimeoutOf(10,TimeUnit.SECONDS).waitFor(menuListContainer).waitUntilVisible();
    }

    public void selectAboutItem() {
        aboutListItem.click();
    }

    public void selectProductSortOption(String option) {
        productSort.selectByVisibleText(option);
    }


    public String isProductSortOptionSelected() {
        return productSortActiveOption.getText();
    }

    public boolean areProductsOrderedByOption(String option) {
        //TODO: switch case to based on option check list is ordered
        Boolean result = false;
        List<WebElementFacade> inventoryItems = inventoryContainer.thenFindAll(By.className("inventory_item"));

        for (WebElementFacade element : inventoryItems){
            String name = element.find(By.className("inventory_item_name")).getText();
            inventoryItemNames.add(name);
        }
        for (WebElementFacade element : inventoryItems) {
            Double price = Double.valueOf(element.find(By.className("inventory_item_price")).getText().substring(1));
            inventoryItemPrices.add(price);
        }

        switch (option) {
            case "Name (A to Z)":
                List<String> sortedNamesAZ = new ArrayList<>(inventoryItemNames);
                Collections.sort(sortedNamesAZ);

                result = inventoryItemPrices.equals(sortedNamesAZ);
                break;
            case "Name (Z to A)":
                List<String> sortedNamesZA = new ArrayList<>(inventoryItemNames);
                Collections.sort(sortedNamesZA, Collections.reverseOrder());

                result = inventoryItemPrices.equals(sortedNamesZA);
                break;
            case "Price (low to high)":
                List<Double> sortedPricesAsc = new ArrayList<>(inventoryItemPrices);
                Collections.sort(sortedPricesAsc);

                result = inventoryItemPrices.equals(sortedPricesAsc);
                break;
            case "Price (high to low)":
                List<Double> sortedPricesDesc = new ArrayList<>(inventoryItemPrices);
                Collections.sort(sortedPricesDesc, Collections.reverseOrder());

                result = inventoryItemPrices.equals(sortedPricesDesc);
                break;
        }

        return result;
    }

    public void addNumberOfProductsToCart(int products) {
        List<WebElementFacade> inventoryItems = inventoryContainer.thenFindAll(By.className("inventory_item"));
        for (int i=0; i < products; i++){
            inventoryItems.get(i).findElements(By.xpath("//div[@class='pricebar']/button")).get(i).click();
        }
    }

    public static int getCartBadgeNumber() {
        return Integer.valueOf(cartBadge.getText());
    }

    public void goToCart() {
        cartLink.click();
    }
}
