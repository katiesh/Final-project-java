<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="message"/>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="http://localhost:8081/finalProject_war_exploded/assets/login-a.css">
    <link rel="shortcut icon" href="http://localhost:8081/finalProject_war_exploded/assets/img/logo.png" type="image/png">
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
                              <a class="nav-link" href="/finalProject_war_exploded/home?command=main_page"><fmt:message key="HOME" /><span class="sr-only">(current)</span></a>
                          </li>
                          <li class="nav-item">
                              <a class="nav-link" href="/finalProject_war_exploded/home?command=set_client"><fmt:message key="ORDER_STATUS" /></a>
                          </li>
                          <li class="nav-item">
                              <a class="nav-link" href="/finalProject_war_exploded/admin?command=applications"><fmt:message key="LOGIN_AS_ADMIN" /></a>
                          </li>
                          <li class="nav-item">
                              <form method="post">
                                  <button type="submit" class="btn btn-default btn-xs" name="lang" value="en_UK">ENG</button>
                              </form>
                          </li>
                          <li class="nav-item">
                              <form method="post">
                                  <button type="submit" class="btn btn-primary btn-xs" name="lang" value="ru_RU">РУС</button>
                              </form>
                          </li>
                      </ul>
                    </div>
                  </nav>
             
            </div>
  
            <h2 class="heading"><fmt:message key="LOGIN" /></h2>
            <form action="j_security_check" method="post">
                    <div class="form-group row">
                      <label class="col-sm-2 col-form-label"><fmt:message key="LOGIN" /></label>
                      <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputEmail3" name="j_username" placeholder=<fmt:message key="LOGIN" />>
                      </div>
                    </div>
                    <div class="form-group row">
                      <label for="inputPassword3" class="col-sm-2 col-form-label"><fmt:message key="PASSWORD" /></label>
                      <div class="col-sm-10">
                        <input type="password" class="form-control" id="inputPassword3" name="j_password" placeholder=<fmt:message key="PASSWORD" />>
                      </div>
                    </div>
                    <div class="form-group row">
                            <div class="col-sm-12">
                                <div class="text-center">
                              <button type="submit" class="btn btn-lg btn btn btn-warning button-d"><fmt:message key="SUBMIT" /></button>
                            </div>
                          </div>
                          </div>
                    </form>
  
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