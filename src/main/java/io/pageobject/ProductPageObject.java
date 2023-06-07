package io.pageobject;

import io.WebElementWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPageObject {

    @FindBy(xpath = "//*[@class='price-container']")
    private WebElement price;

    @FindBy(linkText = "Add to cart")
    private WebElement linkAddCart;

    private final WebDriverWait wait;
    private final WebDriver driver;

    public ProductPageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public WebElementWrapper getPrice() {
        wait.until(ExpectedConditions.visibilityOf(price));
        return new WebElementWrapper(price);
    }

    public WebElementWrapper getLinkAddCart() {
        wait.until(ExpectedConditions.elementToBeClickable(linkAddCart));
        return new WebElementWrapper(linkAddCart);
    }

    public WebElementWrapper getAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        return new WebElementWrapper(driver.switchTo().alert());
    }

}
