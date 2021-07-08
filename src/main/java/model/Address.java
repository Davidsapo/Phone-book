package model;

import java.util.Objects;

public class Address {

    private int id;
    private String city;
    private String street;
    private int house_number;

    public Address(String city, String street, int house_number) {
        this.city = city;
        this.street = street;
        this.house_number = house_number;
    }

    public Address(int id, String city, String street, int house_number) {
        this(city, street, house_number);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse_number() {
        return house_number;
    }

    public void setHouse_number(int house_number) {
        this.house_number = house_number;
    }

    public boolean equalsWith(Address address) {
        return house_number == address.house_number &&
                city.equalsIgnoreCase(address.city) &&
                street.equalsIgnoreCase(address.street);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                house_number == address.house_number &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, house_number);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house_number=" + house_number +
                '}';
    }
}
