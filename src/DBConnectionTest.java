import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/library_db";
        String username = "root";
        String password = "password";

        try {
            Connection con = DriverManager.getConnection(
                url, username, password
            );

            System.out.println("Connected to MySQL successfully!");

            con.close();

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}