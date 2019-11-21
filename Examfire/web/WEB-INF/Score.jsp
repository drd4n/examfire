<%-- 
    Document   : ScoreList
    Created on : Nov 15, 2019, 1:42:05 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Score</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        
        <sql:setDataSource var = "examfire" driver = "org.apache.derby.jdbc.ClientDriver"
         url = "jdbc:derby://localhost:1527/examfire"
         user = "examfire"  password = "examfire"/>
 
      <sql:query dataSource = "${examfire}" var = "result">
         SELECT * from SCORE;
      </sql:query>
 
      <table border = "1" width = "100%">
         <tr>
            <th>Exam ID</th>
            <th>Exam Title</th>
            <th>Score</th>
         </tr>
         
         <c:forEach var = "row" items = "${result.rows}">
            <tr>
               <td><c:out value = "${row.examid}"/></td>
               <td><c:out value = "${row.examtitle}"/></td>
               <td><c:out value = "${row.score}"/></td>
            </tr>
         </c:forEach>
      </table>
        
        ${scoreController.findByUseridAndExamid}
        <a href="/Examfire/Result?examid=${examid}">See Result</a>
    </body>
</html>
