package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Storm on 19.11.2016.
 */
public class DBConnection {



    public Connection createConnection(){
        String host = "jdbc:mysql://localhost:3306/";
        String database = "test";
        String user = "alex00x6";
        String password = "666999";
        String mysqlParams = "?useSSL=false&" +
                "useUnicode=true&" +
                "useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(host+database+mysqlParams, user , password);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("Connection created successfully!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }

    public void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
