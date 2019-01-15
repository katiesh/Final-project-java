package model.entity;

import java.sql.Date;
import java.util.Objects;

/**
 * Describes booking entity
 * @author Kateryna Shkulova
 */
public class Booking {
    /**
     * Field booking's id
     */
    private int id;
    /**
     * Field room's id
     */
    private int roomId;
    /**
     * Field client's id
     */
    private int clientId;
    /**
     * Field request's id
     */
    private int requestId;
    /**
     * Field booking's status
     */
    private String status;
    /**
     * Field date from
     */
    private Date dateFrom;
    /**
     * Field date to
     */
    private Date dateTo;
    /**
     * Field price
     */
    private double price;

    /**
     * @return id {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * @return requestId {@link #requestId}
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * sets booking's requestId
     * @param requestId {@link #requestId}
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * @return status {@link #status}
     */
    public String getStatus() {
        return status;
    }

    /**
     * sets booking's status
     * @param status {@link #status}
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * sets booking's id
     * @param id {@link #id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return roomId {@link #roomId}
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * sets room's id
     * @param roomId {@link #roomId}
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @return clientId {@link #clientId}
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
     * @return date from {@link #dateFrom}
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
     * @return dateTo {@link #dateTo}
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
     * @return price {@link #price}
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
        Booking booking = (Booking) o;
        return id == booking.id &&
                roomId == booking.roomId &&
                clientId == booking.clientId &&
                requestId == booking.requestId &&
                Double.compare(booking.price, price) == 0 &&
                Objects.equals(status, booking.status) &&
                Objects.equals(dateFrom, booking.dateFrom) &&
                Objects.equals(dateTo, booking.dateTo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, clientId, requestId, status, dateFrom, dateTo, price);
    }
}
