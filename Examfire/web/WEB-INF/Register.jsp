<%-- 
    Document   : Register
    Created on : Nov 13, 2019, 9:06:15 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="shortcut icon" href="images/EF_Favicon.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Examfire Register</title>
    </head>
    <body class="bg-secondary">
        <h2>${message}</h2>
        <div class="text-center bg-dark justify-content-center" style="border-radius: 50px; margin: 80px 100px 0px 100px; padding: 50px 0px 50px 0px;">
            <div class="row">
                <div class="col-4"></div>
                <img src="images/EF_Logo.png" alt="examfire_logo" style="width: auto; height: 50px;">
            <h1 class="col-1 text-light mb-4">Register</h1>
            </div>
            <form class="form-group" action="/Examfire/Register" method="post">
                <div class="row">                
                    <label class="form-control-label text-light col-4 text-right">Name :</label>
                    <input class="form-control col-5" type="text" name="userfullname">
                </div>
                <div class="row">                
                    <label class="form-control-label text-light col-4 text-right mt-4">E-Mail :</label>
                    <input class="form-control col-5 mt-4" type="email" name="email">
                </div>
                <div class="row">                
                    <label class="form-control-label text-light col-4 text-right mt-4">Username :</label>
                    <input class="form-control col-5 mt-4" type="text" name="username">
                </div>
                <div class="row">                
                    <label class="form-control-label text-light col-4 text-right mt-4">Password :</label>
                    <input class="form-control col-5 mt-4" type="password" name="password">
                </div>
                <div class="row">                
                    <label class="form-control-label text-light col-4 text-right mt-4">Confirm Password :</label>
                    <input class="form-control col-5 mt-4" type="password" name="cfpassword">
                </div>
                <div class="row">            
                    <div class="col-4">
                    </div>
                    <input class="form-control btn btn-success col-5 mt-5" type="submit" value="Register!">
                </div>


            </form>
        </div>
    </body>
</html>
