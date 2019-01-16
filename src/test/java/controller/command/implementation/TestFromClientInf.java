package controller.command.implementation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import service.ClientService;
import util.constants.ForwardPagesPaths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;

public class TestFromClientInf {
    private static final String name = "name";
    private static final String surname = "surname";
    private static final String email = "email";
    private static final String telNumber = "telNumber";
    private static final Integer clientId = 1;

    private static FormClientInf formClientInf;
    private static HttpServletRequest servletRequest;
    private static HttpServletResponse servletResponse;
    private static RequestDispatcher requestDispatcher;
    private static ClientService clientService;
    private static HttpSession httpSession;

    @BeforeClass
    public static void init(){
        formClientInf = new FormClientInf();
        servletRequest = Mockito.mock(HttpServletRequest.class);
        servletResponse = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
        httpSession = Mockito.mock(HttpSession.class);
        clientService = Mockito.mock(ClientService.class);
        formClientInf.clientService = clientService;
        Mockito.when(servletRequest.getRequestDispatcher(anyString()))
                .thenReturn(requestDispatcher);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Mockito.when(servletRequest.getMethod()).thenReturn("post");
        Mockito.when(servletRequest.getParameter("name")).thenReturn(name);
        Mockito.when(servletRequest.getParameter("surname")).thenReturn(surname);
        Mockito.when(servletRequest.getParameter("tel")).thenReturn(telNumber);
        Mockito.when(servletRequest.getParameter("email")).thenReturn(email);
        Mockito.when(clientService.validateClient(name, surname, telNumber, email))
                .thenReturn(true);
        Mockito.when(clientService.create(name, surname, telNumber, email))
                .thenReturn(true);
        Mockito.when(servletRequest.getSession()).thenReturn(httpSession);
        Mockito.when(clientService.getClientId(email)).thenReturn(clientId);
        formClientInf.execute(servletRequest,servletResponse);
        Mockito.verify(clientService).validateClient(name, surname, telNumber, email);
        Mockito.verify(clientService).create(name, surname, telNumber, email);
        Mockito.verify(servletRequest).getSession();
        Mockito.verify(httpSession).setAttribute("clientId", clientId);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .FORM_CLIENT_ROOM.toString());
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(servletRequest.getMethod()).thenReturn("get");
        formClientInf.execute(servletRequest,servletResponse);
        Mockito.verify(servletRequest).getRequestDispatcher(ForwardPagesPaths
                .DATA_NOT_SENT.toString());

    }
}
