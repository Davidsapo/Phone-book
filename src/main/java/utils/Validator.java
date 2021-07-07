package utils;

import exceptions.ValidatorException;
import model.Address;
import model.Contact;

import java.util.List;

public class Validator {

    private static final String PHONE_NUMBER_PATTERN = "^0\\d{2} ?\\d{3} ?\\d{2} ?\\d{2}$";

    public Contact validContact(String name, String surname, String phoneNumber, List<Contact> contactList) throws ValidatorException {
        name = name.trim();
        surname = surname.trim();
        phoneNumber = phoneNumber.trim();
        if (name.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty())
            throw new ValidatorException("Empty fields!");
        if (!phoneNumber.matches(PHONE_NUMBER_PATTERN))
            throw new ValidatorException("Invalid phone number!\nPattern: 0XX XXX XX XX or 0XXXXXXXXX");
        for (Contact existingContact : contactList) {
            if (existingContact.getPhoneNumber().equals(phoneNumber))
                throw new ValidatorException("Such phone number is already exists!");
        }
        return new Contact(name, surname, phoneNumber);
    }

    public Address validAddress(String city, String street, String houseNumberText) throws ValidatorException {
        city = city.trim();
        street = street.trim();
        houseNumberText = houseNumberText.trim();
        if (city.isEmpty() || street.isEmpty() || houseNumberText.isEmpty())
            throw new ValidatorException("Empty fields!");
        int houseNumber;
        try {
            houseNumber = Integer.parseInt(houseNumberText);
        } catch (NumberFormatException e) {
            throw new ValidatorException("Invalid house number!");
        }
        if (houseNumber <= 0)
            throw new ValidatorException("House number can not be negative!");
        return new Address(city, street, houseNumber);
    }
}
