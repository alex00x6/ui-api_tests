package api.utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import api.inputData.GenerateJSONForJIRA;

import static com.jayway.restassured.RestAssured.given;

public class RequestSender {
    private static String JSESSIONID = null;
    private static String STUDIO_TOKEN = null;
    private final static ContentType CONTENT_TYPE = ContentType.JSON;
    private RequestSpecification requestSpecification = null;
    public Response response = null;

    RequestSender(){
    }

    void authenticate(){
        GenerateJSONForJIRA generateJSON = new GenerateJSONForJIRA();
        String credentials = generateJSON.login();

        createRequest(credentials)
                .post(api.apis.ApiUrls.LOGIN.getUri());


        if (RestAssured.baseURI.contains("https://")){
            System.out.println("Connected via HTTPS");

            JSESSIONID = response.then().extract().path("session.value");
            STUDIO_TOKEN = response.then().extract().cookie("studio.crowd.tokenkey");
            System.out.println(JSESSIONID);
            System.out.println(STUDIO_TOKEN);
        }
        else{
            System.out.println("Connected via HTTP");

            JSESSIONID = response.then().extract().path("session.value");
            System.out.println(JSESSIONID);
        }

    }

    RequestSender createRequest(String body){
        if (RestAssured.baseURI.contains("https://")){
            this.createRequestSpecification()
                    .addHeader("Content-Type", CONTENT_TYPE.toString())
                    .addHeader("Cookie", "JSESSIONID="+RequestSender.JSESSIONID)
                    .addHeader("Cookie", "studio.crowd.tokenkey="+RequestSender.STUDIO_TOKEN)
                    .addBody(body);
            return this;
        }
        else{
            this.createRequestSpecification()
                    .addHeader("Content-Type", CONTENT_TYPE.toString())
                    .addHeader("Cookie", "JSESSIONID=" + RequestSender.JSESSIONID)
                    .addBody(body);
            return this;
        }
    }


    RequestSender createEmptyRequest(){
        if(RestAssured.baseURI.contains("https://")){
            this.createRequestSpecification()
                    .addHeader("Content-Type", CONTENT_TYPE.toString())
                    .addHeader("Cookie", "JSESSIONID="+RequestSender.JSESSIONID)
                    .addHeader("Cookie", "studio.crowd.tokenkey="+RequestSender.STUDIO_TOKEN);
            return this;
        }

        else{
            this.createRequestSpecification()
                    .addHeader("Content-Type", CONTENT_TYPE.toString())
                    .addHeader("Cookie", "JSESSIONID=" + RequestSender.JSESSIONID);
            return this;
        }
    }


     private RequestSender createRequestSpecification() {
        requestSpecification = given().
                when();
        return this;
    }

    // этот метод сможет добавлять столько угодно хедеров
    private RequestSender addHeader(String headerName, String headerValue) {
        requestSpecification.header(headerName, headerValue);
        return this;
    }

    private RequestSender addBody(String body) {
        requestSpecification.body(body);
        return this;
    }

    RequestSender post(String uri) {
        response = requestSpecification.post(uri);
        return this;
    }

    RequestSender delete(String uri){
        response = requestSpecification.delete(uri);
        return this;
    }

    RequestSender get(String uri){
        response = requestSpecification.get(uri);
        return this;
    }

    RequestSender put(String uri) {
        response = requestSpecification.put(uri);
        return this;
    }

    public String extractResponseByPath(String path){
        return response.then().extract().path(path);
    }

    public String extractAllResponseAsString(){
        return response.then().extract().asString();
    }


}
