package dao;

import exceptions.DataBaseException;
import model.Address;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDAOTest {

    private static final String CREATE_TEST_TABLE_SCRIPT = "CREATE TABLE \"address\" (\n" +
            "\t\"id\"\tINTEGER NOT NULL,\n" +
            "\t\"city\"\tTEXT NOT NULL,\n" +
            "\t\"street\"\tTEXT NOT NULL,\n" +
            "\t\"house_number\"\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
            ")";

    private static final String CREATE_TEST_DATA_SCRIPT = "INSERT INTO address(city, street, house_number)\n" +
            "VALUES ('Kiev', 'Main', 15),\n" +
            "      ('Vinnitsa', 'Central', 195),\n" +
            "      ('Odessa', 'Street', 233)";

    private static final ArrayList<Address> TEST_DATA = new ArrayList<>();

    static {
        TEST_DATA.add(new Address(1, "Kiev", "Main", 15));
        TEST_DATA.add(new Address(2, "Vinnitsa", "Central", 195));
                TEST_DATA.add(new Address(3, "Odessa", "Street", 233));
    }

    private static final String DELETE_TEST_TABLE_SCRIPT = "DROP TABLE address;";
    private static final String RESERVE_ACTUAL_TABLE_SCRIPT = "ALTER TABLE address\n" +
            "  RENAME TO address_reserve;";
    private static final String RETURN_ACTUAL_TABLE_SCRIPT = "ALTER TABLE address_reserve\n" +
            "  RENAME TO address;";

    private static final AddressDAO ADDRESS_DAO = new AddressDAO();

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
        ArrayList<Address> contacts = ADDRESS_DAO.select();
        Assert.assertEquals(contacts, TEST_DATA);
    }

    @Test
    public void add() throws DataBaseException {
        Address newAddress = new Address("Test", "Some street", 123);
        ADDRESS_DAO.add(newAddress);
        newAddress.setId(4);
        Assert.assertEquals(ADDRESS_DAO.getLast(), newAddress);
    }

    @Test
    public void getLast() throws DataBaseException {
        Address newAddress = new Address("Test", "Some street", 123);
        ADDRESS_DAO.add(newAddress);
        newAddress.setId(4);
        Assert.assertEquals(ADDRESS_DAO.getLast(), newAddress);
    }
}