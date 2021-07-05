package dao;

import utils.PropertiesHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.*;

public class DBConnection {

    private static boolean driverLoaded = false;

    public static Connection getConnection() throws SQLException {
        if (!driverLoaded){
            DriverManager.registerDriver(new JDBC());
            driverLoaded = true;
        }
        return DriverManager.getConnection(PropertiesHolder.getProperty("DB_URL"));
    }
}
