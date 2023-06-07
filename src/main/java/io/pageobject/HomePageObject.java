package io.pageobject;

import io.WebElementWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageObject {

    @FindBy(xpath = "//*[@id='signin2']")
    private WebElement signUp;

    @FindBy(xpath = "//*[@id='login2']")
    private WebElement logIn;

    @FindBy(xpath = "//*[@id='cartur']")
    private WebElement cart;

    @FindBy(linkText = "Samsung galaxy s6")
    private WebElement linkProductByTitle;

    @FindBy(id = "nameofuser")
    WebElement nameOfUser;
    private final WebDriverWait wait;
    private final WebDriver driver;

    public HomePageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public WebElementWrapper getSignUp() {
        wait.until(ExpectedConditions.elementToBeClickable(signUp));
        return new WebElementWrapper(signUp);
    }
    public WebElementWrapper getLogIn() {
        wait.until(ExpectedConditions.elementToBeClickable(logIn));
        return new WebElementWrapper(logIn);
    }

    public WebElementWrapper getLoginUserName() {
        wait.until(ExpectedConditions.elementToBeClickable(nameOfUser));
        return new WebElementWrapper(nameOfUser);
    }
    public WebElementWrapper getCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart));
        return new WebElementWrapper(cart);
    }

    public WebElementWrapper getLinkProductByTitle() {
        wait.until(ExpectedConditions.elementToBeClickable(linkProductByTitle));
        return new WebElementWrapper(linkProductByTitle);
    }

    public WebElementWrapper getOpenCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart));
        return new WebElementWrapper(cart);
    }
}
