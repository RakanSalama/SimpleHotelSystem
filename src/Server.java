import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mariadb.jdbc.Connection;

public class Server {
    public static void main(String[] args) throws IOException, SQLException {
        ArrayList<String> UsersWhoLoggedIn = new ArrayList<String>();
        UsersWhoLoggedIn.clear();
        try {
            Connection con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/rooms", "root",
                    "1935160");
            Statement stmt = con.createStatement();
            try {
                ResultSet result = stmt.executeQuery("SELECT * FROM rooms;");
                if(!result.next()){
                    stmt.executeQuery(
                        "INSERT INTO rooms VALUES (1,'single',NULL,NULL,NULL,1),(2,'double',NULL,NULL,NULL,1),(3,'double',NULL,NULL,NULL,1),(4,'triple',NULL,NULL,NULL,1),(5,'double',NULL,NULL,NULL,1),(6,'triple',NULL,NULL,NULL,1);");
                }
            } catch (SQLException e) {
            } // Insert only once when server starts
            ServerSocket server = new ServerSocket(2000);
            System.out.println("Server is Online");
            while (true) {
                Socket client = server.accept();

                MyThread thread = new MyThread(client, con);
                thread.start();
            }
        } catch (Exception e) {
        }
    }
}