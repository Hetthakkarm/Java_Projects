package auth;
//login and sign up page so here the main logic of it is:
/*
	login: this would primary used by the operators and mangers(creates operators)
	signup: this would only used by the manager for creating new managers
	exit: exit from the system 
*/
import database.DBConnection; //importing db_connection package used to return live db connection object
import java.sql.*;  //importing all the sql related classes 
/*
SQL libaray contains:
	prepared statements: is used to execute parameterized queries securely and efficiently. prevents SQL injections
	results set: it is used to retrun(fetch) a particular part(data) of the dataset 
*/

public class LoginService {  // class for creating new users and providing authorized access to the already exiting one

	public void signupManager(String username, String password) { //method used for creatinf new managers
    try {
        Connection conn = DBConnection.getConnection(); //opens database connection
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO users(username, password, role) VALUES (?, ?, 'MANAGER')"    //those "?" are placeholders
        );
        ps.setString(1, username); //this used to set the first placeholder values as it set the data
        ps.setString(2, password);//this also used fro the same thing
        ps.executeUpdate(); //used to perfrom insert/update/delete however we are perfroming update query
        System.out.println("Manager account created!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public String login(String username, String password) {   //method used for authentications only
        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                "SELECT user_id, role FROM users WHERE username=? AND password=?"  //used to take inputs and check weather the user and pass exits or not 
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();  //used to execute select queries 
            if (rs.next()) {   //rs.next-> check the data line by line
                int userId = rs.getInt("user_id");
                String role = rs.getString("role");
                System.out.println("Login successful!");
                return userId + ":" + role; // return both
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}