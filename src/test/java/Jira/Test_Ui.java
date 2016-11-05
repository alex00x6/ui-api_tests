package Jira;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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



    @BeforeTest(groups = {"UpdateIssue"})
    public void beforeTest(){
        currentDate = helpers.getTime();
        useGrid =false;
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups = {"LoginCreate"})
    public void loginSuccessful() {
        WebDriver driver;
        if (!useGrid){
            driver = configForChrome();}
        else{
            driver = configForGrid();}

        LoginPage loginPage = new LoginPage(driver);
        String newTitle = "System Dashboard - JIRA";
        String title = "Log in - JIRA";
        //открываем страницу логина. ну в целом логично
        loginPage.openPage();
        // выполняем проверку, попали ли мы на страницу с нужным тайтлом
        helpers.assertByTitle(driver, title);
        //делаем логиномагию
        loginPage.enterLogin(login);
        loginPage.enterPassword(password);
        loginPage.clickSubmit();
        //проверяем уже новый тайтл
        helpers.assertByTitle(driver, newTitle);
        //забираем печенье после логина и делаем его доступным для всех
        cookie = driver.manage().getCookieNamed("JSESSIONID");
        //делаемскриншот
        helpers.makeScreenshot("loginSuccessful", driver, currentDate);
        driver.quit();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups = {"LoginCreate"}, dependsOnMethods = {"loginSuccessful"})
    public void createIssueSuccessful(){
        WebDriver driver;
        if (!useGrid){
            driver = configForCookiedChrome();}
        else{
            driver = configForCookiedGrid();}

        Dashboard dashboard = new Dashboard(driver);
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        //открываем дашборд
        dashboard.openPage();
        //находим кнопку Create и нажимаем
        dashboard.clickCreate();
        //делаем так, чтоб issue точно создалась в проекте QAAUT
        createIssuePopUp.enterProject(project);
        //корявым способом меняем со Story на Bug
        createIssuePopUp.enterType(issueType);
        //находим поле самери и пишем туда что-то
        createIssuePopUp.enterSummary(summary);
        //нажимаем на элемент assign to me
        createIssuePopUp.clickAssignToMe();
        //нажимаем кнопку submit
        createIssuePopUp.clickSubmit();
        //получаем ключ созданной issue, чтоб потом с ней работать
        created_issue = createIssuePopUp.getKeyOfCreatedIssue();
        System.out.println(created_issue);
        //проверяем что всё отработало, и мы забрали то, что надо
        Assert.assertNotNull(created_issue);
        assertTrue(created_issue.contains("QAAUT-"));
        //делаемскриншот вне зависимости от результата теста...
        helpers.makeScreenshot("createIssueSuccessful", driver, currentDate);
        driver.quit();
    }


    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changeTypeOfIssue(){
        WebDriver driver;
        if (!useGrid){
            driver = configForCookiedChrome();}
        else{
            driver = configForCookiedGrid();}


        Issue issue = new Issue(driver);

        //открываем страницу нужной issue
        issue.openPage(created_issue);

        //меняем Type of Issue
        issue.changeType(issueTypeNew);

        helpers.assertTextByXpath(driver, "//*[@id=\"type-val\"]", issueTypeNew);

        //делаем скриншотец
        helpers.makeScreenshot("changeTypeOfIssue", driver, currentDate);
        driver.quit();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changeReporter(){
        WebDriver driver;
        if (!useGrid){
            driver = configForCookiedChrome();}
        else{
            driver = configForCookiedGrid();}

        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем репортЁра :)
        issue.changeReporter(reporter);

        //helpers.assertTextByXpath(driver, ".//*[@id='reporter-val']", issueTypeNew);

        //делаем скриншотец
        helpers.makeScreenshot("changeReporter", driver, currentDate);
        driver.quit();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changePriority(){
        WebDriver driver;
        if (!useGrid){
            driver = configForCookiedChrome();}
        else{
            driver = configForCookiedGrid();}

        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем приоритет
        issue.changePriority(priority);
        //проверяем приоритетность
        helpers.assertTextByXpath(driver, "//*[@id=\"priority-val\"]",priority);
        //делаем скриншот
        helpers.makeScreenshot("changePriority", driver, currentDate);
        driver.quit();

    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void changeSummary(){
        WebDriver driver;
        if (!useGrid){
            driver = configForCookiedChrome();}
        else{
            driver = configForCookiedGrid();}

        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем summary
        issue.changeSummary(summary_new);

        //обновляем страницу, получаем текст, сверяем текст с тем, который должен быть
        helpers.assertTextByXpath(driver, "//*[@id=\"summary-val\"]", summary_new);

        //делаем скриншотец
        helpers.makeScreenshot("changeSummary", driver, currentDate);
        driver.quit();

    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, dependsOnGroups = {"LoginCreate"})
    public void addCommentToIssue(){
        WebDriver driver;
        if (!useGrid){
            driver = configForCookiedChrome();}
        else{
            driver = configForCookiedGrid();}

        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //добавляем коммент
        issue.addComment(comment_text);
        //проверяем комментность коммента
        helpers.assertTextByXpath(driver, "//*[@id=\"activitymodule\"]/div[2]/div[2]", comment_text);
        //делаем скриншотец
        helpers.makeScreenshot("addCommentToIssue", driver, currentDate);
        driver.quit();

    }


    //@Test
    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    public void deleteCreatedIssue(){
        WebDriver driver;
        if (!useGrid){
            driver = configForCookiedChrome();}
        else{
            driver = configForCookiedGrid();}

        Issue issue = new Issue(driver);
        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //открываем More, нажимаем удалить, подтверждаем
        issue.deleteIssue();
        //делаем скриншотец
        helpers.makeScreenshot("deleteCreatedIssue", driver, currentDate);
        driver.quit();
    }

    //TODO
    //@Test
    public void loginCreateIssueDeleteIssue(){
        WebDriver driver = configForChrome();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp(driver);
        LoginPage loginPage = new LoginPage(driver);
        Dashboard dashboard = new Dashboard(driver);
        loginPage.completeLogin(login, password);
        cookie = driver.manage().getCookieNamed("JSESSIONID");
        dashboard.openPage();
        dashboard.clickCreate();
        created_issue = createIssuePopUp.fullCreateIssue(project, summary, issueType);

    }

    @AfterTest
    public void afterTest(){
        //удаляем чего мы там насоздавали(вынес сюда вызов метода, ибо на мой взгляд пока что так логичнее)
        deleteCreatedIssue();
    }


    public WebDriver configForGrid(){
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

    public WebDriver configForChrome(){
        WebDriver driver = new ChromeDriver();
        //currentDate = helpers.getTime();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
