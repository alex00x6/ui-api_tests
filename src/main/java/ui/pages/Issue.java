package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;
import ui.utils.DriverManager;
import ui.utils.Helpers;

/**
 * Created by Storm on 27.10.2016.
 */
public class Issue {

    private WebDriver driver;
    private final String url_issue = "http://soft.it-hillel.com.ua:8080/browse/";

    private final String xpath_issue_comment_button = "//*[@id=\"comment-issue\"]";
    private final String xpath_issue_comment_form = "//*[@id=\"comment\"]";
    private final String xpath_issue_comment_submit_button = "//*[@id=\"issue-comment-add-submit\"]";

    public final String xpath_issue_type_button = "//*[@id=\"type-val\"]";
    private final String xpath_issue_type_form = "//*[@id=\"issuetype-field\"]";

    private final String xpath_issue_more_button = "//*[@id=\"opsbar-operations_more\"]";
    private final String xpath_issue_more_delete_button = "//*[@id=\"delete-issue\"]";
    private final String xpath_issue_delete_submit_button = "//*[@id=\"delete-issue-submit\"]";

    public final String xpath_issue_reporter_button = ".//*[@id='reporter-val']";
    private final String xpath_issue_reporter_field = ".//*[@id='reporter-field']";

    public final String xpath_issue_priority_button = ".//*[@id='priority-val']";
    private final String xpath_issue_priority_field = "//*[@id=\"priority-field\"]";

    public final String xpath_issue_summary_button = ".//*[@id='summary-val']";
    private final String xpath_issue_summary_field = ".//*[@id='summary']";



    public Issue(){
        this.driver = DriverManager.getDriver();
    }

    @Step("open issue page")
    public Issue openPage(String key){
        this.driver.get(url_issue+key);
        return this;
    }

    @Step("add comment to issue")
    public void addComment(String comment_text){
        driver.findElement(By.xpath(xpath_issue_comment_button)).click();
        driver.findElement(By.xpath(xpath_issue_comment_form)).sendKeys(comment_text);
        driver.findElement(By.xpath(xpath_issue_comment_submit_button)).submit();
    }

    @Step("change type of issue bug/task/epic/story")
    public void changeType(String issueType){
        Helpers helpers = new Helpers();

        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        helpers.scrollPageUp();

        WebElement type = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath_issue_type_button)));
        type.click();

        driver.findElement(By.xpath(xpath_issue_type_form)).click();
        driver.findElement(By.xpath(xpath_issue_type_form)).clear();
        driver.findElement(By.xpath(xpath_issue_type_form)).sendKeys(issueType, Keys.ALT+"S");
    }

    @Step("delete issue")
    public void deleteIssue(){
        driver.findElement(By.xpath(xpath_issue_more_button)).click();
        driver.findElement(By.xpath(xpath_issue_more_delete_button)).click();
        driver.findElement(By.xpath(xpath_issue_delete_submit_button)).submit();
    }

    @Step("change reporter of issue")
    public void changeReporter(String reporter){
        Helpers helpers = new Helpers();
        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        //а поле со сменой репортера по странице не ездит(как и приорити и т.п.)
        helpers.scrollPageUp();

        driver.findElement(By.xpath(xpath_issue_reporter_button)).click();
        //helpers.waitForVisibilityByXpath(xpath_issue_reporter_field);
        driver.findElement(By.xpath(xpath_issue_reporter_field)).sendKeys(reporter, Keys.ENTER);
    }


    @Step("change priority of issue")
    public void changePriority(String priority){
        Helpers helpers = new Helpers();
        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        helpers.scrollPageUp();

        helpers.waitForVisibilityByXpath(xpath_issue_priority_button);
        helpers.waitForClickableByXpath(xpath_issue_priority_button);
        driver.findElement(By.xpath(xpath_issue_priority_button)).click();
        driver.findElement(By.xpath(xpath_issue_priority_field)).sendKeys(priority, Keys.ALT+"S");
    }


    @Step("change summary of issue")
    public void changeSummary(String summary){
        Helpers helpers = new Helpers();
        helpers.waitForClickableByXpath(xpath_issue_summary_button);
        driver.findElement(By.xpath(xpath_issue_summary_button)).click();
        driver.findElement(By.xpath(xpath_issue_summary_field)).sendKeys(summary, Keys.ALT+"S");
    }
}
