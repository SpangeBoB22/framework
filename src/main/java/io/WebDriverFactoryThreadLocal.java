package io;

import org.openqa.selenium.WebDriver;

public class WebDriverFactoryThreadLocal {

    private static final ThreadLocal<WebDriver> threadLocal = new InheritableThreadLocal<>();

    private static final WebDriverFactoryThreadLocal instance;

    private WebDriverFactoryThreadLocal() {}

    static {
        try {
            instance = new WebDriverFactoryThreadLocal();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    public static WebDriverFactoryThreadLocal getInstance() {
        return instance;
    }
    public static void set(WebDriver webDriver) {
        threadLocal.set(webDriver);
    }
    public static WebDriver get() {
        return threadLocal.get();
    }
    public static void remove() {
        threadLocal.remove();
    }
}
