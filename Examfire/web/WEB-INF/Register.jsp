<%-- 
    Document   : Register
    Created on : Nov 13, 2019, 9:06:15 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Examfire Register</title>
    </head>
    <body>
        <h1>Register</h1>
        <h2>${message}</h2>
        <hr>
        <form action="/Examfire/Register" method="post">
            <label>Name</label><input type="text" name="userfullname"><br>
            <label>E-Mail</label><input type="email" name="email"><br>
            <label>Username</label><input type="text" name="username"><br>
            <label>Password</label><input type="password" name="password"><br>
            <label>Confirm Password</label><input type="password" name="cfpassword"><br>
            <input type="submit" value="Register!">
            
        </form>

    </body>
</html>
