package controller;

import dao.AddressDAO;
import dao.ContactDAO;
import exceptions.DataBaseException;
import exceptions.ValidatorException;
import model.Address;
import model.Contact;
import utils.Validator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PhoneBookController {

    public JTextField nameField, surnameField, phoneNumField, cityField, streetField, houseNumField;
    public JButton clearButton, deleteButton, editButton, addButton;
    public JTable table;

    private final ContactDAO contactDAO = new ContactDAO();
    private final AddressDAO addressDAO = new AddressDAO();
    private final Validator validator = new Validator();

    private List<Contact> contacts;
    private List<Address> addresses;
    private Contact selectedContact = null;

    public void initTableData() {
        try {
            contacts = contactDAO.select();
            addresses = addressDAO.select();
        } catch (DataBaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        for (Address address : addresses) {
            for (Contact contact : contacts) {
                if (contact.getAddress_id() == address.getId())
                    contact.setAddress(address);
            }
        }
        table.setModel(new ContactTableModel());
    }

    public void initActionListeners() {
        PhoneBookActionListener actionListener = new PhoneBookActionListener();
        clearButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);
        editButton.addActionListener(actionListener);
        addButton.addActionListener(actionListener);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSelectedContact(contacts.get(table.getSelectedRow()));
            }
        });
    }

    private void addContact() throws DataBaseException {
        if (selectedContact != null) {
            showMessage("Press clear button first!");
            return;
        }

        Contact newContact;
        Address contactAdders;
        try {
            newContact = validator.validContact(nameField.getText(), surnameField.getText(),phoneNumField.getText(), contacts);
            contactAdders = validator.validAddress(cityField.getText(),streetField.getText(), houseNumField.getText());
        } catch (ValidatorException e) {
            showMessage(e.getMessage());
            return;
        }

        contactAdders = searchOrAddNewAddress(contactAdders);

        newContact.setAddress_id(contactAdders.getId());
        contactDAO.add(newContact);
        newContact = contactDAO.getLast();
        newContact.setAddress(contactAdders);
        contacts.add(newContact);

        clearFields();
        table.revalidate();
        showMessage("Contact successfully added.");
    }

    private Address searchOrAddNewAddress(Address newAddress) throws DataBaseException {
        for (Address address : addresses) {
            if (address.equals(newAddress))
                return address;
        }
        addressDAO.add(newAddress);
        newAddress = addressDAO.getLast();
        addresses.add(newAddress);
        return newAddress;
    }

    private void editContact() {

    }

    private void deleteContact() throws DataBaseException {
        if (selectedContact == null) {
            showMessage("Select the contact!");
            return;
        }
        if (JOptionPane.showConfirmDialog(null, "Are you sure?") == JOptionPane.YES_OPTION) {
            contactDAO.delete(selectedContact);
            contacts.remove(selectedContact);
            clear();
        }
    }

    private void setSelectedContact(Contact contact) {
        selectedContact = contact;
        nameField.setText(contact.getName());
        surnameField.setText(contact.getSurname());
        phoneNumField.setText(contact.getPhoneNumber());
        cityField.setText(contact.getAddress().getCity());
        streetField.setText(contact.getAddress().getStreet());
        houseNumField.setText(String.valueOf(contact.getAddress().getHouse_number()));
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void clearFields() {
        nameField.setText("");
        surnameField.setText("");
        phoneNumField.setText("");
        cityField.setText("");
        streetField.setText("");
        houseNumField.setText("");
    }

    private void clear() {
        clearFields();
        selectedContact = null;
        table.setModel(new ContactTableModel());
    }

    private class PhoneBookActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == clearButton)
                    clear();
                else if (e.getSource() == deleteButton)
                    deleteContact();
                else if (e.getSource() == editButton)
                    editContact();
                else if (e.getSource() == addButton)
                    addContact();
            } catch (DataBaseException dataBaseException) {
                JOptionPane.showMessageDialog(null, dataBaseException.getMessage());
            }
        }
    }

    private class ContactTableModel extends DefaultTableModel {

        private final String[] columnNames = new String[]{"Name", "Surname", "Phone number", "City", "Street", "House number"};

        @Override
        public int getRowCount() {
            return contacts.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return contacts.get(rowIndex).getTableInfo()[columnIndex];
        }
    }
}