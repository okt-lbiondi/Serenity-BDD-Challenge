package SauceDemo.pageobject;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends PageObject {

    @FindBy(id = "cart_contents_container")
    WebElementFacade cartContainer;

    @FindBy(id = "checkout")
    WebElementFacade checkoutButton;

    @FindBy(id = "first-name")
    WebElementFacade fistNameInput;

    @FindBy(id = "last-name")
    WebElementFacade lastNameInput;

    @FindBy(id = "postal-code")
    WebElementFacade zipcodeInput;

    @FindBy(id = "continue")
    WebElementFacade continueCheckoutButton;

    @FindBy(id = "checkout_summary_container")
    WebElementFacade checkoutSummaryContainer;

    @FindBy(xpath = "//div[contains(@class,'summary_total_label')]")
    WebElementFacade totalPriceLabel;

    @FindBy(className = "summary_subtotal_label")
    WebElementFacade subTotalPriceLabel;
    @FindBy(className = "summary_tax_label")
    WebElementFacade taxPriceLabel;

    @FindBy(id = "finish")
    WebElementFacade finishButton;

    @FindBy(id = "checkout_complete_container")
    WebElementFacade checkoutCompleteContainer;

    @FindBy(className = "complete-header")
    WebElementFacade checkoutCompleteHeader;

    @FindBy(className = "summary_value_label")
    List<WebElementFacade> summaryValues;

    public boolean isNumberOfProductsEqualTo(int products) {
        List<WebElementFacade> cartItems = cartContainer.thenFindAll(By.className("cart_item"));
        List<String> cartItemNames = getCartProductNames(cartItems);
        List<Double> cartItemPrices = getCartProductPrices(cartItems);
        List<String> inventoryItemNames = new ArrayList<>();
        List<Double> inventoryItemPrices = new ArrayList<>();

        for (int i=0; i < products; i++) {
            inventoryItemNames.add(HomePage.inventoryItemNames.get(i));
            inventoryItemPrices.add(HomePage.inventoryItemPrices.get(i));
        }

        return cartItemNames.size() == products
                && cartItemNames.equals(inventoryItemNames)
                && cartItemPrices.equals(inventoryItemPrices);
    }

    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    public void fillPersonalData(String fistname, String lastname, String zipcode) {
        fistNameInput.sendKeys(fistname);
        lastNameInput.sendKeys(lastname);
        zipcodeInput.sendKeys(zipcode);
    }

    public void clickContinueToStepTwo() {
        continueCheckoutButton.click();
    }

    public Double getCartTotalPrice() {
        return Double.valueOf(totalPriceLabel.getText().substring(8));
    }

    public double getCartSumPrice(){
        double taxPrice = getTaxPrice();
        List<Double> cartItemPrices = getCartProductPrices(checkoutSummaryContainer.thenFindAll(By.className("cart_item")));

        double cartSumPrice= 0.00 + taxPrice;

        for (double num : cartItemPrices){
            cartSumPrice = cartSumPrice + num;
        }

        return cartSumPrice;
    }

    public double getInventorySumPrice(){
        double taxPrice = getTaxPrice();
        List<Double> cartItemPrices = getCartProductPrices(checkoutSummaryContainer.thenFindAll(By.className("cart_item")));

        double inventorySumPrice = 0.00 + taxPrice;

        for (int i = 0; i < cartItemPrices.size(); i++){
            inventorySumPrice = inventorySumPrice + HomePage.inventoryItemPrices.get(i);
        }

        return inventorySumPrice;
    }

    public List<String> getCartProductNames(List<WebElementFacade> elements){
        List<String> cartItemNames = new ArrayList<>();

        for (WebElementFacade element : elements){
            String name = element.find(By.className("inventory_item_name")).getText();
            cartItemNames.add(name);
        }

        return cartItemNames;
    }

    public List<Double> getCartProductPrices(List<WebElementFacade> elements){
        List<Double> cartItemPrices = new ArrayList<>();

        for (WebElementFacade element : elements) {
            Double price = Double.valueOf(element.find(By.className("inventory_item_price")).getText().substring(1));
            cartItemPrices.add(price);
        }

        return cartItemPrices;
    }

    public void clickFinishCheckout() {
        finishButton.click();
    }

    public boolean isCheckoutCompletedPageVisible() {
        return checkoutCompleteContainer.isVisible();
    }

    public String getCheckoutCompleteHeaderText() {
        return checkoutCompleteHeader.getText();
    }

    public String getCartSubTotalPrice() {
        return subTotalPriceLabel.getText().substring(13);
    }

    public Double getTaxPrice() {
        return Double.valueOf(taxPriceLabel.getText().substring(6));
    }

    public String getSummaryValues(int i) {
        return summaryValues.get(i).getText();
    }
}
