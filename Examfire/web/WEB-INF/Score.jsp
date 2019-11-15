<%-- 
    Document   : ScoreList
    Created on : Nov 15, 2019, 1:42:05 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Score</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <div class="row text-center">
            
        <table class="col-6 table table-striped">
            
            <thead class="thead-dark">
                <tr>
                    <th>Title</th>
                </tr>
                
            </thead>
              <c:forEach items="${exams}" var="exs">  
            <tr>
                
                    <td>${score.getExamScore()}</td>
                
            </tr>
            </c:forEach>
        </table>
            
            <table class="col-6 table table-striped">
            
            <thead class="thead-dark">
                <tr>
                    <th>Title</th>
                </tr>
                
            </thead>
              <c:forEach items="${exams}" var="exs">  
            <tr>
                
                    <td>${score.getExamScore()}</td>
                
            </tr> 
            </c:forEach>
        </table>
            </div>
    </body>
</html>
