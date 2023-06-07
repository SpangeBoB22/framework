package io;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;

public class WebElementWrapper {

    private WebElement element;
    private Alert alert;

    public WebElementWrapper(WebElement element) {
        this.element = element;
    }
    public WebElementWrapper(Alert alert) {
        this.alert = alert;
    }
    public void clickButton() {
        element.click();
    }
    public void clickLink() {
        element.click();
    }

    public void setText(String text) {
        element.sendKeys(text);
    }
    public String getText() {
        return element.getText();
    }

    public boolean isVisible() {
        return element.isDisplayed();
    }

    public String getAlertText() {
        return alert.getText();
    }
    public void accept(){
        alert.accept();
    }
}
