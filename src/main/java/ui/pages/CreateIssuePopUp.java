package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;
import ui.utils.Helpers;

/**
 * Created by Storm on 01.11.2016.
 */
public class CreateIssuePopUp {
    private WebDriver driver;
    Helpers helpers = new Helpers();

    private final String xpath_createIssue_project_field = "//*[@id=\"project-field\"]";
    private final String xpath_createIssue_type_field = "//*[@id=\"issuetype-field\"]";
    private final String xpath_createIssue_summary_field = "//*[@id=\"summary\"]";
    private final String xpath_createIssue_assignToMe_button = "//a[@id='assign-to-me-trigger']";
    private final String xpath_createIssue_submit_button = "//*[@id=\"create-issue-submit\"]";

    private final String xpath_createIssue_created_popup = "//*[@id=\"aui-flag-container\"]/div/div/a";
    private final String xpath_createIssue_created_popup_attribute = "data-issue-key";

    public CreateIssuePopUp(WebDriver driver){
        this.driver = driver;
    }


    @Step("enter project name")
    public void enterProject(String project){
        helpers.waitForVisibilityByXpath(driver, xpath_createIssue_project_field);
        helpers.waitForClickableByXpath(driver, xpath_createIssue_project_field);

        driver.findElement(By.xpath(xpath_createIssue_project_field)).click();
        driver.findElement(By.xpath(xpath_createIssue_project_field)).clear();
        driver.findElement(By.xpath(xpath_createIssue_project_field)).sendKeys(project, Keys.ENTER);
    }


    @Step("enter type of issue")
    public void enterType(String issueType){
        helpers.waitForVisibilityByXpath(driver, xpath_createIssue_type_field);
        helpers.waitForClickableByXpath(driver, xpath_createIssue_type_field);

        driver.findElement(By.xpath(xpath_createIssue_type_field)).click();
        driver.findElement(By.xpath(xpath_createIssue_type_field)).clear();
        driver.findElement(By.xpath(xpath_createIssue_type_field)).sendKeys(issueType, Keys.ENTER);
    }


    @Step("enter issue summary")
    public void enterSummary(String summary){
        helpers.waitForVisibilityByXpath(driver, xpath_createIssue_summary_field);
        helpers.waitForClickableByXpath(driver, xpath_createIssue_summary_field);

        driver.findElement(By.xpath(xpath_createIssue_summary_field)).click();
        driver.findElement(By.xpath(xpath_createIssue_summary_field)).clear();
        driver.findElement(By.xpath(xpath_createIssue_summary_field)).sendKeys(summary, Keys.TAB);
    }


    @Step("click assign to me button")
    public void clickAssignToMe(){
        helpers.waitForClickableByXpath(driver, xpath_createIssue_assignToMe_button);
        driver.findElement(By.xpath(xpath_createIssue_assignToMe_button)).click();
    }

    @Step("click submit button")
    public void clickSubmit(){
        driver.findElement(By.xpath(xpath_createIssue_submit_button)).click();
    }

    @Step("get key of recently created issue")
    public String getKeyOfCreatedIssue(){
        String key =  driver.findElement(By.xpath(xpath_createIssue_created_popup))
                .getAttribute(xpath_createIssue_created_popup_attribute);
        return key;
    }

    public String fullCreateIssue(String project, String summary, String type){
        enterProject(project);
        enterSummary(summary);
        enterType(type);
        clickAssignToMe();
        clickSubmit();
        String key = getKeyOfCreatedIssue();
        return key;
    }

}
