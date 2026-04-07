package machine;

//this page contains buyer side logics
import database.DBConnection;
import java.sql.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
/*
LOGIC:
	first when user enter as buyer this interface openas where a list of avilable vending machine opens where they have to select any one of them to operate on 
	on the machine is selected the items of machine get displayed on the screen where the buyer can enter the code of particular items to despenes than 
	there is a cart system implemented where user can add more than items of different codes and can checkout the whole cart.
*/

public class MachineService {  //class for handling buyer side 

    public void displayItems(int machineId) {  //method used for displaying the items from the selected vending machine
        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                "SELECT i.item_id, i.name, i.price, mi.quantity " +
                "FROM items i JOIN machine_inventory mi " +
                "ON i.item_id = mi.item_id WHERE mi.machine_id = ?"
            );

            ps.setInt(1, machineId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nID | Item | Price | Stock");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("item_id") + " | " +
                    rs.getString("name") + " | ₹" +
                    rs.getDouble("price") + " | " +
                    rs.getInt("quantity")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayMachines() {  //block of code used to display all the avilable machines and user have to select only 1 machine from it for operating on it
        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                "SELECT machine_id, name, location FROM machines"
            );

            ResultSet rs = ps.executeQuery();

            System.out.println("\nAvailable Machines:");
            while (rs.next()) {
                System.out.println(
                    rs.getInt(1) + " | " +
                    rs.getString(2) + " | " +
                    rs.getString(3)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyFromMachine() {  //this method is the heart of our system where main vending machine simulation is done over here

        Scanner sc = new Scanner(System.in);
        try {
            displayMachines(); //display list of machines

            System.out.print("\nSelect Machine ID: ");
            int machineId = sc.nextInt();

            Connection conn = DBConnection.getConnection();

            // CART  (itemId → quantity) logic implementation over here 
            Map<Integer, Integer> cart = new HashMap<>(); // creating a cart 
            double total = 0;
            while (true) {
                displayItems(machineId); // display the items of selected machine

                System.out.print("\nEnter Item ID (0 to checkout): ");
                int itemId = sc.nextInt();

                if (itemId == 0) break;
                    PreparedStatement ps = conn.prepareStatement(  //fetching the price of selected items
                    "SELECT price FROM items WHERE item_id = ?"
                );

                ps.setInt(1, itemId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {   //check whether the item is there
                    double price = rs.getDouble("price"); //fetching the price

                    // Add to cart logic
                    cart.put(itemId, cart.getOrDefault(itemId, 0) + 1); //if items exits than add to cart mean cart +1 else 1
                    total += price;

                    System.out.println("Added to cart: ₹" + price);

                } else {
                    System.out.println("Invalid item!");
                }
            }

            // If cart empty check
            if (cart.isEmpty()) {
                System.out.println("Cart is empty!");
                return;
            }

            //Show cart summary   
            System.out.println("\n--- CART SUMMARY ---");
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                System.out.println("Item ID: " + entry.getKey() +
                                   " | Qty: " + entry.getValue());
            }

            System.out.println("Total Amount: ₹" + total);

            System.out.print("Confirm purchase (1/0): ");  //1 ->yes than checkout the cart 
            int confirm = sc.nextInt();

            if (confirm == 1) {

                // Update stock properly
                for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

                    int itemId = entry.getKey();
                    int qty = entry.getValue();

                    PreparedStatement update = conn.prepareStatement(
                        "UPDATE machine_inventory SET quantity = quantity - ? " +
                        "WHERE machine_id = ? AND item_id = ? AND quantity >= ?"
                    );

                    update.setInt(1, qty);
                    update.setInt(2, machineId);
                    update.setInt(3, itemId);
                    update.setInt(4, qty);

                    int rows = update.executeUpdate();

                    if (rows == 0) {  //this check whether the items get out of stock or not
                        System.out.println("Not enough stock for item ID: " + itemId);
                        return;
                    }
                }

                //  Create order and despense the items
                PreparedStatement order = conn.prepareStatement(
                    "INSERT INTO orders(machine_id, total_amount) VALUES (?, ?)"
                );

                order.setInt(1, machineId);
                order.setDouble(2, total);
                order.executeUpdate();

                System.out.println("\nItems dispensed successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}