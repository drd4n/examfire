<%-- 
    Document   : Login
    Created on : Nov 2, 2019, 3:07:53 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Examfire Login</h1>
        <h2>${message}</h2>
        <hr>
        <form action="Login" method="post">
            <label>Username : </label><input type="text" name="username"><br>
            <label>Password : </label><input type="password" name="password"><br>
            <input type="submit" value="Login">
            <p>You don't have an account? <a href="/Examfire/Register">Register Here!</a></p>
        </form>
    </body>
</html>
