//the manager interface
package manager;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerService {  //class to handle all the manager level work

public void createOperator(String username, String password) {  //method to create a new operator
    try {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO users(username, password, role) VALUES (?, ?, 'OPERATOR')"
        );
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeUpdate();
        System.out.println("Operator created!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void assignMachine(int operatorId, int machineId) { //method to assign a machine to the operator 
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO operator_machine(operator_id, machine_id) VALUES (?, ?)"
        );
        ps.setInt(1, operatorId);
        ps.setInt(2, machineId);
        ps.executeUpdate();
        System.out.println("Machine assigned!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void createMachine(String name, String location) {  //method to create a new vending machine by initializing its name and location
        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO machines(name, location) VALUES (?, ?)"
            );
            ps.setString(1, name);
            ps.setString(2, location);
            ps.executeUpdate();
            System.out.println("Machine created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void todaySalesReport() { // block of code for geting daily reports 
    try {
        Connection conn = DBConnection.getConnection();

        String query =
            "SELECT m.name, COUNT(o.order_id) AS total_orders, " +
            "SUM(o.total_amount) AS revenue " +
            "FROM orders o " +
            "JOIN machines m ON o.machine_id = m.machine_id " +
            "WHERE DATE(o.created_at) = CURDATE() " +
            "GROUP BY m.machine_id";

        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n===== TODAY SALES REPORT =====");
        System.out.println("Machine\t\tOrders\t\tRevenue");

        while (rs.next()) {
            System.out.println(
                rs.getString(1) + "\t\t" +
                rs.getInt(2) + "\t\t₹" +
                rs.getDouble(3)
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}