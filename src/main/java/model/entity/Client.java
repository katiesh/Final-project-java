package model.entity;

import java.util.Objects;

/**
 * Describes entity client
 * @author Kateryna Shkulova
 */
public class Client {
    /**
     * Field client's id
     */
    private int id;
    /**
     * Field client's name
     */
    private String name;
    /**
     * Field client's surname
     */
    private String surname;
    /**
     * Filed client's telephone number
     */
    private String telNumber;
    /**
     * Field client's email
     */
    private String email;

    /**
     * sets id
     * @param id {@link #id}
     * }
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link #surname}
     */
    public String getSurname() {
        return surname;
    }

    /**
     * sets surname
     * @param surname {@link #surname}
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return {@link #telNumber}
     */
    public String getTelNumber() {
        return telNumber;
    }

    /**
     * sets telephone number
     * @param telNumber {@link #telNumber}
     */
    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    /**
     * @return {@link #email}
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email
     * @param email {@link #email}
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
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
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, telNumber, email);
    }
}
