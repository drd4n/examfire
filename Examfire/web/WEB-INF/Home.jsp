<%-- 
    Document   : home
    Created on : Nov 2, 2019, 3:07:02 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Examfire Home</title>
         <script src="https://unpkg.com/react@16/umd/react.development.js" crossorigin></script>
         <script src="https://unpkg.com/react-dom@16/umd/react-dom.development.js" crossorigin></script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/Header.jsp" />
        <h1>${user.getUserFullName()}</h1>
    </body>
</html>
