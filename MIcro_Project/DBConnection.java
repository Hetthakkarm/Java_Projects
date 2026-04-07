package database;

import java.sql.Connection; //used to import connection interface from jdbc repsenting active connection session with the mysql
import java.sql.DriverManager;  // helps to create the connection with db as a object

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/vending_db";  //syntax for connections (protocal ,server, portno, db_name)
    private static final String USER = "root"; //name of user as per mysql
    private static final String PASSWORD = "1234"; //password for connection

    public static Connection getConnection() {  // method to return live connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // loading jdbc driver into memory

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);  //main line that help in establishing the connect ,authenticate the user and return the obj       // of the connection

            System.out.println("Database Connected!");
            return conn;

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}