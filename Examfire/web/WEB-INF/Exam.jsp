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

        <div class="text-center mx-auto bg-dark mb-0">
            <span class="text-light"><h1>${Exam.getExamtitle()}</h1></span>
        </div>
        <div class="form-group">
            <form action="ExamServlet" method="post">
                <c:forEach items="${Exam.getChoicesetList()}" var="set">
                    <div class="text-left bg-dark pt-3 pl-2 pb-2 pr-2">
                        <span class="text-light"><h3>${set.getTitle()}</h3></span>
                    </div>
                    <div class="form-row text-center">
                        <table class="col-6 table table-striped">
                            <c:forEach items="${set.getShuffleChoice()}" var="choice">
                                <tr>
                                    <td class="d-flex justify-content-center">
                                        <div class="col-4 input-group-prepend">
                                            <input class="col-3 form-control" type="number" name="${choice.getChoiceid()}" min="1" max="4" required>
                                            <label class="input-group-text"> ${choice.getQuestion()}</label>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>



                        <table class="col-6 table table-striped">
                            <c:forEach items="${set.getChoiceList()}" var="answer" varStatus="num">
                                <tr>
                                    <td class="d-flex justify-content-center">
                                        <div class=" input-group-prepend">
                                            <span class="input-group-text">${num.count}</span>
                                            <span class="mt-2">${answer.getAnswer()}</span>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>


                    </div>
                </c:forEach>
                <input value="${Exam.getExamid()}" hidden="true">
                <input class="btn btn-success" type="submit" value="Send Answer">
            </form>
        </div>

    </body>
</html>
