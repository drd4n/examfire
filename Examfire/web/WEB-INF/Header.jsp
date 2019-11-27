<%-- 
    Document   : Header
    Created on : Nov 13, 2019, 9:31:03 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

        <link rel="shortcut icon" href="images/EF_Favicon.png">
        <link href="https://fonts.googleapis.com/css?family=Tinos&display=swap" rel="stylesheet">
        <style>
            body{
                font-family: 'Tinos', serif;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <link rel="shortcut icon" href="images/EF_Favicon.png">
        <nav style="background-image: url(images/EF_Favicon.png); background-size: 300px 300px; background-position: center; background-repeat: no-repeat;" class="navbar navbar-dark bg-dark row" style="height: 90px;">

            <a class="navbar-brand col-2" href="/Examfire/Home">
                <img src="images/EF_Logo.png" width="40" alt="Examfire_logo">
                <span style="font-size: 30px;">${user.getUserfullname()}</span> 
            </a>
            <h2 class="text-light col-3" style="margin-right: -65px;">EXAMFIRE!</h2>
            <div class="form-inline">
            <form action="EditPassword" method="get">
                <button class="btn btn-secondary text-light mr-4">Profile</button>
            </form>
            <form class="form-inline mr-3" action="Logout" method="post">
                <button class="btn btn-secondary text-light" type="sumbit">Logout</button>
            </form>
            </div>
        </nav>
            <div class="text-center bg-dark">
                <span class="text-light" style="font-size: 22px;">${message}
                </span>
            </div>
               
    </body>
</html>
