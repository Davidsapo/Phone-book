package dao;

import exceptions.DataBaseException;
import model.Contact;
import org.sqlite.core.DB;
import utils.ResultSetConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDAO {

    private static final String SQL_SELECT_CONTACTS = "SELECT * FROM contacts;";
    private static final String SQL_ADD_CONTACT = "INSERT INTO contacts (name, surname, phone_number, address_id) VALUES (?, ?, ?, ?);";
    private static final String SQL_DELETE_CONTACT = "DELETE FROM contacts WHERE id = ?;";
    private static final String SQL_UPDATE_CONTACT = "UPDATE contacts SET name=?, surname=?, phone_number=?, address_id=? WHERE id=?;";

    public ArrayList<Contact> select() throws DataBaseException {
        ArrayList<Contact> contacts = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CONTACTS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                contacts.add(ResultSetConverter.convertContact(resultSet));
        } catch (SQLException exception) {
            throw new DataBaseException("SELECT", "contacts", exception);
        }
        return contacts;
    }

    public void add(Contact contact) throws DataBaseException {
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_CONTACT);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getSurname());
            statement.setString(3, contact.getPhoneNumber());
            statement.setInt(4, contact.getAddress_id());
            statement.execute();
        } catch (SQLException exception) {
            throw new DataBaseException("ADD", "contacts", exception);
        }
    }

    public void update(Contact contact) throws DataBaseException {
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CONTACT);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getSurname());
            statement.setString(3, contact.getPhoneNumber());
            statement.setInt(4, contact.getAddress_id());
            statement.setInt(5, contact.getId());
            statement.execute();
        } catch (SQLException exception) {
            throw new DataBaseException("UPDATE", "contacts", exception);
        }
    }

    public void delete(Contact contact) throws DataBaseException {
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CONTACT);
            statement.setInt(1, contact.getId());
            statement.execute();
        } catch (SQLException exception) {
            throw new DataBaseException("DELETE", "contacts", exception);
        }
    }
}