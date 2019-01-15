package model.entity;

import java.sql.Date;
import java.util.Objects;

/**
 * Describes entity request
 * @author Kateryna Shkulova
 */
public class Request {
    /**
     * Field request's id
     */
    private int id;
    /**
     * Field client's id
     */
    private int clientId;
    /**
     * Field number of places in the room
     */
    private int numOfPlaces;
    /**
     * Field class of the room
     */
    private String classOfRoom;
    /**
     * Field date from
     */
    private Date dateFrom;
    /**
     * Field date to
     */
    private Date dateTo;
    /**
     * Field request's status
     */
    private String status;

    /**
     * @return status {@link #status}
     */
    public String getStatus() {
        return status;
    }

    /**
     * sets class of the room
     * @param classOfRoom {@link #classOfRoom}
     */
    public void setClassOfRoom(String classOfRoom) {
        this.classOfRoom = classOfRoom;
    }

    /**
     * sets id
     * @param id {@link #id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return client's id {@link #clientId}
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * sets client's id
     * @param clientId {@link #clientId}
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return number of places {@link #numOfPlaces}
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
     * @return {@link #dateFrom}
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * sets date from
     * @param dateFrom {@link #dateFrom}
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return date to {@link #dateTo}
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * sets date to
     * @param dateTo {@link #dateTo}
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     * @return
     */
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
