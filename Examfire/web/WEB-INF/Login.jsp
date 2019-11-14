<%-- 
    Document   : Login
    Created on : Nov 2, 2019, 3:07:53 PM
    Author     : Dan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Examfire Login</h1>
        <h2>${message}</h2>
        <hr>
        <form class="form-group bg-dark" action="Login" method="post">
            <div class="form-group row">
                
                <label class="col-1 form-control-label text-light">Username : </label><input class="col-4 form-control" type="text" name="username">
            </div>
            <div class="form-group row">
                
                <label class=" col-1 form-control-label text-light">Password : </label><input class=" col-4 form-control" type="password" name="password">
            </div>
            
            <input class="form-control btn btn-secondary" type="submit" value="Login">
            <span class="text-light">You don't have an account? <a href="/Examfire/Register">Register Here!</a></span>
        </form>
    </body>
</html>
