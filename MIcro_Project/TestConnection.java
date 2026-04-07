import database.DBConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("Working ");
        } else {
            System.out.println("Not Working ");
        }
    }
}