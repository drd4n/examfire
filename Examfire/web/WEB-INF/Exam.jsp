<%-- 
    Document   : Exam
    Created on : Nov 11, 2019, 11:21:57 PM
    Author     : ZolyKana
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <h1>${Exam.getExamtitle()}</h1>
        
        <form action="ExamServlet" method="post">
            <c:forEach items="${Exam.getChoicesetList()}" var="set">
                <div class="row text-center">
                    <table class="col-6 table table-striped">
                        <c:forEach items="${set.getChoiceList()}" var="choice">
                            <tr>
                                <td><input type="number" name="${choice.getChoiceid()}" min="1" max="4" required>. ${choice.getQuestion()}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    
                    
                    
                    <table class="col-6 table table-striped">
                        <c:forEach items="${set.getShuffleChoice()}" var="answer" varStatus="num">
                            <tr>
                                <td>${num.count}. ${answer.getAnswer()}</td>
                                
                            </tr>
                        </c:forEach>
                    </table>
                    
                    
                </div>
            </c:forEach>
            <input value="${Exam.getExamid()}" hidden="true">
            <input type="submit" value="Send Answer">
        </form>
        
    </body>
</html>
