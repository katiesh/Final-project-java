package service;

import model.dao.factory.ClientDAO;
import model.dao.factory.FactoryDao;
import model.entity.Client;
import util.Validator;

import javax.servlet.http.HttpServletRequest;

public class ServiceClient {
    private ClientDAO dao = (ClientDAO)FactoryDao.createDAO("client");


    public int getClientId(Client client){
        if(dao.findEntityByEmail(client.getEmail())==null &&
                dao.findEntityByTel(client.getTelNumber())==null){
            dao.create(client);
        }
        return dao.findEntity(client).getId();
    }

    public boolean vaildateClient(HttpServletRequest request){
        return (Validator.isCorrectNameSurname(request.getParameter("name")) &&
                Validator.isCorrectNameSurname(request.getParameter("surname"))&&
                Validator.isCorrectTelNumber(request.getParameter("tel"))&&
                Validator.isCorrectEmail(request.getParameter("email")));
    }

    public Client getClientByEmailOrTel(String emailOrTel){
        if(Validator.isCorrectEmail(emailOrTel)){
            return dao.findEntityByEmail(emailOrTel);
        }else if(Validator.isCorrectTelNumber(emailOrTel)){
            return dao.findEntityByTel(emailOrTel);
        }else{
            return null;
        }
    }

}
