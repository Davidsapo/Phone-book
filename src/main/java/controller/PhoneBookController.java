package controller;

import dao.ContactDAO;
import model.Address;
import model.Contact;
import utils.ContactValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookController {

    private JTextField nameField, surnameField, phoneNumField, cityField, streetField, houseNumField;
    private JButton clearButton, deleteButton, editButton, addButton;

    private final List<Contact> contacts = new ArrayList<>();
    private final List<Address> addresses = new ArrayList<>();
    private Contact selectedContact = null;
    private Address selectedAddress = null;

    private final ContactDAO contactDAO = new ContactDAO();
    private final ContactValidator contactValidator = new ContactValidator();

    public void initTableData() {

    }

    public void initActionListeners() {
        PhoneBookActionListener actionListener = new PhoneBookActionListener();
        clearButton.addActionListener(actionListener);
        deleteButton.addActionListener(actionListener);
        editButton.addActionListener(actionListener);
        addButton.addActionListener(actionListener);
    }

    private void clear() {

    }

    private void addContact() {

    }

    private void editContact() {

    }

    private void deleteContact() {

    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public void setSurnameField(JTextField surnameField) {
        this.surnameField = surnameField;
    }

    public void setPhoneNumField(JTextField phoneNumField) {
        this.phoneNumField = phoneNumField;
    }

    public void setCityField(JTextField cityField) {
        this.cityField = cityField;
    }

    public void setStreetField(JTextField streetField) {
        this.streetField = streetField;
    }

    public void setHouseNumField(JTextField houseNumField) {
        this.houseNumField = houseNumField;
    }

    public void setClearButton(JButton clearButton) {
        this.clearButton = clearButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    private class PhoneBookActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton)
                clear();
            else if (e.getSource() == deleteButton)
                deleteContact();
            else if (e.getSource() == editButton)
                editContact();
            else if (e.getSource() == addButton)
                addContact();
        }
    }
}
