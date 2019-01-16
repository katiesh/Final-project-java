package controller.command.implementation;

import model.entity.Request;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import service.BookingService;
import service.RequestService;
import service.RoomService;
import util.constants.ForwardPagesPaths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

public class TestRooms {

    private static final String requestId = "1";

    private static Rooms rooms;
    private static HttpServletRequest servletRequest;
    private static HttpServletResponse servletResponse;
    private static RequestDispatcher requestDispatcher;
    private static RoomService roomService;
    private static BookingService bookingService;
    private static RequestService requestService;

    @BeforeClass
    public static void init(){
        rooms = new Rooms();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
        roomService = Mockito.mock(RoomService.class);
        bookingService = Mockito.mock(BookingService.class);
        requestService = Mockito.mock(RequestService.class);
        rooms.roomService = roomService;
        rooms.bookingService = bookingService;
        rooms.requestService = requestService;
        Mockito.when(servletRequest.getRequestDispatcher(anyString())).
                thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(servletRequest.getParameter("requestId")).thenReturn(requestId);
        Mockito.when(requestService.getRequestById(1)).thenReturn(new Request());
        Mockito.when(servletRequest.getAttribute("currentPage")).thenReturn(1);
        Mockito.when(servletRequest.getAttribute("recordsPerPage")).thenReturn(3);
        Mockito.when(servletRequest.getParameter("only_date")).thenReturn("true");
        Mockito.when(bookingService.getRoomsIdsByDate(any(Date.class), any(Date.class)))
                .thenReturn(new ArrayList<>());
        Mockito.when(roomService.getNumOfRowsWithoutNotAvailableRooms(any(List.class)))
                .thenReturn(5);
        Mockito.when(roomService.findRoomsByNotSuitbleIdsForCurrentPage(anyInt(),anyInt(),
                any(List.class))).thenReturn(new ArrayList());
        Mockito.when(servletRequest.getRequestDispatcher(anyString())).
                thenReturn(requestDispatcher);
        rooms.execute(servletRequest,servletResponse);
        Mockito.verify(servletRequest).getParameter("requestId");
        Mockito.verify(requestService).getRequestById(1);
        Mockito.verify(servletRequest).getParameter("only_date");
        Mockito.verify(bookingService).getRoomsIdsByDate(null,null);
        Mockito.verify(roomService).getNumOfRowsWithoutNotAvailableRooms(new ArrayList<>());
        Mockito.verify(roomService).findRoomsByNotSuitbleIdsForCurrentPage(1,3,
                new ArrayList<>());
        Mockito.verify(servletRequest).setAttribute("nothingFound", "true");
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .ROOMS.toString());
    }
}
