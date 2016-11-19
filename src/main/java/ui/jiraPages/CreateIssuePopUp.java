package ui.jiraPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;
import ui.utils.DriverManager;
import ui.utils.Helpers;

/**
 * Created by Storm on 01.11.2016.
 */
public class CreateIssuePopUp extends Helpers{
    private WebDriver driver;


    private final String xpath_createIssue_project_field = "//*[@id=\"project-field\"]";
    private final String xpath_createIssue_type_field = "//*[@id=\"issuetype-field\"]";
    private final String xpath_createIssue_summary_field = "//*[@id=\"summary\"]";
    private final String xpath_createIssue_assignToMe_button = "//a[@id='assign-to-me-trigger']";
    private final String xpath_createIssue_submit_button = "//*[@id=\"create-issue-submit\"]";

    private final String xpath_createIssue_created_popup = "//*[@id=\"aui-flag-container\"]/div/div/a";
    private final String xpath_createIssue_created_popup_attribute = "data-issue-key";

    public CreateIssuePopUp(){
        this.driver = DriverManager.getDriver();
    }


    @Step("enter project name")
    public void enterProject(String project){
        waitForClickableByXpath(xpath_createIssue_project_field);
        driver.findElement(By.xpath(xpath_createIssue_project_field)).click();
        driver.findElement(By.xpath(xpath_createIssue_project_field)).clear();
        driver.findElement(By.xpath(xpath_createIssue_project_field)).sendKeys(project, Keys.ENTER);
    }


    @Step("enter type of issue")
    public void enterType(String issueType){
        waitForClickableByXpath(xpath_createIssue_type_field);
        driver.findElement(By.xpath(xpath_createIssue_type_field)).click();
        driver.findElement(By.xpath(xpath_createIssue_type_field)).clear();
        driver.findElement(By.xpath(xpath_createIssue_type_field)).sendKeys(issueType, Keys.ENTER);
    }


    @Step("enter issue summary")
    public void enterSummary(String summary){
        waitForClickableByXpath(xpath_createIssue_summary_field);
        driver.findElement(By.xpath(xpath_createIssue_summary_field)).click();
        driver.findElement(By.xpath(xpath_createIssue_summary_field)).clear();
        driver.findElement(By.xpath(xpath_createIssue_summary_field)).sendKeys(summary, Keys.TAB);
    }


    @Step("click assign to me button")
    public void clickAssignToMe(){
        waitForClickableByXpath(xpath_createIssue_assignToMe_button);
        //пытаюсь нажать на эту херню и не словить StaleElementReferenceException
        //если не поможет - надо попробовать нажимать на нее по css локатору
        retryingFindClickByXpath(xpath_createIssue_assignToMe_button);
    }

    @Step("click submit button")
    public void clickSubmit(){
        waitForClickableByXpath(xpath_createIssue_submit_button);
        driver.findElement(By.xpath(xpath_createIssue_submit_button)).click();
    }

    @Step("get key of recently created issue")
    public String getKeyOfCreatedIssue(){
        waitForVisibilityByXpath(xpath_createIssue_created_popup);
        String key =  driver.findElement(By.xpath(xpath_createIssue_created_popup))
                .getAttribute(xpath_createIssue_created_popup_attribute);
        return key;
    }

    public String completeCreateIssue(String project, String summary, String typeOfIssue){
        enterProject(project);
        enterSummary(summary);
        enterType(typeOfIssue);
        clickAssignToMe();
        clickSubmit();
        String key = getKeyOfCreatedIssue();
        return key;
    }

}
