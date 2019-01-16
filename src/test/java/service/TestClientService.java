package service;

import model.dao.factory.implementation.ClientDAO;
import model.entity.Client;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class TestClientService {

    private static ClientService clientService;
    private static ClientDAO clientDAO;

    @BeforeClass
    public static void init(){
        clientDAO = Mockito.mock(ClientDAO.class);
        clientService = new ClientService(clientDAO);
    }

    @Test
    public void testGetClientIdByEmail(){
        int id = 1;
        Client client = new Client();
        client.setId(id);
        Mockito.when(clientDAO.findEntityByEmail(anyString())).thenReturn(client);
        int result = clientService.getClientId("email");
        Mockito.verify(clientDAO).findEntityByEmail("email");
        Assert.assertEquals(id, result);
    }

    @Test
    public void testValidateClient(){
        Assert.assertFalse(clientService.validateClient("Kasd@13", "   ", "QWE@11",
                "qwrdjwksdlv"));
    }

    @Test
    public void testGetClientByEmailOrTel(){
        Client client1 = new Client();
        Client client2 = new Client();
        client1.setId(1);
        client2.setId(2);
        List<Client> clients = Arrays.asList(client1, client2);
        String email = "eshku@ukr.net";
        String telNum = "+380234857123";
        Mockito.when(clientDAO.findEntityByEmail(email)).thenReturn(client1);
        Mockito.when(clientDAO.findEntityByTel(telNum)).thenReturn(client2);
        Client resultClient1 = clientService.getClientByEmailOrTel(email);
        Client resultClient2 = clientService.getClientByEmailOrTel(telNum);
        Mockito.verify(clientDAO).findEntityByTel(telNum);
        List<Client> resultClients = Arrays.asList(resultClient1,resultClient2);
        Assert.assertEquals(clients, resultClients);

    }

    @Test
    public void testCreate(){
        Client client = new Client();
        String name = "name";
        String surname = "surname";
        String email = "email";
        String telNum = "telNum";
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setTelNumber(telNum);
        Mockito.when(clientDAO.create(any(Client.class))).thenReturn(true);
        Mockito.when(clientDAO.findEntityByTel(telNum)).thenReturn(null);
        Mockito.when(clientDAO.findEntityByEmail(email)).thenReturn(null);
        boolean result = clientService.create(name, surname, email, telNum);
        Mockito.verify(clientDAO).create(any(Client.class));
        Assert.assertTrue(result);
    }
}
