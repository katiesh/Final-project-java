package service;

import model.dao.factory.AbstractDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class Service {

    public int getNumOfRows(AbstractDAO dao){
        return dao.getNumOfRows();
    }

    public List getEntitiesForCurrentPage(AbstractDAO dao, int currentPage, int recordsPerPage){
        return dao.findFromTo((currentPage-1)*recordsPerPage, recordsPerPage);
    }

    public void setCurrentPageRecordsPerPage(HttpServletRequest servletRequest){
        int currentPage;
        int recordsPerPage;
        if (servletRequest.getParameter("currentPage") == null) {
            currentPage = 1;
        } else {
            currentPage = Integer.valueOf(servletRequest.getParameter("currentPage"));
        }
        if (servletRequest.getParameter("currentPage") == null) {
            recordsPerPage = 3;
        } else {
            recordsPerPage = Integer.valueOf(servletRequest.getParameter("recordsPerPage"));
        }

        servletRequest.setAttribute("currentPage", currentPage);
        servletRequest.setAttribute("recordsPerPage", recordsPerPage);
    }

    public void setNumOfPages(int rows,HttpServletRequest servletRequest){
        //int rows = getNumOfRows(dao);
        int recordsPerPage = (Integer)(servletRequest.getAttribute("recordsPerPage"));

        int numOfPages = rows / recordsPerPage;

        if (rows % recordsPerPage > 0) {
            numOfPages++;
        }
        servletRequest.setAttribute("noOfPages", numOfPages);
    }

    public void forwardToPage(String path, HttpServletRequest servletRequest,
                              HttpServletResponse servletResponse) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(path);
        requestDispatcher.forward(servletRequest, servletResponse);
    }

    public abstract void closeConnections();

//    public int getNumOfRows(){
//
//    }
}
