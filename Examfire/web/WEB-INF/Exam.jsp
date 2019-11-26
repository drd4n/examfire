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
        <title>'s Exam</title>
        <link rel="shortcut icon" href="images/EF_Favicon.png">
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />

        <div class="text-center bg-dark mb-0">
            <span class="text-light"><h1>${Exam.getExamtitle()}</h1></span>
        </div>

        <form action="ExamServlet" method="post">
            <c:forEach items="${Exam.getChoicesetList()}" var="set">
                <div class="text-left bg-dark pl-2 mt-0">
                    <span class="text-light pl-5"><h2>${set.getTitle()}</h2></span>
                </div>
                <div class="row text-center">
                    <table class="col-6 table table-striped">
                        <c:forEach items="${set.getShuffleChoice()}" var="choice">
                            <tr>
                                <td><input type="number" name="${choice.getChoiceid()}" min="1" max="4" required>. ${choice.getQuestion()}</td>
                            </tr>
                        </c:forEach>
                    </table>



                    <table class="col-6 table table-striped">
                        <c:forEach items="${set.getChoiceList()}" var="answer" varStatus="num">
                            <tr>
                                <td style="height: 55px;">${num.count}. ${answer.getAnswer()}</td>

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
