package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import ui.utils.Helpers;

/**
 * Created by Storm on 01.11.2016.
 */
public class CreateIssuePopUp {
    private WebDriver driver;
    Helpers helpers = new Helpers();

    public CreateIssuePopUp(WebDriver driver){
        this.driver = driver;
    }

    public void enterProject(String project){
        String projectFieldXpath = "//*[@id=\"project-field\"]";

        helpers.waitForVisibilityByXpath(driver, projectFieldXpath);
        helpers.waitForClickableByXpath(driver, projectFieldXpath);

        driver.findElement(By.xpath(projectFieldXpath)).click();
        driver.findElement(By.xpath(projectFieldXpath)).clear();
        driver.findElement(By.xpath(projectFieldXpath)).sendKeys(project, Keys.ENTER);
    }

    public void enterType(String issueType){
        String issueTypeFieldXpath = "//*[@id=\"issuetype-field\"]";

        helpers.waitForVisibilityByXpath(driver, issueTypeFieldXpath);
        helpers.waitForClickableByXpath(driver, issueTypeFieldXpath);

        driver.findElement(By.xpath(issueTypeFieldXpath)).click();
        driver.findElement(By.xpath(issueTypeFieldXpath)).clear();
        driver.findElement(By.xpath(issueTypeFieldXpath)).sendKeys(issueType, Keys.ENTER);
    }

    public void enterSummary(String summary){
        String summaryXpath = "//*[@id=\"summary\"]";

        helpers.waitForVisibilityByXpath(driver, summaryXpath);
        helpers.waitForClickableByXpath(driver, summaryXpath);

        driver.findElement(By.xpath(summaryXpath)).click();
        driver.findElement(By.xpath(summaryXpath)).clear();
        driver.findElement(By.xpath(summaryXpath)).sendKeys(summary, Keys.TAB);
    }

    public void clickAssignToMe(){
        driver.findElement(By.xpath("//a[@id='assign-to-me-trigger']")).click();
    }

    public void clickSubmit(){
        driver.findElement(By.xpath("//*[@id=\"create-issue-submit\"]")).click();
    }

    public String getKeyOfCreatedIssue(){
        String key =  driver.findElement(By.xpath("//*[@id=\"aui-flag-container\"]/div/div/a"))
                .getAttribute("data-issue-key");
        return key;
    }

}
