package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.utils.Helpers;

/**
 * Created by Storm on 27.10.2016.
 */
public class Issue {

    private WebDriver driver;
    Helpers helpers = new Helpers();

    public Issue(WebDriver driver){
        this.driver = driver;
    }

    public void openPage(String key){
        driver.get("http://soft.it-hillel.com.ua:8080/browse/"+key);
    }

    public void addComment(String comment_text){
        driver.findElement(By.xpath("//*[@id=\"comment-issue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"comment\"]")).sendKeys(comment_text);
        driver.findElement(By.xpath("//*[@id=\"issue-comment-add-submit\"]")).submit();
    }

    public void changeType(String issueType){

        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        helpers.scrollPageUp(driver);

        WebElement type = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"type-val\"]")));
        type.click();

        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"issuetype-field\"]")).sendKeys(issueType, Keys.ALT+"S");
    }

    public void deleteIssue(){
        driver.findElement(By.xpath("//*[@id=\"opsbar-operations_more\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"delete-issue-submit\"]")).submit();
    }

    public void changeReporter(String reporter){
        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        //а поле со сменой репортера по странице не ездит(как и приорити и т.п.)
        helpers.scrollPageUp(driver);

        driver.findElement(By.xpath(".//*[@id='reporter-val']")).click();
        driver.findElement(By.xpath(".//*[@id='reporter-field']")).sendKeys(reporter, Keys.ENTER);
    }

    public void changePriority(String priority){
        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        helpers.scrollPageUp(driver);

        driver.findElement(By.xpath(".//*[@id='priority-val']")).click();
        driver.findElement(By.xpath("//*[@id=\"priority-field\"]")).sendKeys(priority, Keys.ALT+"S");
    }

    public void changeSummary(String summary){
        driver.findElement(By.xpath(".//*[@id='summary-val']")).click();
        driver.findElement(By.xpath(".//*[@id='summary']")).sendKeys(summary, Keys.ALT+"S");
    }
}
