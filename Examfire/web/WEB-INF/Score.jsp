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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Score</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />

        <div class="row text-center">
            
            <form action='score' method="post">


                <table class="col-6 table table-striped">

                    <thead class="thead-dark">

                        <tr>
                            <th>Title</th>
                            <th>Exam ID</th>
                            <th>Score</th>
                            <th>More Info</th>
                        </tr>

                        <tr>
                            <td>${Exam.getExamtitle()}</td>
                            <td>${Exam.getExamid()}</td>
                            <td>${score}</td>

                            <td><a href="/Examfire/Result?examid=${examid}">See Result</a></td>
                        </tr>

                    </thead>

                </table>


            </form>
                        
        </div>
                        
                        
    </body>
</html>
