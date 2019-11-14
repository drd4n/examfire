<%-- 
    Document   : home
    Created on : Nov 2, 2019, 3:07:02 PM
    Author     : Dan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Examfire Home</title>
         
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <h1>Let's Practice!</h1>
        <c:forEach items="${exams}" var="exs">
            <p>${exs.getExamtitle()}</p><br>
        </c:forEach>
    </body>
</html>
