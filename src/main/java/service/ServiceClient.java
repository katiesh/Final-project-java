package service;

import model.dao.factory.ClientDAO;
import model.dao.factory.FactoryDao;
import model.entity.Client;
import util.Validator;

import javax.servlet.http.HttpServletRequest;

public class ServiceClient extends Service {
    private ClientDAO dao = (ClientDAO)FactoryDao.createDAO("client");


    public int getClientId(String email){
        return dao.findEntityByEmail(email).getId();
    }

    public boolean validateClient(HttpServletRequest request){
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

    public boolean create(HttpServletRequest servletRequest){
        Client client = new Client();
        client.setName(servletRequest.getParameter("name"));
        client.setSurname(servletRequest.getParameter("surname"));
        client.setTelNumber(servletRequest.getParameter("tel"));
        client.setEmail(servletRequest.getParameter("email"));
        if(dao.findEntityByEmail(client.getEmail()) == null &&
                dao.findEntityByTel(client.getTelNumber()) == null){
            return dao.create(client);
        }
        return true;
    }

    @Override
    public void closeConnections() {
        dao.close();
    }
}
