package Jira;

import api.inputData.GenerateJSONForJIRA;
import api.utils.RequestGroups;
import api.utils.RequestSender;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.TestCaseId;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class Test_Api {

    @BeforeTest(groups = {"Issue", "Search", "Comment"})
    public void beforeTest(){
        RequestGroups requestGroups = new RequestGroups();

        //RestAssured.baseURI = "https://forapitest.atlassian.net"; //JIRA Rostislav
        RestAssured.baseURI = "http://soft.it-hillel.com.ua:8080/"; //JIRA Hillel
        //RestAssured.baseURI = "https://katherinetestsapi.atlassian.net"; //JIRA кати (там не работает ничего))

        //проверяем, какой поток
        long id = Thread.currentThread().getId();
        System.out.println("BeforeTest. Thread id is: " + id);

        requestGroups.authenticate();
    }


    @TestCaseId("API-1")
    @Features("IssueApi")
    @Stories({"SomeStoryForIssueApi"})
    @Test(groups = {"Issue"})
    public void createIssueDeleteIssue(){
        RequestGroups requestGroups = new RequestGroups();
        GenerateJSONForJIRA generateJSONForJIRA = new GenerateJSONForJIRA();

        //проверяем, какой поток (не обязательно, но просто ради интереса)
        long id = Thread.currentThread().getId();
        System.out.println("createIssueDeleteIssue. Thread id is: " + id);

        //создаем issue
        RequestSender createIssue = requestGroups.createIssue(generateJSONForJIRA.createSampleIssue());

        //проверяем
        assertEquals(createIssue.response.statusCode(), 201);

        //берем id того, что мы создали
        String issueId = createIssue.extractResponseByPath("id");
        System.out.println(issueId);

        //удаляем то, что создали, и проверяем ответ от сервера
        RequestSender deleteIssue = requestGroups.deleteIssue(issueId);
        assertEquals(deleteIssue.response.statusCode(), 204);
        assertTrue(deleteIssue.response.statusCode()==204); //странная реализация, просто проверял, работает ли
    }

    @TestCaseId("API-1")
    @Features("IssueApi")
    @Stories({"SomeStoryForIssueApi"})
    @Test(groups = {"Issue"})
    public void createIssue(){
        RequestGroups requestGroups = new RequestGroups();
        GenerateJSONForJIRA generateJSONForJIRA = new GenerateJSONForJIRA();

        //проверяем, какой поток
        long id = Thread.currentThread().getId();
        System.out.println("createIssue. Thread id is: " + id);

        //создаем issue
        RequestSender response = requestGroups.createIssue(generateJSONForJIRA.createSampleIssue());

        //проверяем
        assertEquals(response.response.statusCode(), 201);

        //берем id того, что мы создали и удаляем
        String issueId = response.extractResponseByPath("id");
        requestGroups.deleteIssue(issueId);
    }

    @TestCaseId("API-1")
    @Features("IssueApi")
    @Stories({"SomeStoryForIssueApi"})
    @Test(groups = {"Issue"})
    public void deleteIssue(){
        RequestGroups requestGroups = new RequestGroups();
        GenerateJSONForJIRA generateJSONForJIRA = new GenerateJSONForJIRA();

        //проверяем, какой поток
        long id = Thread.currentThread().getId();
        System.out.println("deleteIssue. Thread id is: " + id);

        //создаем issue
        RequestSender response = requestGroups.createIssue(generateJSONForJIRA.createSampleIssue());
        String issueId = response.extractResponseByPath("id");

        //удаляем issue и проверяем статускод
        RequestSender delete = requestGroups.deleteIssue(issueId);
        assertEquals(delete.response.statusCode(), 204);
    }

    @TestCaseId("API-1")
    @Features("IssueApi")
    @Stories({"SomeStoryForIssueApi"})
    @Test(groups = {"Issue"})
    public void getIssue(){
        RequestGroups requestGroups = new RequestGroups();
        GenerateJSONForJIRA generateJSONForJIRA = new GenerateJSONForJIRA();

        //проверяем, какой поток
        long id = Thread.currentThread().getId();
        System.out.println("getIssue. Thread id is: " + id);

        RequestSender createIssue = requestGroups.createIssue(generateJSONForJIRA.createSampleIssue());
        String issueId = createIssue.extractResponseByPath("id");

        //тест и проверка
        RequestSender getIssue = requestGroups.getIssue(issueId);
        assertEquals(getIssue.response.statusCode(), 200);
        assertTrue(getIssue.response.contentType().contains(ContentType.JSON.toString()));

        requestGroups.deleteIssue(issueId);
    }

    @TestCaseId("API-1")
    @Features("SearchApi")
    @Stories({"SomeStoryForApi"})
    @Test(groups = {"Search"}, dependsOnGroups = {"Issue"}, alwaysRun = true)
    public void search(){
        RequestGroups requestGroups = new RequestGroups();
        GenerateJSONForJIRA generateJSONForJIRA = new GenerateJSONForJIRA();

        //проверяем, какой поток
        long id = Thread.currentThread().getId();
        System.out.println("Search. Thread id is: " + id);

        RequestSender response = requestGroups.search(generateJSONForJIRA.search());
        System.out.println(response.response.asString());
        assertEquals(response.response.statusCode(), 200);
    }

    @Test(groups = {"Search"})
    public void searchForIssue(){
        RequestGroups requestGroups = new RequestGroups();
        GenerateJSONForJIRA generateJSONForJIRA = new GenerateJSONForJIRA();

        //проверяем, какой поток
        long id = Thread.currentThread().getId();
        System.out.println("Search. Thread id is: " + id);

        //TODO - запилить создание issue, поиск именно её, а потом её удаление

    }

    @TestCaseId("API-1")
    @Features("CommentApi")
    @Stories({"SomeStoryForCommentApi"})
    @Test(groups = {"Comment"}, dependsOnMethods = {"createIssue", "deleteIssue"}, alwaysRun = true)
    public void addCommentToIssue(){
        RequestGroups requestGroups = new RequestGroups();
        GenerateJSONForJIRA generateJSONForJIRA = new GenerateJSONForJIRA();

        //проверяем, какой поток
        long id = Thread.currentThread().getId();
        System.out.println("addCommentToIssue. Thread id is: " + id);

        //создаем issue, получаем его id
        RequestSender createIssue = requestGroups.createIssue(generateJSONForJIRA.createSampleIssue());
        String issueId = createIssue.extractResponseByPath("id");

        //добавляем комент
        RequestSender addComment = requestGroups.addCommentToIssue(generateJSONForJIRA.addCommentToIssue(), issueId);

        //проверяем, всё ли на месте
        assertEquals(addComment.response.statusCode(), 201);
        assertTrue(addComment.response.contentType().contains(ContentType.JSON.toString()));
        //ну это прям уж совсем чтоб 100%
        System.out.println(addComment.extractResponseByPath("id"));
        System.out.println(addComment.extractAllResponseAsString());

        //удаляем созданную issue вместе с коментом
        requestGroups.deleteIssue(issueId);
    }

}
