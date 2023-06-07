package io.pageobject;

import io.WebElementWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPageObject {

    @FindBy(xpath = "//*[@id='totalp']")
    WebElement total;

    WebDriverWait wait;
    public CartPageObject(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public WebElementWrapper getTotalInCart() {
        wait.until(ExpectedConditions.visibilityOf(total));
        return new WebElementWrapper(total);
    }
}
