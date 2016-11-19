package api.inputData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class GenerateJSONForJIRA {

    PropertiesInput propertiesInput = new PropertiesInput();

    public String login(){
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject credentials = new JSONObject();
        credentials.put("username", credentialsFromProperties.get("login"));
        credentials.put("password", credentialsFromProperties.get("password_right"));

        /*
        {
        "username": "login",
        "password": "pwd"
        }
         */

        return credentials.toJSONString();
    }


    public String createSampleIssue(){
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject issue = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject project = new JSONObject();
        JSONObject issuetype = new JSONObject();
        JSONObject assignee = new JSONObject();
        JSONObject reporter = new JSONObject();

        fields.put("project", project);
        fields.put("summary", credentialsFromProperties.get("summary_text"));
        fields.put("issuetype", issuetype);
        fields.put("assignee", assignee);
        fields.put("reporter", reporter);

        reporter.put("name", credentialsFromProperties.get("reporter_name"));
        assignee.put("name", credentialsFromProperties.get("assignee_name"));
        issuetype.put("id", credentialsFromProperties.get("issue_type"));
        project.put("id", credentialsFromProperties.get("project_id"));

        issue.put("fields", fields);

        /*
        {
	    "fields": {
		"project": {
			"id": "10315"
		},
		"summary": "creating test issue with soap",
		"issuetype": {
			"id": "10004"
		},
		"assignee": {
			"name": "alex00x6"
		},
		"reporter": {
			"name": "alex00x6"
		}
	    }
        }
        */

        return issue.toJSONString();
    }

    public String addCommentToIssue(){
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject credentials = new JSONObject();

        credentials.put("body", credentialsFromProperties.get("comment_text"));

        return credentials.toJSONString();
    }

    public String changeTypeOfIssue() {
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject credentials = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject issuetype = new JSONObject();

        issuetype.put("id", credentialsFromProperties.get("issue_type"));
        fields.put("issuetype", issuetype);
        credentials.put("fields", fields);

        /*
           {
	        "fields":
	        {
		    "issuetype": {"id": "10001"},
	        }
           }
         */

        System.out.println(credentials.toString());
        return credentials.toJSONString();
    }



    public String changeSummaryOfIssue() {
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject credentials = new JSONObject();
        JSONObject update = new JSONObject();
        JSONObject summary = new JSONObject();
        JSONArray insideSummary = new JSONArray();

        summary.put("set", credentialsFromProperties.get("summary_text"));

        insideSummary.add(summary);

        update.put("summary", insideSummary);
        credentials.put("update", update);


        /*
        {
	        "update":{
		    "summary":[{"set":"changing summary with soap"}]
	        }
        }
         */
        System.out.println(credentials.toString());
        return credentials.toJSONString();
    }


    public String search() {
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject credentials = new JSONObject();
        JSONArray array = new JSONArray();

        //array.add("summary");
        array.add("key");

        credentials.put("jql", credentialsFromProperties.get("jql"));
        credentials.put("startAt","0");
        credentials.put("maxResults", credentialsFromProperties.get("max_results"));
        credentials.put("fields", array);

        /*
            {"jql":"project = QAAUT","maxResults":"15","startAt":"0"}
        */

        System.out.println(credentials.toString());
        return credentials.toJSONString();
    }

    public String searchByJql(String jql) {
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject credentials = new JSONObject();
        JSONArray array = new JSONArray();

        array.add("summary");
        array.add("key");

        credentials.put("jql", jql);
        credentials.put("startAt","0");
        credentials.put("maxResults", credentialsFromProperties.get("max_results"));
        credentials.put("fields", array);

        System.out.println(credentials.toString());
        return credentials.toJSONString();
    }

    public String createForapiTestIssue(){
        HashMap<String, String> credentialsFromProperties = propertiesInput.readProperties();
        JSONObject issue = new JSONObject();
        JSONObject fields = new JSONObject();
        JSONObject project = new JSONObject();
        JSONObject issuetype = new JSONObject();
        JSONObject assignee = new JSONObject();
        JSONObject reporter = new JSONObject();

        fields.put("project", "TES");
        fields.put("summary", credentialsFromProperties.get("summary_text"));
        fields.put("issuetype", issuetype);
        fields.put("assignee", assignee);
        fields.put("reporter", reporter);

        reporter.put("name", credentialsFromProperties.get("reporter_name"));
        assignee.put("name", credentialsFromProperties.get("assignee_name"));
        issuetype.put("id", credentialsFromProperties.get("issue_type"));
        project.put("id", credentialsFromProperties.get("project_id"));

        issue.put("fields", fields);

        /*
        {
	    "fields": {
		"project": {
			"id": "10315"
		},
		"summary": "creating test issue with soap",
		"issuetype": {
			"id": "10004"
		},
		"assignee": {
			"name": "alex00x6"
		},
		"reporter": {
			"name": "alex00x6"
		}
	    }
        }
        */

        return issue.toJSONString();
    }
}
