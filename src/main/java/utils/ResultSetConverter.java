package utils;

import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetConverter {

    public static Contact convertContact(ResultSet resultSet) throws SQLException {
        return new Contact(resultSet.getInt(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getString(4),
                resultSet.getInt(5));
    }
}
