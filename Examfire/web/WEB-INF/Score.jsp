<%-- 
    Document   : ScoreList
    Created on : Nov 15, 2019, 1:42:05 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" href="images/EF_Favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${Exam.getExamtitle()}'s Score</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />

        <div class="row text-center">

            <table class="table table-striped">

                <thead class="thead-dark">

                    <tr>
                        <th>Title</th>
                        <th>Score</th>
                        <th>More Info</th>
                    </tr>
                </thead>
                <tr>
                    <td>${exam.getExamtitle()}</td>
                    <td>${score}</td>
                    <td><a href="/Examfire/Result?examid=${exam.getExamid()}" class="btn btn-primary active" role="button" aria-pressed="true">See Result</a></td>
                </tr>



            </table>

        </div>


    </body>
</html>
