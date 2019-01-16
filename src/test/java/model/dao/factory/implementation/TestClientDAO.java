package model.dao.factory.implementation;

import model.dao.factory.FactoryDao;
import model.entity.Client;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import util.constants.TypesDAO;

import java.util.ArrayList;
import java.util.List;

public class TestClientDAO {

    private static Client client;
    private static ClientDAO clientDAO;

    @BeforeClass
    public static void init(){
        clientDAO = (ClientDAO) FactoryDao.getDAO(TypesDAO.CLIENT);
        client = new Client();
        client.setName("Andy");
        client.setSurname("Andreev");
        client.setTelNumber("+380940236781");
        client.setEmail("email@email.com");
    }

    @Test
    public void testCreateDelete(){
        boolean created = false;
        clientDAO.create(client);
        for (Client curClient:clientDAO.getAll()) {
            client.setId(curClient.getId());
            if(client.equals(curClient)){
                created = true;
                clientDAO.delete(curClient);
                break;
            }
        }
        Assert.assertTrue(created && clientDAO.findEntityById(client.getId())==null );
    }

    @Test
    public void testGetNumOfRows(){
        List<Client> clients = clientDAO.getAll();
        Assert.assertEquals(clients.size(), clientDAO.getNumOfRows());
    }

    @Test
    public void testFindWithOffsetFromPosition(){
        List <Client> clients = clientDAO.getAll();
        List<Client> expected = new ArrayList<>();
        for (int i = clients.size()/4; i < clients.size()/2 ; i++) {
            expected.add(clients.get(i));
        }
        Assert.assertEquals(expected,
                clientDAO.findWithOffsetFromPosition(clients.size()/4, expected.size()));
    }

    @Test
    public void testFindEntityByTel(){
        clientDAO.create(client);
        Client actualResult = clientDAO.findEntityByTel(client.getTelNumber());
        clientDAO.delete(client);
        Assert.assertEquals(client.getTelNumber(),
                actualResult.getTelNumber());
    }

    @Test
    public void testFindEntityByEmail(){
        clientDAO.create(client);
        Client actualResult = clientDAO.findEntityByEmail(client.getEmail());
        clientDAO.delete(client);
        Assert.assertEquals(client.getEmail(), actualResult.getEmail());

    }
}
