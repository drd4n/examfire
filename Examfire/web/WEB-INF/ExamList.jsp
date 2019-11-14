<%-- 
    Document   : ExamList
    Created on : Nov 11, 2019, 11:21:44 PM
    Author     : ZolyKana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <c:choose>
            <c:when test="${yourexams!=null}">
                Your Exams
                <table>
                    <c:forEach items="${yourexams}" var="e">
                        <form>
                            <tr>
                            <td>${e.getExamTitle()}</td>
                            <td><input hidden="true" name="viewid" value="${e.getExamid()}"></td>
                            <td><input type="submit"></td>
                        </tr>
                        </form>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
                <c:choose>
            <c:when test="${exams!=null}">
                Exam
                <table>
                    <c:forEach items="${exams}" var="e">
                        <form>
                            <tr>
                            <td>${e.getExamTitle()}</td>
                            <td><input hidden="true" name="doid" value="${e.getExamid()}"></td>
                            <td><input type="submit"></td>
                        </tr>
                        </form>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
    </body>
</html>
