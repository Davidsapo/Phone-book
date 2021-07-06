package dao;

import exceptions.DataBaseException;
import model.Address;
import utils.ResultSetConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDAO {

    private static final String SQL_SELECT_ADDRESSES = "SELECT * FROM address;";
    private static final String SQL_ADD_ADDRESS = "INSERT INTO address (city, street, house_number) VALUES (?, ?, ?);";
    private static final String SQL_GET_LAST_ADDRESS = "SELECT * FROM address WHERE id = (SELECT MAX(id) FROM address);";

    public ArrayList<Address> select() throws DataBaseException {
        ArrayList<Address> addresses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ADDRESSES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                addresses.add(ResultSetConverter.convertAddress(resultSet));
        } catch (SQLException exception) {
            throw new DataBaseException("SELECT", "address", exception);
        }
        return addresses;
    }

    public void add(Address address) throws DataBaseException {
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_ADDRESS);
            statement.setString(1, address.getCity());
            statement.setString(2, address.getStreet());
            statement.setInt(3, address.getHouse_number());
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DataBaseException("ADD", "address", exception);
        }
    }

    public Address getLast() throws DataBaseException {
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_LAST_ADDRESS);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return ResultSetConverter.convertAddress(resultSet);
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DataBaseException("GETLAST", "address", exception);
        }
    }
}
