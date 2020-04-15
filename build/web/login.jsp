<%-- 
    Document   : login
    Created on : 23-mar-2020, 22:50:38
    Author     : Jesús
--%>

<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Inicio Sesion</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
    </head>
    <body>
        <%
        UsuarioDTO usuario;
        String status;
        
        // Si el usuario está dentro de la sesión quiere decir que ya hizo login
        // por lo que se le redirige a menu.jsp
        usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario != null) {
            response.sendRedirect("CargarMenu");  
            return;
        } 
                
        // Si se llama a esta JSP desde el servlet "Autenticar", se le enviará
        // el atributo "status" con el mensaje de error. 
        status = (String)request.getAttribute("status");
        if (status == null) {
            status = "";
        }                
        %>
        
        <!-- NAVBAR INICIO -->
        <nav class="navbar navbar-expand-lg navbar-light bg-dark" id="navbarInicio">
            <div class="navbar-brand"><h2 class="text-primary d-inline">Tecno </h2><h2 class="text-white d-inline"> Web</h2></div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse ml-5" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto mx-2">
                    <li class="nav-item active mx-2">
                        <a class="nav-link text-white h5 font-weight-bold" href="#">Inicio</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="registro.jsp" >Registro</a>
                    </li>
                </ul>
            </div>
         </nav>
        
        <!----------------------------------------------->
        
        <div id="panel" class="shadow rounded">
            <h2>Inicio Sesi&oacute;n</h2>
            <form method="post" action="InicioSesionServlet" name="datos" accept-charset="UTF-8">
                <div id="error"><%=status%></div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Enter email">
                </div>
                <div class="form-group">
                    <label>Contraseña</label>
                    <input type="password" class="form-control" name="password" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
            <div id="divRegistro">
                <small id="registroHelp" class="form-text text-white">¿No se ha registrado aún en nuestra web?</small>
                <a class="text-white font-weight-bold" href=".\registro.jsp">Registrese aquí</a>
            </div>
        </div>
        
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
