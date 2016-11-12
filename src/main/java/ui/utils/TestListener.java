package ui.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {

    @Attachment
    public File captureScreenshot(WebDriver d) {
        File file = null;
        try {
            file = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);

        }catch (WebDriverException e){
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

        System.out.println("============onTestStart============");

    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        System.out.println("============onTestSuccess============");

        File screen = captureScreenshot((WebDriver) DriverManager.getDriver());
        try {
            FileUtils.copyFile(screen, new File("C://Users/Storm/Desktop/scr/test/" + tr.getMethod().getMethodName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("============onTestFailure============");
            System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
            File screen = captureScreenshot((WebDriver) DriverManager.getDriver());
            try {
                FileUtils.copyFile(screen, new File("C://Users/Storm/Desktop/scr/test/" + tr.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        //WebDriver driver = DriverManager.getDriver();
        //driver.quit();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("============onTestSkipped============");

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("============onTestFailedButWithinSuccessPercentage============");

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("============onStart============");
        String browserName = iTestContext.getCurrentXmlTest().getParameter("browserName");
        String useGrid = iTestContext.getCurrentXmlTest().getParameter("useGrid");
        Boolean boo = useGrid.contentEquals("true");
        WebDriver driver = GridDriverConfig.createInstance(browserName, boo);
        DriverManager.setWebDriver(driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("============onFinish============");
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
        }

    }
}