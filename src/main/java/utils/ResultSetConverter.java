package utils;

import model.Address;
import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetConverter {

    public static Contact convertContact(ResultSet resultSet) throws SQLException {
        return new Contact(resultSet.getInt(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getString(4),
                resultSet.getInt(5));
    }

    public static Address convertAddress(ResultSet resultSet) throws SQLException {
        return new Address(resultSet.getInt(1), resultSet.getString(2),
                resultSet.getString(3), resultSet.getInt(4));
    }
}
