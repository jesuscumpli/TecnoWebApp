<%-- 
    Document   : registro
    Created on : 23-mar-2020, 13:23:27
    Author     : Jesús
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro Tecno Web</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
    </head>
    <body>
        <%
            String status = (String) request.getAttribute("status");
            if(status==null){
                status = "";
            }
        %>
        
        <!-- NAVBAR INICIO -->
        <nav class="navbar navbar-expand-lg navbar-light bg-dark" id="navbarInicio">
            <div class="navbar-brand"><h2 class="text-primary d-inline">Tecno </h2><h2 class="text-white d-inline"> Web</h2></div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link text-white" href="#">Home<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#" >Link</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Dropdown
                        </a>
                        <div class="dropdown-menu bg-secondary" id="dropdownNavbar" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item text-white" href="#">Action</a>
                            <a class="dropdown-item text-white" href="#">Another action</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item text-white" href="#">Something else here</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="./ListarUsuarios">Listar Usuarios</a>
                    </li>
                </ul>
            </div>
         </nav>
        
        <!----------------------------------------------->
        
        <div id="panel" class="border border-primary rounded">
            <h2>Registro</h2>
            <span class="small text-danger font-italic font-weight-bold"> <%=status%> </span>
            <form method="post" action="RegistrarUsuario" name="datos" accept-charset="UTF-8">
                <div class="form-group">
                    <label class="mb-0">*Email</label>
                    <input type="email" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Insertar Email" max="50" maxlength="50">
                    <small id="emailHelp" class="form-text text-white">Introduce su correo electr&oacute;nico aqu&iacute;</small>

                    <label class="mb-0">*Contraseña</label>
                    <input type="password" class="form-control" name="password1" placeholder="Password" max="50" maxlength="50">
                    <label class="mb-0">*Repita su contraseña</label>
                    <input type="password" class="form-control" name="password2" placeholder="Password" max="50" maxlength="50">
                </div>
                <hr class="bg-white">
                <div class="form-group">
                    <label class="mb-0">Nombre</label>
                    <input class="form-control" name="nombre" placeholder="Insertar Nombre" max="50" maxlength="50">
                    <label class="mb-0">Apellidos</label>
                    <input class="form-control" name="apellidos" placeholder="Insertar Apellidos" max="100" maxlength="100">
                    <label class="mb-0">*Fecha de Nacimiento</label>
                    <small class="form-text text-white m-0">Debe cumplir la mayoría de edad (18)</small>
                    <input type="date" class="form-control" name="fechaNac">
                </div>
                <hr class="bg-white">
                <label class="mb-0">Foto perfil</label>
                <!--<small class="form-text text-white m-0">Introduce aquí una foto de perfil si lo desea</small>
                <div class="file-upload-wrapper">
                    <input type="file" name="imagen" class="file-upload" />
                </div>-->
                <small class="form-text text-white m-0">Introduce aquí una url con tu foto de perfil</small>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3">URL:</span>
                  </div>
                  <input id="url" name="url" type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">
                    <div class="input-group-append">
                        <button type="button" class="btn btn-success" id="basic-addon2" onclick="cambiarImagenJS()">Ver</button>
                    </div>
                </div>
                <img id="imgVista" src="https://748073e22e8db794416a-cc51ef6b37841580002827d4d94d19b6.ssl.cf3.rackcdn.com/not-found.png" class="img-thumbnail rounded mx-auto d-block w-25 h-25">

                <br>
                <button type="submit" class="btn btn-primary">Registrarse</button>
            </form>
        </div>
        
        
        <script> 
            function cambiarImagenJS(){
                var url = document.getElementById("url").value;
                document.getElementById("imgVista").src = url;
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
