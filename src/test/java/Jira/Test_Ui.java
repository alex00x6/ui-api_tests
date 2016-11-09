package Jira;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ui.pages.CreateIssuePopUp;
import ui.pages.Dashboard;
import ui.pages.Issue;
import ui.pages.LoginPage;
import ui.utils.Helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

/**
 * Created by Storm on 25.10.2016.
 */
public class Test_Ui {

    Helpers helpers = new Helpers();
    private static Cookie cookie;

    String summary = "Some summary for createIssue via WebDriver";
    String issueType = "Bug";
    String issueTypeNew = "Epic";
    String login = "alex00x6"; //"a.a.piluck2"
    String password = "666999"; // "1234"
    String created_issue = "";
    String comment_text = "Some comment for addCommentToIssue via WebDriver+Grid";
    String currentDate = "";
    String reporter = "a.a.piluck";
    String priority = "High";
    String summary_new = "Updated summary, blah blah blah";
    String project = "QAAUT";

    Boolean useGrid;
    String browser;


    WebDriver driver;


    @Parameters({"pUseGrid", "pBrowser"})
    @BeforeTest(groups = {"UpdateIssue"})
    public void beforeTest(Boolean pUseGrid, String pBrowser){
        currentDate = helpers.getTime();
        //useGrid = true;
        //browser = "firefox";
        useGrid = pUseGrid;
        browser = pBrowser;
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups = {"LoginCreate"})
    public void loginSuccessful() {
        if (useGrid){
            switch(browser){
                case("chrome"):
                    driver = configForGridChrome();
                    break;
                case("firefox"):
                    driver = configForGridFirefox();
                    break;
                }
            }
        else{
            switch(browser) {
                case ("chrome"):
                    driver = configForChrome();
                    break;
                case ("firefox"):
                    //driver = configForFirefox();
                    break;
                }
            }

        LoginPage loginPage = new LoginPage(driver);
        String newTitle = "System Dashboard - JIRA";
        String title = "Log in - JIRA";
        //открываем страницу логина. ну в целом логично
        loginPage.openPage();
        // выполняем проверку, попали ли мы на страницу с нужным тайтлом
        helpers.assertEqualsByTitle(driver, title);
        //делаем логиномагию
        loginPage.completeLogin(login, password);
        //проверяем уже новый тайтл
        helpers.assertEqualsByTitle(driver, newTitle);
        //забираем печенье после логина и делаем его доступным для всех
        cookie = driver.manage().getCookieNamed("JSESSIONID");
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups = {"LoginCreate"}, dependsOnMethods = {"loginSuccessful"})
    public void createIssueSuccessful(){
        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        Issue issue = new Issue(driver);
        //открываем дашборд
        dashboard.openPage();
        //находим кнопку Create и нажимаем
        dashboard.clickCreate();
        //создаем issue(вводим проект, самери, тип, жмем assignToMe, submit) и получаем ключ созданной issue, чтоб потом с ней работать
        String created_issue = createIssuePopUp.completeCreateIssue(project, summary, issueType);
        System.out.println(created_issue);
        //проверяем что всё отработало, и мы забрали то, что надо
        Assert.assertNotNull(created_issue);
        assertTrue(created_issue.contains("QAAUT-"));
        issue.openPage(created_issue).deleteIssue();
    }


    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changeTypeOfIssue(){
        //WebDriver driver;
        //if (!useGrid){
        //    driver = configForCookiedChrome();}
        //else{
        //    driver = configForCookiedGrid();}

        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        Issue issue = new Issue(driver);

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);


        //открываем страницу нужной issue
        issue.openPage(created_issue);

        //меняем Type of Issue
        issue.changeType(issueTypeNew);

        helpers.assertTextByXpath(driver, issue.xpath_issue_type_button, issueTypeNew);

        issue.openPage(created_issue).deleteIssue();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changeReporter(){
        //WebDriver driver;
        //if (!useGrid){
        //    driver = configForCookiedChrome();}
        //else{
        //    driver = configForCookiedGrid();}

        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        Issue issue = new Issue(driver);

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем репортЁра :)
        issue.changeReporter(reporter);

        helpers.assertNotEqualsByXpath(driver, issue.xpath_issue_reporter_button, login);

        issue.openPage(created_issue).deleteIssue();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changePriority(){
        //WebDriver driver;
       // if (!useGrid){
        //    driver = configForCookiedChrome();}
       // else{
        //    driver = configForCookiedGrid();}

        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        Issue issue = new Issue(driver);

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем приоритет
        issue.changePriority(priority);
        //проверяем приоритетность
        helpers.assertTextByXpath(driver, issue.xpath_issue_priority_button ,priority);

        issue.openPage(created_issue).deleteIssue();

    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changeSummary(){
        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        Issue issue = new Issue(driver);

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем summary
        issue.changeSummary(summary_new);

        //обновляем страницу, получаем текст, сверяем текст с тем, который должен быть
        helpers.assertTextByXpath(driver, issue.xpath_issue_summary_button, summary_new);

        issue.openPage(created_issue).deleteIssue();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void addCommentToIssue(){
        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        Issue issue = new Issue(driver);

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //добавляем коммент
        issue.addComment(comment_text);
        //проверяем комментность коммента
        helpers.assertTextByXpath(driver, "//*[@id=\"activitymodule\"]/div[2]/div[2]", comment_text);

        issue.openPage(created_issue).deleteIssue();
    }


    @Test(dependsOnGroups = {"UpdateIssue"}, alwaysRun = true)
    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    public void deleteCreatedIssue(){
        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        Issue issue = new Issue(driver);

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //открываем More, нажимаем удалить, подтверждаем
        issue.deleteIssue();
        issue.openPage(created_issue);
        helpers.assertEqualsByTitle(driver, "Issue Navigator - JIRA");
    }

    //TODO
    //@Test
    public void loginCreateIssueDeleteIssue(){
       // if (!useGrid){
       //     driver = configForChrome();}
       // else{
       //     driver = configForGridChrome();}

        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        LoginPage loginPage = new LoginPage(driver);
        Dashboard dashboard = new Dashboard(driver);
        Issue issue = new Issue(driver);
        loginPage.completeLogin(login, password);
        Cookie cookie = driver.manage().getCookieNamed("JSESSIONID");
        dashboard.openPage();
        dashboard.clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project, summary, issueType);
        issue.openPage(created_issue);
        helpers.assertContainsByTitle(driver, created_issue);
        issue.deleteIssue();
        issue.openPage(created_issue);
        helpers.assertEqualsByTitle(driver, "Issue Navigator - JIRA");
    }

    @AfterTest
    public void afterTest(){
        //удаляем чего мы там насоздавали(вынес сюда вызов метода, ибо на мой взгляд пока что так логичнее)
        //deleteCreatedIssue();
        driver.quit();
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        try{
            if(result.getStatus() == ITestResult.SUCCESS){
                //Do something here
            }
            else if(result.getStatus() == ITestResult.FAILURE){
                //Do something here
                helpers.makeScreenshot("_FAILED_"+result.getName(), driver, currentDate);
            }
            else if(result.getStatus() == ITestResult.SKIP ){
                //Do something here
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public WebDriver configForGridChrome(){
        URL hostURL = null;
        try {
            hostURL = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.LINUX);

        WebDriver driver = new RemoteWebDriver(hostURL, capability);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // разворачивает окно браузера
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver configForGridFirefox(){
        URL hostURL = null;
        try {
            hostURL = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        capability.setBrowserName("firefox");
        capability.setPlatform(Platform.LINUX);

        WebDriver driver = new RemoteWebDriver(hostURL, capability);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // разворачивает окно браузера
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver configForChrome(){
        WebDriver driver = new ChromeDriver();
        //currentDate = helpers.getTime();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // разворачивает окно браузера
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver configForCookiedGrid(){
        URL hostURL = null;
        try {
            hostURL = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.LINUX);

        WebDriver driver = new RemoteWebDriver(hostURL, capability);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //заходим на какой-то урл, ибо нельзя положить куки в браузер если ты никуда не заходил (втф чтозабред)
        driver.get("http://soft.it-hillel.com.ua:8080/secure/Dashboard.jspa");
        //пихает печенье в окно браузера
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(cookie);
        // разворачивает окно браузера
        driver.manage().window().maximize();
        return driver;
    }


    public WebDriver configForCookiedChrome(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //заходим на какой-то урл, ибо нельзя положить куки в браузер если ты никуда не заходил (втф чтозабред)
        driver.get("http://soft.it-hillel.com.ua:8080/secure/Dashboard.jspa");
        //пихает печенье в окно браузера
        driver.manage().deleteAllCookies();
        driver.manage().addCookie(cookie);

        // разворачивает окно браузера
        driver.manage().window().maximize();
        return driver;
    }

}
