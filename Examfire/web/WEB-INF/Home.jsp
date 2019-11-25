<%-- 
    Document   : home
    Created on : Nov 2, 2019, 3:07:02 PM
    Author     : Dan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" href="images/EF_Favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>'s Home</title>


    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <h1>Let's Practice!</h1>
        <div class="row text-center">

            <table class="col-6 table table-striped">

                <thead class="thead-dark">
                    <tr>
                        <th>Title</th>
                       
                    </tr>

                </thead>
                <c:forEach items="${exams}" var="exs">  
                    <tr>

                        <td>
                            <div style="height: 40px; overflow:auto;">
                                <a href="/Examfire/Score?examid=${exs.getExamid()}"> ${exs.getExamtitle()}</a>
                            </div>
                        </td>
                        
                    </tr>
                </c:forEach>
            </table>
            <table class="col-6 table table-striped">

                <thead class="thead-dark">
                    <tr>
                        <th>Score</th>
                    </tr>

                </thead>
                <c:forEach items="${scores}" var="scores" varStatus="i">  
                    <tr>

                        <td>
                            <div style="height: 40px; overflow:auto;">
                                ${scores}
                            </div>
                        </td>

                    </tr>
                </c:forEach>
            </table>

        </div>
        
    </body>
</html>
