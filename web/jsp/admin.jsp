<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="message"/>
<html lang="ru">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="http://localhost:8081/finalProject_war_exploded/assets/style.css">
    <link rel="shortcut icon" href="http://localhost:8081/finalProject_war_exploded/assets/img/logo.png" type="image/png">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
    <title>Hotel "Space"</title>
  </head>
  <body>
   
    <div class="site-wrapper">

        <div class="site-wrapper-inner">
  
          <div class="container">
  
            
              <div class="container inner  ">
                <nav class="navbar navbar-expand-lg navbar-dark">
                    <a class="navbar-brand" href="/finalProject_war_exploded/home?command=main_page">
                       <img src="http://localhost:8081/finalProject_war_exploded/assets/img/logo.png" height="60px" width="60px" alt="hotel logo">
                        <strong>Hotel "Space"</strong>
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                      <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                      <ul class="navbar-nav ml-auto">
                          <li class="nav-item active">
                              <a class="nav-link" href="?command=applications"><fmt:message key="VIEW_APLICATIONS" /><span class="sr-only">(current)</span></a>
                          </li>
                          <li class="nav-item">
                              <a class="nav-link" href="?command=bookings"><fmt:message key="VIEW_BOOKINGS" /></a>
                          </li>
                          <li class="nav-item">
                              <form method="post">
                                  <button type="submit" class="btn btn-default btn-xs" name="lang" value="en_UK">ENG</button>
                                  <input type="hidden" name = "new_appl" value="${param.new_appl}">
                              </form>
                          </li>
                          <li class="nav-item">
                              <form method="post">
                                  <button type="submit" class="btn btn-primary btn-xs" name="lang" value="ru_RU">РУС</button>
                                  <input type="hidden" name = "new_appl" value="${param.new_appl}">
                              </form>
                          </li>
                      </ul>
                    </div>
                  </nav>
             
            </div>
  
           <!--Start of Content part-->
           <div class="container">
                <div class="row">
                  <div class="col">
                       
                  </div>
                  <div class="col-sm-12">
                      <div class="text-right">
                          <form action="home?command=applications" method="post">
                        <div class="form-check form-check-inline">
                            <c:choose>
                            <c:when test="${not empty param.new_appl and param.new_appl eq 'true'}">
                                <input class="form-check-input" type="checkbox" name="new_appl" id="inlineCheckbox1" value="true" checked>
                            </c:when>
                            <c:otherwise>
                                <input class="form-check-input" type="checkbox" name="new_appl" id="inlineCheckbox1" value="true">
                            </c:otherwise>
                            </c:choose>
                                <label class="form-check-label" for="inlineCheckbox1"><fmt:message key="NEW_APLICATIONS" /></label>
                              </div>
                          <div class="form-group row">
                              <div class="col-sm-12">
                                      <button type="submit" class="btn btn-default btn-xs"><fmt:message key="SUBMIT"/></button>
                              </div>
                          </div>
                          </form>
                            </div>

                  </div>
                </div>
           </div>
           
    
    <!--Start-->
    <c:forEach items="${requests}" var="requestItem">
        <h2 class="heading" name = "requestId"><fmt:message key="APLICATION" /> №${requestItem.id}</h2>
        <c:if test="${nothingFound eq 'true'}">
           <h3><fmt:message key="NOTHING_FOUND"/></h3>
        </c:if>
    <table class="table table-hover table-bordered">
            <thead>
             
            </thead>
            <tbody>
             
              <tr>
                    <th  scope="row"><fmt:message key="CLASS_OF_THE_ROOM" /></th>
                    <td>${requestItem.classOfRoom}</td>
                    
              </tr>
              <tr>
                    <th scope="row"><fmt:message key="NUM_OF_PLACES" /></th>
                    <td>${requestItem.numOfPlaces}</td>
                    
                  </tr>
              <tr>
                <th scope="row"><fmt:message key="DATE_FROM" /></th>
                <td>${requestItem.dateFrom}</td>

            </tr> 
            <tr>
                    <th scope="row"><fmt:message key="DATE_TO" /></th>
                    <td>${requestItem.dateTo}</td>
                    
                  </tr>  
             <tr>
               <th scope="row"><fmt:message key="STATUS"/></th>
                <td class="in-work"> ${requestItem.status}</td>
                        
              </tr>      
            </tbody>
          </table>
        <c:if test="${requestItem.status ne 'processed'}">
          <div class="form-group row">
                <div class="col-sm-12">
                    <div class="text-right">
                  <a href="?command=rooms&requestId=${requestItem.id}"> <button type="submit" class="btn btn-lg btn btn btn-warning button-d"><fmt:message key="PICK_OPTIONS" /></button></a>
                </div>
              </div>
              </div>
        </c:if>
    </c:forEach>
         <!--End-->     
         <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center pagination-lg">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="?command=applications&new_appl=${param.new_appl}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">Previous</a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="page-item active"><a class="page-link">
                                        ${i} <span class="sr-only">(current)</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="?command=applications&new_appl=${param.new_appl}&recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="?command=applications&new_appl=${param.new_appl}&recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
                        </li>
                    </c:if>
                </ul>
              </nav>
         <footer>

         </footer>
          </div>
  
        </div>
  
      </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>