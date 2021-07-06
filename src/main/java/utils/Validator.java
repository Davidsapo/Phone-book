package utils;

import model.Address;
import model.Contact;

public class Validator {

    public boolean validContact(Contact contact) {
        return !contact.getName().isEmpty() && !contact.getSurname().isEmpty() && !contact.getPhoneNumber().isEmpty();
    }

    public boolean validAddress(Address address) {
        return !address.getStreet().isEmpty() && !address.getCity().isEmpty() && address.getHouse_number() > 0;
    }
}
