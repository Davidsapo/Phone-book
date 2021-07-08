package dao;

import exceptions.DataBaseException;
import model.Contact;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDAOTest {

    private static final String CREATE_TEST_TABLE_SCRIPT = "CREATE TABLE \"contacts\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL,\n" +
            "\t\"name\"\tTEXT NOT NULL,\n" +
            "\t\"surname\"\tTEXT NOT NULL,\n" +
            "\t\"phone_number\"\tTEXT NOT NULL UNIQUE,\n" +
            "\t\"address_id\"\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";

    private static final String CREATE_TEST_DATA_SCRIPT = "INSERT INTO contacts(name, surname, phone_number, address_id)\n" +
            "VALUES ('Dan', 'Smith', '000', 5),\n" +
            "      ('Molly', 'Brown', '111', 100),\n" +
            "      ('Ann', 'Wilson', '555', 567)";

    private static final ArrayList<Contact> TEST_DATA = new ArrayList<>();

    static {
        TEST_DATA.add(new Contact(1, "Dan", "Smith", "000", 5));
        TEST_DATA.add(new Contact(2, "Molly", "Brown", "111", 100));
        TEST_DATA.add(new Contact(3, "Ann", "Wilson", "555", 567));
    }

    private static final String DELETE_TEST_TABLE_SCRIPT = "DROP TABLE contacts;";
    private static final String RESERVE_ACTUAL_TABLE_SCRIPT = "ALTER TABLE contacts\n" +
            "  RENAME TO contacts_reserve;";
    private static final String RETURN_ACTUAL_TABLE_SCRIPT = "ALTER TABLE contacts_reserve\n" +
            "  RENAME TO contacts;";

    private static final ContactDAO CONTACT_DAO = new ContactDAO();

    @BeforeClass
    public static void reserveActualTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        connection.prepareStatement(RESERVE_ACTUAL_TABLE_SCRIPT).execute();
        connection.close();
    }

    @AfterClass
    public static void returnActualTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        connection.prepareStatement(RETURN_ACTUAL_TABLE_SCRIPT).execute();
        connection.close();
    }

    @Before
    public void createTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_TEST_TABLE_SCRIPT);
        statement.execute();
        connection.close();
        connection = DBConnection.getConnection();
        PreparedStatement statement1 = connection.prepareStatement(CREATE_TEST_DATA_SCRIPT);
        statement1.execute();
        connection.close();
    }

    @After
    public void deleteTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement2 = connection.prepareStatement(DELETE_TEST_TABLE_SCRIPT);
        statement2.execute();
        connection.close();
    }

    @Test
    public void select() throws DataBaseException {
        ArrayList<Contact> contacts = CONTACT_DAO.select();
        Assert.assertEquals(contacts, TEST_DATA);
    }

    @Test
    public void add() throws DataBaseException {
        Contact newContact = new Contact("Test", "Surname","123");
        newContact.setAddress_id(222);
        CONTACT_DAO.add(newContact);
        newContact.setId(4);
        Contact lastAdded = CONTACT_DAO.getLast();
        Assert.assertEquals(lastAdded, newContact);
    }

    @Test
    public void update() throws DataBaseException {
        Contact updatedContact = CONTACT_DAO.select().get(0);
        updatedContact.setName("New name");
        updatedContact.setSurname("New surname");
        updatedContact.setPhoneNumber("321");
        updatedContact.setAddress_id(90);
        CONTACT_DAO.update(updatedContact);
        Assert.assertEquals(CONTACT_DAO.select().get(0), updatedContact);
    }

    @Test
    public void delete() throws DataBaseException {
        ArrayList<Contact> contacts = CONTACT_DAO.select();
        Contact deletedContact = contacts.get(2);
        contacts.remove(deletedContact);
        CONTACT_DAO.delete(deletedContact);
        Assert.assertEquals(CONTACT_DAO.select(), contacts);
    }

   @Test
    public void getLast() throws DataBaseException {
       Contact newContact = new Contact("Test", "Surname","123");
       newContact.setAddress_id(222);
       newContact.setId(4);
       CONTACT_DAO.add(newContact);
       Assert.assertEquals(CONTACT_DAO.getLast(), newContact);
    }
}