package model.entity;

import java.util.Objects;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String telNumber;
    private String email;

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(name, client.name) &&
                Objects.equals(surname, client.surname) &&
                Objects.equals(telNumber, client.telNumber) &&
                Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, telNumber, email);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
