package controller.command.implementation;

import controller.command.Command;
import model.entity.Request;
import model.entity.Room;
import service.BookingService;
import service.RequestService;
import service.RoomService;
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
    RequestService requestService;
    /**
     * field service room
     */
    RoomService roomService;
    /**
     * field service booking
     */
    BookingService bookingService;

    /**
     * constructor without parameters
     */
    public Rooms() {
        requestService = new RequestService();
        roomService = new RoomService();
        bookingService = new BookingService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        String strRequestId = servletRequest.getParameter("requestId");
        if (Validator.isCorrectInt(strRequestId)) {
            int id = Integer.valueOf(strRequestId);
            Request requestItem = requestService.getRequestById(id);
            servletRequest.setAttribute("requestItem", requestItem);
            setCurrentPageRecordsPerPage(servletRequest);
            int currentPage = (Integer)servletRequest.getAttribute("currentPage");
            int recordsPerPage = (Integer)servletRequest.getAttribute("recordsPerPage");
            String checkBoxValue = servletRequest.getParameter("only_date");
            List<Integer> notAvailableRooms = bookingService.getRoomsIdsByDate(requestItem.getDateFrom(), requestItem.getDateTo());
            List<Room> roomsForPage;
            if (checkBoxValue != null && checkBoxValue.equals("true")) {
                setNumOfPages(roomService.getNumOfRowsWithoutNotAvailableRooms(notAvailableRooms), servletRequest);

                roomsForPage = roomService.findRoomsByNotSuitbleIdsForCurrentPage(
                        currentPage, recordsPerPage,notAvailableRooms);
            } else {
                setNumOfPages(roomService.getNumOfRowsByAllRequiredParams(requestItem, notAvailableRooms), servletRequest);
                roomsForPage = roomService.findRoomsByRequestParametersForCurrentPage(currentPage,
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
