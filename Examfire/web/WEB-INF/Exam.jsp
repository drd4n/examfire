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
        <h1>${Exam.getExamTitle()}</h1>
        <c:forEach items="Exam.getChoicesetList()" var="set">
            <div>
                <table>
                    <c:forEach items="set.getChoiceList()" var="choice">
                        <tr>
                            <td><input type="number" name="answer${choice.getChoiceid()}s${set.getChoicesetid()}"></td>
                            <td>${choice.getQuestion()}</td>
                        </tr>
                    </c:forEach>
                </table>
                //Question
                <table>
                    <c:forEach items="AnswerI" var="answer">
                        <tr>
                            <td>num</td>
                            <td>${answer.getAnswer()}</td>
                        </tr>
                    </c:forEach>
                </table>
                //Answer
            </div>
        </c:forEach>
        
    </body>
</html>
