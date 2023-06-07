package io;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {

    private WebDriver driver;
    @Override
    public void onTestStart(ITestResult iTestResult) {
        driver = WebDriverFactoryThreadLocal.get();
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            if(driver != null) {
                takeSnapShot(driver, iTestResult);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {

        System.out.println("onFinish(ITestContext iTestContext)");
    }
    @Attachment(value = "Page Screenshot", type = "image/png")
    private void takeSnapShot(WebDriver driver, ITestResult result) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot)driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File dstFile = new File("./ScreenShots/onTestFailure-" + result.getName() + "_" + result.getEndMillis() + ".jpg");
        FileUtils.moveFile(srcFile, dstFile);
        Allure.addAttachment("Screenshot", "image/png", String.valueOf(dstFile.canRead()), "png");
        textLog("Screenshot was captured for method: " + result.getName());
    }

    @Attachment(value = "{0}", type = "text/plain")
    private String textLog(String msg) {
        return msg;
    }
}