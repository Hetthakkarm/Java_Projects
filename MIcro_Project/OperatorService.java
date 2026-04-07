//operator page
package operator;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*
LOGIC:
	operator get this interface by login succesfully 
		here operator can see there machine items assigned by the maanger
		can perfrom basic operation like add new item / update the stock of it 
*/
public class OperatorService {  //andles all the operator related serevices

    public void addItem(String name, double price) {  //method used to create new item by initializing its name and price
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO items(name, price) VALUES (?, ?)"
            );
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.executeUpdate();
            System.out.println("Item added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void viewMyMachines(int operatorId) {  //used to display the machine which i assigned to the particular operator
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(
            "SELECT m.machine_id, m.name FROM machines m " +
            "JOIN operator_machine om ON m.machine_id = om.machine_id " +
            "WHERE om.operator_id = ?"
        );
        ps.setInt(1, operatorId);
        ResultSet rs = ps.executeQuery();
        System.out.println("Your Machines:");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " - " + rs.getString(2));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void addStock(int machineId, int itemId, int qty) {  // method to refill the stock of a particular item  by entering the machine id item id and quantity
        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE machine_inventory SET quantity = quantity + ? " +
                "WHERE machine_id = ? AND item_id = ?"
            );
            ps.setInt(1, qty);
            ps.setInt(2, machineId);
            ps.setInt(3, itemId);
            int rows = ps.executeUpdate(); //upadte the quantity of the item
            if (rows == 0) {  //if the row is 0 than there is such item exits so we create an new record for it
                PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO machine_inventory(machine_id, item_id, quantity) VALUES (?, ?, ?)"
                );
                insert.setInt(1, machineId);
                insert.setInt(2, itemId);
                insert.setInt(3, qty);
                insert.executeUpdate();
            }

            System.out.println("Stock updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void viewMachineItems(int operatorId) {  // used to display the full inventory of the assigned machine
    try {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(
            "SELECT m.machine_id, i.name, i.price, mi.quantity " +
            "FROM machine_inventory mi " +
            "JOIN machines m ON mi.machine_id = m.machine_id " +
            "JOIN items i ON mi.item_id = i.item_id " +
            "JOIN operator_machine om ON om.machine_id = m.machine_id " +
            "WHERE om.operator_id = ?"
        );
        ps.setInt(1, operatorId);
        ResultSet rs = ps.executeQuery();
        System.out.println("\nYour Machine Items:");
        while (rs.next()) {
            System.out.println(
                "Machine " + rs.getInt(1) +
                " | " + rs.getString(2) +
                " | ₹" + rs.getDouble(3) +
                " | Qty: " + rs.getInt(4)
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}