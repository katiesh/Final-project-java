package controller.command.implamantation;

import controller.command.ICommand;
import model.entity.Request;
import service.ServiceRequest;
import service.ServiceRoomBooking;
import util.ForwardPagesPaths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Rooms implements ICommand {

    private ServiceRequest serviceRequest;
    private ServiceRoomBooking serviceRoomBooking;

    public Rooms() {
        serviceRequest = new ServiceRequest();
        serviceRoomBooking = new ServiceRoomBooking();
    }

    @Override
    public void execute(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        try{
            int id = Integer.valueOf(servletRequest.getParameter("requestId"));
            Request requestItem = serviceRequest.getRequestById(id);
            servletRequest.setAttribute("requestItem", requestItem);
            String checkBoxValue = servletRequest.getParameter("only_date");
            if (checkBoxValue!=null && checkBoxValue.equals("true")) {
                serviceRoomBooking.roomsByBookingsDatePagination(servletRequest, requestItem.getDateFrom(), requestItem.getDateTo());
            }else{
                serviceRoomBooking.roomsByAllRequiredParamsPagination(requestItem, servletRequest);
            }
            serviceRequest.forwardToPage(ForwardPagesPaths.ROOMS.toString(),
                    servletRequest, servletResponse);
        }finally {
            serviceRequest.closeConnections();
            serviceRoomBooking.closeConnections();
        }

    }
}
