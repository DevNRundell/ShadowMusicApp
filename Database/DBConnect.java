package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Creates a single instance of the connection to the database.
 */

public class DBConnect {

    private static final DBConnect instance = new DBConnect();
    private static final String userHome = System.getProperty("user.home") + "\\Documents\\Microsoft Access Projects\\Shadow Music Database\\Shadow Music.accdb";
    private static final String url = "jdbc:ucanaccess://"  + userHome;
    private static final String userNameDB = "";
    private static final String passwordDB = "";

  //------------------------------------------------------------------------------------------------------------------
    private Connection createConnection() {

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(url, userNameDB, passwordDB);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;

    }

  //------------------------------------------------------------------------------------------------------------------
    public static Connection getConnection() {
        return instance.createConnection();
    }

}
