package Jira;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;
import ui.jiraPages.CreateIssuePopUp;
import ui.jiraPages.Dashboard;
import ui.jiraPages.Issue;
import ui.jiraPages.LoginPage;
import ui.utils.Helpers;
import ui.utils.TestListener;

import static org.testng.Assert.assertTrue;

/**
 * Created by Storm on 25.10.2016.
 */
@Listeners(TestListener.class)
public class Test_Ui {

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

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups = {"LoginCreate"})
    public void loginSuccessful() {

        Helpers helpers = new Helpers();
        LoginPage loginPage = new LoginPage();
        String newTitle = "System Dashboard - JIRA";
        String title = "Log in - JIRA";
        //открываем страницу логина. ну в целом логично
        loginPage.openPage();
        // выполняем проверку, попали ли мы на страницу с нужным тайтлом
        //helpers.makeScreenshot("name", "date");
        helpers.assertEqualsByTitle(title);
        //делаем логиномагию
        loginPage.completeLogin(login, password);
        //проверяем уже новый тайтл

        //TODO - адекватный вейт для FF
        //helpers.sleep(5000);

        helpers.assertEqualsByTitle(newTitle);
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups = {"LoginCreate"}, priority = 2)
    public void createIssueSuccessful(){
        Helpers helpers = new Helpers();
        Dashboard dashboard = new Dashboard();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        Issue issue = new Issue();
        //LoginPage loginPage = new LoginPage();

        //loginPage.openPage().completeLogin(login, password);
        //helpers.sleep(2000);

        //открываем дашборд
        dashboard.openPage();

        //находим кнопку Create и нажимаем
        dashboard.clickCreate();
        //создаем issue(вводим проект, самери, тип, жмем assignToMe, submit) и получаем ключ созданной issue, чтоб потом с ней работать
        String created_issue = createIssuePopUp.completeCreateIssue(project, summary, issueType);
        //System.out.println(created_issue);
        //проверяем что всё отработало, и мы забрали то, что надо
        Assert.assertNotNull(created_issue);
        assertTrue(created_issue.contains("QAAUT-"));
        issue.openPage(created_issue).deleteIssue();
    }


    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, priority = 2)
    public void changeTypeOfIssue(){
        Helpers helpers = new Helpers();
        Dashboard dashboard = new Dashboard();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        Issue issue = new Issue();

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);


        //открываем страницу нужной issue
        issue.openPage(created_issue);

        //меняем Type of Issue
        issue.changeType(issueTypeNew);

        helpers.assertTextByXpath(issue.xpath_issue_type_button, issueTypeNew);

        issue.openPage(created_issue).deleteIssue();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, priority = 2)
    public void changeReporter(){
        Helpers helpers = new Helpers();
        Dashboard dashboard = new Dashboard();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        Issue issue = new Issue();

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем репортЁра :)
        issue.changeReporter(reporter);

        helpers.assertNotEqualsByXpath(issue.xpath_issue_reporter_button, login);

        issue.openPage(created_issue).deleteIssue();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, priority = 2)
    public void changePriority(){
        Helpers helpers = new Helpers();
        Dashboard dashboard = new Dashboard();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        Issue issue = new Issue();

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем приоритет
        issue.changePriority(priority);
        //проверяем приоритетность
        helpers.assertTextByXpath(issue.xpath_issue_priority_button ,priority);

        issue.openPage(created_issue).deleteIssue();

    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, priority = 2)
    public void changeSummary(){
        Helpers helpers = new Helpers();
        Dashboard dashboard = new Dashboard();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        Issue issue = new Issue();

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //меняем summary
        issue.changeSummary(summary_new);

        //обновляем страницу, получаем текст, сверяем текст с тем, который должен быть
        helpers.assertTextByXpath(issue.xpath_issue_summary_button, summary_new);

        issue.openPage(created_issue).deleteIssue();
    }

    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    @Test(groups={"UpdateIssue"}, priority = 2)
    public void addCommentToIssue(){
        Helpers helpers = new Helpers();
        Dashboard dashboard = new Dashboard();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        Issue issue = new Issue();

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //добавляем коммент
        issue.addComment(comment_text);
        //проверяем комментность коммента
        helpers.assertTextByXpath("//*[@id=\"activitymodule\"]/div[2]/div[2]", comment_text);

        issue.openPage(created_issue).deleteIssue();
    }


    @Test( priority = 2)
    @TestCaseId("UI-1")
    @Features("Issue")
    @Stories({"SomeStoryForIssue"})
    public void deleteCreatedIssue(){
        Helpers helpers = new Helpers();
        Dashboard dashboard = new Dashboard();
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        Issue issue = new Issue();

        dashboard.openPage().clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project,summary,issueType);

        //открываем страницу нужной issue
        issue.openPage(created_issue);
        //открываем More, нажимаем удалить, подтверждаем
        issue.deleteIssue();
        issue.openPage(created_issue);
        helpers.assertEqualsByTitle("Issue Navigator - JIRA");
    }

    //TODO
    //@Test
    public void loginCreateIssueDeleteIssue(){
        CreateIssuePopUp createIssuePopUp = new CreateIssuePopUp();
        LoginPage loginPage = new LoginPage();
        Dashboard dashboard = new Dashboard();
        Issue issue = new Issue();
        loginPage.completeLogin(login, password);
        //Cookie cookie = driver.manage().getCookieNamed("JSESSIONID");
        dashboard.openPage();
        dashboard.clickCreate();
        String created_issue = createIssuePopUp.completeCreateIssue(project, summary, issueType);
        issue.openPage(created_issue);
        //helpers.assertContainsByTitle(created_issue);
        issue.deleteIssue();
        issue.openPage(created_issue);
        //helpers.assertEqualsByTitle("Issue Navigator - JIRA");
    }

}
