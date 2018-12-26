package model.entity;

import java.sql.Date;
import java.util.Objects;

public class Booking {
    private int id;
    private int roomId;
    private int clientId;
    private int requestId;
    private String status;
    private Date dateFrom;
    private Date dateTo;

    public int getId() {
        return id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id &&
                roomId == booking.roomId &&
                clientId == booking.clientId &&
                requestId == booking.requestId &&
                Objects.equals(status, booking.status) &&
                Objects.equals(dateFrom, booking.dateFrom) &&
                Objects.equals(dateTo, booking.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, clientId, requestId, status, dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", clientId=" + clientId +
                ", requestId=" + requestId +
                ", status='" + status + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
