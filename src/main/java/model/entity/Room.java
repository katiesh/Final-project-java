package model.entity;

import java.util.Objects;

/**
 * Describes entity room
 * @author Kateryna Shkulova
 */
public class Room {
    /**
     * Field room's id
     */
    private int id;
    /**
     * Field number of places in the room
     */
    private int numOfPlaces;
    /**
     * Field class of the room
     */
    private String classOfRoom;
    /**
     * field price per night of the room
     */
    private double price;

    /**
     * @return id {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * sets id
     * @param id {@link #id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return {@link #numOfPlaces}
     */
    public int getNumOfPlaces() {
        return numOfPlaces;
    }

    /**
     * sets number of places
     * @param numOfPlaces {@link #numOfPlaces}
     */
    public void setNumOfPlaces(int numOfPlaces) {
        this.numOfPlaces = numOfPlaces;
    }

    /**
     * @return class of room {@link #classOfRoom}
     */
    public String getClassOfRoom() {
        return classOfRoom;
    }

    /**
     * sets class of room
     * @param classOfRoom {@link #classOfRoom}
     */
    public void setClassOfRoom(String classOfRoom) {
        this.classOfRoom = classOfRoom;
    }

    /**
     * @return  price {@link #price}
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets price
     * @param price {@link #price}
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                numOfPlaces == room.numOfPlaces &&
                Double.compare(room.price, price) == 0 &&
                Objects.equals(classOfRoom, room.classOfRoom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, numOfPlaces, classOfRoom, price);
    }
}
