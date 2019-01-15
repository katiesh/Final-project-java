package service;

import model.dao.factory.FactoryDao;
import model.dao.factory.implementation.ClientDAO;
import model.entity.Client;
import util.Validator;
import util.constants.TypesDAO;

/**
 * provides services for clients
 */
public class ServiceClient{
    /**
     * field dao
     */
    private ClientDAO dao;

    /**
     * constructor without parameters
     */
    public ServiceClient(){
        dao = (ClientDAO)FactoryDao.getDAO(TypesDAO.CLIENT);
    }

    /**
     * gets the client id by its email
     * @param email is the client's id
     * @return found id
     */
    public int getClientId(String email){
        return dao.findEntityByEmail(email).getId();
    }

    /**
     * validate the client data
     * @param name input name
     * @param surname input surname
     * @param telNumber input telephone number
     * @param email input email
     * @return true if the validation is passed
     */
    public boolean validateClient(String name, String surname, String telNumber, String email){
        return (Validator.isCorrectNameSurname(name) &&
                Validator.isCorrectNameSurname(surname)&&
                Validator.isCorrectTelNumber(telNumber)&&
                Validator.isCorrectEmail(email));
    }

    /**
     * gets client by its email or telephone
     * <p>
     *     if {@link Validator#isCorrectEmail(String)} is true the client will be found
     *     by email {@link ClientDAO#findEntityByEmail(String)}
     *     if {@link Validator#isCorrectTelNumber(String)} is true the client will be found by
     *     telephone number {@link ClientDAO#findEntityByTel(String)}
     *     else the client will be not found
     * </p>
     * @param emailOrTel is the string by which will be found
     * @return found client or null if there are not such client
     */
    public Client getClientByEmailOrTel(String emailOrTel){
        if(Validator.isCorrectEmail(emailOrTel)){
            return dao.findEntityByEmail(emailOrTel);
        }else if(Validator.isCorrectTelNumber(emailOrTel)){
            return dao.findEntityByTel(emailOrTel);
        }else{
            return null;
        }
    }

    /**
     * creates the client
     * <p>
     *     gets parameters from request to set client
     * </p>
     * @param name input name
     * @param surname input surname
     * @param telNumber input telephone number
     * @param email input email
     * @return result of creation of client if client with such email or telephone doesn't exist
     * true if client with such email or telephone exists
     */
    public boolean create(String name, String surname, String telNumber, String email){
        if(dao.findEntityByEmail(email) == null &&
                dao.findEntityByTel(telNumber) == null){
            Client client = new Client();
            client.setName(name);
            client.setSurname(surname);
            client.setTelNumber(telNumber);
            client.setEmail(email);
            return dao.create(client);
        }
        return true;
    }
}
