package model.entity;

import java.util.Objects;

public class Room {
    private int id;
    private int numOfPlaces;
    private String classOfRoom;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumOfPlaces() {
        return numOfPlaces;
    }

    public void setNumOfPlaces(int numOfPlaces) {
        this.numOfPlaces = numOfPlaces;
    }

    public String getClassOfRoom() {
        return classOfRoom;
    }

    public void setClassOfRoom(String classOfRoom) {
        this.classOfRoom = classOfRoom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, numOfPlaces, classOfRoom, price);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", numOfPlaces=" + numOfPlaces +
                ", classOfRoom='" + classOfRoom + '\'' +
                ", price=" + price +
                '}';
    }
}
