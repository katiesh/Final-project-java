package model.entity;

import java.sql.Date;
import java.util.Objects;

public class Request {
    private int id;
    private int clientId;
    private int numOfPlaces;
    private String classOfRoom;
    private Date dateFrom;
    private Date dateTo;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setClassOfRoom(String classOfRoom) {
        this.classOfRoom = classOfRoom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getNumOfPlaces() {
        return numOfPlaces;
    }

    public void setNumOfPlaces(int numOfPlaces) {
        this.numOfPlaces = numOfPlaces;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", numOfPlaces=" + numOfPlaces +
                ", classOfRoom='" + classOfRoom + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                clientId == request.clientId &&
                numOfPlaces == request.numOfPlaces &&
                Objects.equals(classOfRoom, request.classOfRoom) &&
                Objects.equals(dateFrom, request.dateFrom) &&
                Objects.equals(dateTo, request.dateTo) &&
                Objects.equals(status, request.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, numOfPlaces, classOfRoom, dateFrom, dateTo, status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getClassOfRoom() {
        return classOfRoom;
    }

}
