package io.businessobject;

import io.WebElementWrapper;
import io.pageobject.ProductPageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class ProductBusinessObject {

    private final ProductPageObject productPageObject;

    public ProductBusinessObject(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5, 500);
        productPageObject = new ProductPageObject(driver, wait);
    }

    @Step("Get price product")
    public String getPriceProduct() {
        return Arrays.stream(productPageObject.getPrice().getText().split(" "))
                .findFirst().orElseThrow().substring(1);
    }
    @Step("Add product to cart")
    public void addProductToCart() {
        productPageObject.getLinkAddCart().clickButton();
    }

    @Step("Check added product to cart")
    public boolean checkAddProductToCart() {
        WebElementWrapper alert = productPageObject.getAlert();
        if (alert.getAlertText().equals("Product added.")) {
            alert.accept();
            return true;
        } else {
            return false;
        }
    }

}
