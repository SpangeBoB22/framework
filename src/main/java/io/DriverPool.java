package io;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverPool {
    private static final DriverPool instance;

    private DriverPool() {}

    static {
        try {
            instance = new DriverPool();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }
    public static WebDriver getDriver(DriverType type) {
        return switch (type) {
            case FIREFOX -> firefoxDriver();

            case CHROME -> chromeDriver();

            default -> null;
        };
    }
    private static WebDriver firefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver(new FirefoxOptions());
        driver.manage().window().maximize();
        return  driver;
    }
    private static WebDriver chromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920x1080");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        Dimension dm = new Dimension(1552, 849);
        driver.manage().window().setSize(dm);
        return driver;
    }
    public static void quitDriver(WebDriver driver) {
        driver.quit();
    }
}
