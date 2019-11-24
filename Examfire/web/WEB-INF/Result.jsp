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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <h1>See the answer!</h1>
        <h2> ${examresult.getExamtitle()} </h2>
        <form action="Home" method="get">
            <c:forEach items="${examresult.getChoicesetList()}" var="set">
                <div class="row text-center">

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
  
                </div>
            </c:forEach>
            <input type="submit" value="Back to home">
        </form>
    </body>
</html>
