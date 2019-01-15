package controller.command.implementation;

import controller.command.Command;
import model.entity.Request;
import model.entity.Room;
import service.ServiceBookings;
import service.ServiceRequest;
import service.ServiceRoom;
import util.Validator;
import util.constants.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/**
 * implements interface ICommand{@link Command}
 * @author Kateryna Shkulova
 */
public class Rooms extends Command {
    /**
     * field service request
     */
    private ServiceRequest serviceRequest;
    /**
     * field service room
     */
    private ServiceRoom serviceRoom;
    /**
     * field service booking
     */
    private ServiceBookings serviceBookings;

    /**
     * constructor without parameters
     */
    public Rooms() {
        serviceRequest = new ServiceRequest();
        serviceRoom = new ServiceRoom();
        serviceBookings = new ServiceBookings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        String strRequestId = servletRequest.getParameter("requestId");
        if (Validator.isCorrectInt(strRequestId)) {
            int id = Integer.valueOf(strRequestId);
            Request requestItem = serviceRequest.getRequestById(id);
            servletRequest.setAttribute("requestItem", requestItem);
            setCurrentPageRecordsPerPage(servletRequest);
            int currentPage = (Integer)servletRequest.getAttribute("currentPage");
            int recordsPerPage = (Integer)servletRequest.getAttribute("recordsPerPage");
            String checkBoxValue = servletRequest.getParameter("only_date");
            List<Integer> notAvailableRooms = serviceBookings.getRoomsIdsByDate(requestItem.getDateFrom(), requestItem.getDateTo());
            List<Room> roomsForPage;
            if (checkBoxValue != null && checkBoxValue.equals("true")) {
                setNumOfPages(serviceRoom.getNumOfRowsWithoutNotAvailableRooms(notAvailableRooms), servletRequest);

                roomsForPage = serviceRoom.findRoomsByNotSuitbleIdsForCurrentPage(
                        currentPage, recordsPerPage,notAvailableRooms);
            } else {
                setNumOfPages(serviceRoom.getNumOfRowsByAllRequiredParams(requestItem, notAvailableRooms), servletRequest);
                roomsForPage = serviceRoom.findRoomsByRequestParametersForCurrentPage(currentPage,
                        recordsPerPage, requestItem, notAvailableRooms);
            }
            if(roomsForPage.isEmpty()){
                servletRequest.setAttribute("nothingFound", "true");
            }else{
                servletRequest.setAttribute("rooms", roomsForPage);
            }
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .ROOMS.toString()).forward(servletRequest, servletResponse);

        } else {
            servletRequest.getRequestDispatcher(ForwardPagesPaths
                    .ERROR.toString()).forward(servletRequest, servletResponse);
        }

    }
}
