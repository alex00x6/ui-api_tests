package Jira;

import db.DBConnection;
import org.testng.annotations.Test;

public class JDBC_connection_test {

    @Test
    public void jdbc_test() {
        DBConnection dbConnection = new DBConnection();

        System.out.println("-------- MySQL JDBC Connection Testing ------------");
        dbConnection.createConnection();
    }
}