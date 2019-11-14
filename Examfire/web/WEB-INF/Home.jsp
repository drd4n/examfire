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
        <table class="table table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>Title</th>
                    <th></th>
                    <th></th>
                    <th>Score</th>
                </tr>
            </thead>
            <c:forEach items="${exams}" var="exs">
                <tr>
                    <td>${exs.getExamtitle()}</td>
                    <td></td>
                    <td></td>
                    <td>Score</td>
                </tr>
        </c:forEach>
            </table>
        <h1>Your exams</h1>
        <table class="table table-striped">
            <thead class="thead-dark">
                <tr>
                    <th>Title</th>
                    <th></th>
                    <th></th>
                    <th>Score</th>
                </tr>
            </thead>
            <c:forEach items="${urexs}" var="urexs">
                <tr>
                    <td>${urexs.getExamtitle()}</td>
                    <td></td>
                    <td></td>
                    <td>Score</td>
                </tr>
        </c:forEach>
            </table>
    </body>
</html>
