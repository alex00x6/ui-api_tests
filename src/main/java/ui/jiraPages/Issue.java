package ui.jiraPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;
import ui.utils.DriverManager;
import ui.utils.Helpers;

/**
 * Created by Storm on 27.10.2016.
 */
public class Issue extends Helpers{

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
        waitForClickableByXpath(xpath_issue_comment_button);
        driver.findElement(By.xpath(xpath_issue_comment_button)).click();

        waitForVisibilityByXpath(xpath_issue_comment_form);
        driver.findElement(By.xpath(xpath_issue_comment_form)).sendKeys(comment_text);

        waitForClickableByXpath(xpath_issue_comment_submit_button);
        driver.findElement(By.xpath(xpath_issue_comment_submit_button)).submit();
    }

    @Step("change type of issue bug/task/epic/story")
    public void changeType(String issueType){
        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        scrollPageUp();

        waitForClickableByXpath(xpath_issue_type_button);
        driver.findElement(By.xpath(xpath_issue_type_button)).click();

        waitForClickableByXpath(xpath_issue_type_form);
        driver.findElement(By.xpath(xpath_issue_type_form)).click();
        driver.findElement(By.xpath(xpath_issue_type_form)).clear();
        driver.findElement(By.xpath(xpath_issue_type_form)).sendKeys(issueType);
        driver.findElement(By.xpath(xpath_issue_type_form)).submit();
    }

    @Step("delete issue")
    public void deleteIssue(){
        waitForClickableByXpath(xpath_issue_more_button);
        driver.findElement(By.xpath(xpath_issue_more_button)).click();

        waitForClickableByXpath(xpath_issue_more_delete_button);
        driver.findElement(By.xpath(xpath_issue_more_delete_button)).click();

        waitForClickableByXpath(xpath_issue_delete_submit_button);
        driver.findElement(By.xpath(xpath_issue_delete_submit_button)).submit();
    }

    @Step("change reporter of issue")
    public void changeReporter(String reporter){
        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        //а поле со сменой репортера по странице не ездит(как и приорити и т.п.)
        scrollPageUp();

        waitForClickableByXpath(xpath_issue_reporter_button);
        driver.findElement(By.xpath(xpath_issue_reporter_button)).click();

        //waitForVisibilityByXpath(xpath_issue_reporter_field);
        waitForClickableByXpath(xpath_issue_reporter_field);
        driver.findElement(By.xpath(xpath_issue_reporter_field)).sendKeys(reporter);
        driver.findElement(By.xpath(xpath_issue_reporter_field)).submit();
    }


    @Step("change priority of issue")
    public void changePriority(String priority){
        //скроллим страницу вверх, т.к после создания коммента jira автоматически скроллит страницу к коменту
        scrollPageUp();

        waitForClickableByXpath(xpath_issue_priority_button);
        driver.findElement(By.xpath(xpath_issue_priority_button)).click();

        //waitForVisibilityByXpath(xpath_issue_priority_field);
        waitForClickableByXpath(xpath_issue_priority_field);
        driver.findElement(By.xpath(xpath_issue_priority_field)).sendKeys(priority);
        driver.findElement(By.xpath(xpath_issue_priority_field)).submit();
    }


    @Step("change summary of issue")
    public void changeSummary(String summary){
        waitForClickableByXpath(xpath_issue_summary_button);
        driver.findElement(By.xpath(xpath_issue_summary_button)).click();

        //waitForVisibilityByXpath(xpath_issue_summary_field);
        waitForClickableByXpath(xpath_issue_summary_field);
        driver.findElement(By.xpath(xpath_issue_summary_field)).sendKeys(summary);
        driver.findElement(By.xpath(xpath_issue_summary_field)).submit();
    }
}
