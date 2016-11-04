package api.utils;

import api.apis.ApiUrls;

public class RequestGroups {


    public RequestSender createIssue(String body){
        RequestSender requestSender = new RequestSender();
        requestSender.createRequest(body).post(ApiUrls.ISSUE.getUri());
        return requestSender;
    }

    public RequestSender deleteIssue(String issue){
        RequestSender requestSender = new RequestSender();
        requestSender.createEmptyRequest().delete(ApiUrls.ISSUE.getUri(issue));
        return requestSender;
    }

    public RequestSender getIssue(String issue){
        RequestSender requestSender = new RequestSender();
        requestSender.createEmptyRequest().get(ApiUrls.ISSUE.getUri(issue));
        return requestSender;
    }

    public RequestSender search(String body){
        RequestSender requestSender = new RequestSender();
        requestSender.createRequest(body).post(ApiUrls.SEARCH.getUri());
        return requestSender;
    }

    public RequestSender addCommentToIssue(String body, String issue){
        RequestSender requestSender = new RequestSender();
        requestSender.createRequest(body).post(ApiUrls.ISSUE.getUri(issue+"/comment"));
        return requestSender;
    }

    public RequestSender authenticate(){
        RequestSender requestSender = new RequestSender();
        requestSender.authenticate();
        return requestSender;
    }

}
