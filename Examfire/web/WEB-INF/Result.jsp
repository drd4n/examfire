<%-- 
    Document   : Result
    Created on : Nov 20, 2019, 11:17:07 PM
    Author     : Dan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" href="images/EF_Favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>'s Result</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <div style="width: 100%;" class="bg-dark text-light">
            <span style="font-size: 30px;">All answer of ${examresult.getExamtitle()}</span>
        </div>
            <div class="row text-center d-flex justify-content-center">
                <c:forEach items="${examresult.getChoicesetList()}" var="set">

                    <table class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>Question Left side</th>
                                <th>Right Answer</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${set.getChoiceList()}" var="answer" varStatus="num">
                                <tr>
                                    <td>${answer.getQuestion()}</td>
                                    <td>
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">${num.count}</span>
                                            <span class="ml-2"> ${answer.getAnswer()}</span>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </c:forEach>
            </div>
        <form action="Home" method="get">
            <input class="btn btn-primary" type="submit" value="Back to home">
        </form>
    </body>
</html>
