//the main logic or part of the system where all things are colected and placed
import auth.LoginService; //authentication and creating new manager
import manager.ManagerService; //managing the manager services
import operator.OperatorService; //used to manage the vending machine inventory
import machine.MachineService; // used to manage the vending machine operations

import java.util.Scanner;

/* working flow and logic its menu driven part
		the begining interface is of login/sign up page from there we divide the users like buyer, login->operators and managers, signup-> for manager only
		buyers->to buying interface where he got the list of machine and menually selects machine on which they want to buy the items along with cart system implementation
		Operator-> reposinable for add new item ,update the stock of  items in the machine and only wok on assigned machine only
		Manager->responsiable for creating and assign tasks to operators, creatinf and manageing vending machine , can see day to day sales report
*/
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginService loginService = new LoginService();
        ManagerService manager = new ManagerService();
        OperatorService operator = new OperatorService();
        MachineService machine = new MachineService();
        while (true) {
            System.out.println("\n==== VENDING SYSTEM ====");
            System.out.println("1. Buyer");
            System.out.println("2. Login");
            System.out.println("3. Signup (Manager)");
            System.out.println("4. Exit");
            int choice = sc.nextInt();
            if (choice == 1) {
                machine.buyFromMachine(); // calls the method of package macine service redirected to buyers interface
            }
            else if (choice == 2) {// block of code for authentication work only based on roles ->manager/operator the respective interface get open
                sc.nextLine();
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                String result = loginService.login(username, password);
                if (result == null) {
                    System.out.println("Invalid login!");
                    continue;
                }
                String[] parts = result.split(":");
                int userId = Integer.parseInt(parts[0]);
                String role = parts[1];
                if (role.equalsIgnoreCase("MANAGER")) {  //if the logined person is manager than
                    while (true) {
                        System.out.println("\n--- MANAGER PANEL ---");
                        System.out.println("1. Add Machine"); //add new machine
                        System.out.println("2. Create Operator");//add new operator
                        System.out.println("3. Assign Machine");//assign the machine to operator
                        System.out.println("4. Today Sales Report"); //get current date sales report 
			System.out.println("5. Back");
                        int m = sc.nextInt();
                        if (m == 1) { //add new machine by taking inputs like machine name,location 
                            sc.nextLine();
                            System.out.print("Machine name: ");
                            String name = sc.nextLine();
                            System.out.print("Location: ");
                            String loc = sc.nextLine();
                            manager.createMachine(name, loc);
                        }
                        else if (m == 2) { //creates new operator by creating its username and password
                            sc.nextLine();
                            System.out.print("Operator Username: ");
                            String u = sc.nextLine();
                            System.out.print("Operator Password: ");
                            String p = sc.nextLine();
                            manager.createOperator(u, p);
                        }
                        else if (m == 3) { //used to assign the n=machine to the operator perfrom id based mapping operator id mapped with machine id
                            System.out.print("Operator ID: ");
                            int opId = sc.nextInt();
                            System.out.print("Machine ID: ");
                            int mId = sc.nextInt();
                            manager.assignMachine(opId, mId);
                        }
			else if (m == 4) { //genrate machine wise today sale report
    				manager.todaySalesReport();
			}
                        else if (m == 5) {
                            break;
                        }
                    }
                }
               else if (role.equalsIgnoreCase("OPERATOR")) { //if logined person is operator
                    while (true) {
                        System.out.println("\n--- OPERATOR PANEL ---");
                        operator.viewMachineItems(userId); //get view of the assigned machine 
                        System.out.println("\n1. Add Item"); //used to create a new machine
                        System.out.println("2. Add Stock"); //used to update stock
                        System.out.println("3. Back");
                        int op = sc.nextInt();
                        if (op == 1) {  //used to add new items by inputing data like name and price 
                            sc.nextLine();
                            System.out.print("Item name: ");
                            String name = sc.nextLine();
                            System.out.print("Price: ");
                            double price = sc.nextDouble();
                            operator.addItem(name, price);
                        }
                        else if (op == 2) { //used to update the stock by selecting machine id ,item id and entering the quantities
                            System.out.print("Machine ID: ");
                            int mId = sc.nextInt();
                            System.out.print("Item ID: ");
                            int iId = sc.nextInt();
                            System.out.print("Qty: ");
                            int qty = sc.nextInt();
                            operator.addStock(mId, iId, qty);
                        }
                        else if (op == 3) {
                            break;
                        }
                    }
                }
            }
            else if (choice == 3) { //signup part for creating new manager 
                sc.nextLine();
                System.out.print("New Username: ");
                String username = sc.nextLine();
                System.out.print("New Password: ");
                String password = sc.nextLine();
                loginService.signupManager(username, password);
            }
            else {
                System.out.println("Exiting...");
                break;
            }
        }
        sc.close();
    }
}