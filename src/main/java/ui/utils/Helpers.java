package ui.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    private WebDriver driver;

    public Helpers(){
        driver = DriverManager.getDriver();
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

    public void scrollPageUp(){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, -250);");
    }

    public void waitForVisibilityByXpath(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitForClickableByXpath(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public void waitForSomethingByXpath(String xpath){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = driver.findElement(By.xpath(xpath));
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    public boolean retryingFindClickByXpath(String XPath) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(By.xpath(XPath)).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    public void assertTextByXpath(String XPath, String text){
        driver.navigate().refresh();
        waitForVisibilityByXpath(XPath);
        String aIssueTitle = driver.findElement(By.xpath(XPath)).getText();
        assertTrue(aIssueTitle.contains(text));
    }

    public void assertNotEqualsByXpath(String XPath, String text){
        driver.navigate().refresh();
        waitForVisibilityByXpath(XPath);
        String aIssueTitle = driver.findElement(By.xpath(XPath)).getText();
        assertFalse(aIssueTitle.contains(text));
    }

    public void assertEqualsByTitle(String title){
        Helpers helpers = new Helpers();
        //TODO
        helpers.sleep(2000);
        String currentTitle = driver.getTitle();
        assertEquals(currentTitle, title);
    }

    public void assertContainsByTitle(String title){
        String currentTitle = driver.getTitle();
        assertTrue(currentTitle.contains(title));
    }



}
