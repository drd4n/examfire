<%-- 
    Document   : CreateExam
    Created on : Nov 11, 2019, 11:20:58 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Your Exam</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <h1>Create Your Own Exam!</h1>
        <hr>
        <form action="AddExam" method="post">
            <input type="text" name="choicesettitle" placeholder="Main Quest"><br>
            <input type="text" name="question1" placeholder="question1"><input type="text" name="answer1" placeholder="answer1"><br>
            <input type="text" name="question1" placeholder="question1"><input type="text" name="answer1" placeholder="answer1"><br>
            <input type="text" name="question1" placeholder="question1"><input type="text" name="answer1" placeholder="answer1"><br>
            <input type="text" name="question1" placeholder="question1"><input type="text" name="answer1" placeholder="answer1"><br>
           <input type="submit">
        </form>
    </body>
</html>
