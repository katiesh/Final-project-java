package controller.command.implementation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import service.RequestService;
import util.constants.ForwardPagesPaths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;

public class TestFormClientRoom {

    private static final String classOfRoom = "class";
    private static final String numOfPlaces = "2";
    private static final String dateFrom = "date from";
    private static final String dateTo = "date to";
    private static final Integer clientId = 1;

    private static FormClientRoom formClientRoom;
    private static HttpServletRequest servletRequest;
    private static HttpServletResponse servletResponse;
    private static RequestDispatcher requestDispatcher;
    private static RequestService requestService;
    private static HttpSession httpSession;

    @BeforeClass
    public static void init(){
        formClientRoom = new FormClientRoom();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
        httpSession = Mockito.mock(HttpSession.class);
        requestService = Mockito.mock(RequestService.class);
        formClientRoom.requestService = requestService;
        Mockito.when(servletRequest.getRequestDispatcher(anyString()))
                .thenReturn(requestDispatcher);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Mockito.when(servletRequest.getMethod()).thenReturn("post");
        Mockito.when(servletRequest.getParameter("class")).thenReturn(classOfRoom);
        Mockito.when(servletRequest.getParameter("numOfPlaces")).thenReturn(numOfPlaces);
        Mockito.when(servletRequest.getParameter("dateFrom")).thenReturn(dateFrom);
        Mockito.when(servletRequest.getParameter("dateTo")).thenReturn(dateTo);
        Mockito.when(servletRequest.getSession()).thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute("clientId")).thenReturn(clientId);
        Mockito.when(requestService.validateRequestForm(classOfRoom, numOfPlaces,
                dateFrom, dateTo, clientId)).thenReturn(true);
        Mockito.when(requestService.create(classOfRoom, numOfPlaces,
                dateFrom, dateTo, clientId)).thenReturn(true);
        formClientRoom.execute(servletRequest,servletResponse);
        Mockito.verify(requestService).validateRequestForm(classOfRoom, numOfPlaces,
                dateFrom, dateTo, clientId);
        Mockito.verify(requestService).create(classOfRoom, numOfPlaces,
                dateFrom, dateTo, clientId);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .THANKS_PAGE.toString());
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(servletRequest.getMethod()).thenReturn("get");
        formClientRoom.execute(servletRequest,servletResponse);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .DATA_NOT_SENT.toString());

    }

}
