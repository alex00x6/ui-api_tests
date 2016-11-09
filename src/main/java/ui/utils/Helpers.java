package ui.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertFalse;


/**
 * Created by Storm on 27.10.2016.
 */
public class Helpers {

    public void makeScreenshot(String name, WebDriver driver, String date){
        sleep(1000);
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screen, new File("C://Users/Storm/Desktop/scr/"+date+"/"+name+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sleep(int time){
        //Ожидаем
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("(dd.MM.yyyy) HH-mm-ss");
        Date date = new Date();
        String now = dateFormat.format(date);
        return now;
    }

    public void scrollPageUp(WebDriver driver){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, -250);");
    }

    public void waitForVisibilityByXpath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitForClickableByXpath(WebDriver driver, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public void assertTextByXpath(WebDriver driver, String XPath, String text){
        driver.navigate().refresh();
        waitForVisibilityByXpath(driver, XPath);
        String aIssueTitle = driver.findElement(By.xpath(XPath)).getText();
        assertTrue(aIssueTitle.contains(text));
    }

    public void assertNotEqualsByXpath(WebDriver driver, String XPath, String text){
        driver.navigate().refresh();
        waitForVisibilityByXpath(driver, XPath);
        String aIssueTitle = driver.findElement(By.xpath(XPath)).getText();
        assertFalse(aIssueTitle.contains(text));
    }

    public void assertEqualsByTitle(WebDriver driver, String title){
        String currentTitle = driver.getTitle();
        assertEquals(currentTitle, title);
    }

    public void assertContainsByTitle(WebDriver driver, String title){
        String currentTitle = driver.getTitle();
        assertTrue(currentTitle.contains(title));
    }

}
