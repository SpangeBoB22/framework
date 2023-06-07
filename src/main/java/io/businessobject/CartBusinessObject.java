package io.businessobject;

import io.pageobject.CartPageObject;
import io.pageobject.ProductPageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class CartBusinessObject {

    private final CartPageObject cartPageObject;

    public CartBusinessObject(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5, 500);
        cartPageObject = new CartPageObject(driver, wait);
    }

    @Step("Get total from cart")
    public String getTotal() {
        return cartPageObject.getTotalInCart().getText();
    }

}
