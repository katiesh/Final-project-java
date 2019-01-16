package controller.command.implementation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import service.RequestService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

public class TestAllApplications {

    private static AllApplications allApplications;
    private static HttpServletRequest servletRequest;
    private static HttpServletResponse servletResponse;
    private static RequestDispatcher requestDispatcher;
    private static RequestService requestService;

    @BeforeClass
    public static void init(){
        allApplications = new AllApplications();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
        requestService = Mockito.mock(RequestService.class);
        allApplications.requestService = requestService;
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(servletRequest.isUserInRole("admin")).thenReturn(true);
        Mockito.when(servletRequest.getParameter("new_appl")).thenReturn("true");
        Mockito.when(requestService.getNumOfRowsForNewRequests()).thenReturn(3);
        Mockito.when(requestService.findNewRequestsForCurrentPage(anyInt(),
                anyInt())).thenReturn(null);
        Mockito.when(servletRequest.getAttribute("currentPage")).thenReturn(1);
        Mockito.when(servletRequest.getAttribute("recordsPerPage")).thenReturn(3);
        Mockito.when(servletRequest.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        allApplications.execute(servletRequest,servletResponse);
       Mockito.verify(servletRequest).isUserInRole("admin");
       Mockito.verify(servletRequest).getParameter("new_appl");
       Mockito.verify(requestService).getNumOfRowsForNewRequests();
       Mockito.verify(requestService).findNewRequestsForCurrentPage(1,3);
       Mockito.verify(requestDispatcher).forward(servletRequest,servletResponse);
    }
}
