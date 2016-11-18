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
import java.nio.file.Files;

public class TestListener implements ITestListener {

    private String params;

    private File captureScreenshot(WebDriver driver) {
        File file = null;
        try {
            file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            screenshotToAllure(file);
        }catch (WebDriverException e){
            e.printStackTrace();
        }
        return file;
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] screenshotToAllure(File screen) {
        byte[] screenShot = new byte[0];
        try {
            screenShot = Files.readAllBytes(screen.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenShot;
    }


    @Override
    public void onTestStart(ITestResult iTestResult) {

        System.out.println("============onTestStart============");


    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        System.out.println("============onTestSuccess============");
        String pathSucceed = "target/screenshots/"+params+"/success/" + tr.getMethod().getMethodName() + ".png";

        File screen = captureScreenshot(DriverManager.getDriver());
        try {
            FileUtils.copyFile(screen, new File(pathSucceed));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
    }


    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("============onTestFailure============");
        String pathFailed = "target/screenshots/"+params+"/failed/" + tr.getMethod().getMethodName() + ".png";

            File screen = captureScreenshot(DriverManager.getDriver());
            try {
                FileUtils.copyFile(screen, new File(pathFailed));
            } catch (IOException e) {
                e.printStackTrace();
            }

        System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
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
        WebDriver driver = DriverConfigurator.createInstance(browserName, boo);
        DriverManager.setWebDriver(driver);


        Helpers helpers = new Helpers();
        String currentDate = helpers.getTime();

        params = currentDate +"-"+browserName+"-grid-"+useGrid;
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