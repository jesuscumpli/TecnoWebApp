<%-- 
    Document   : registroUsuarioAdmin
    Created on : 07-abr-2020, 0:02:57
    Author     : haylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        
        <div id="panel" class="border border-primary rounded">
            <h2>Registro de un nuevo usuario (Admin)</h2>
            <span class="small text-danger font-italic font-weight-bold"> <%=status%> </span>
            <form method="post" action="AnadirUsuario" name="datos" accept-charset="UTF-8">
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
                <!--isAdmin-->
                <label class="mb-0">¿Es administrador?</label>
                <br>
                    <input type="radio" name="isAdmin" value="Si">Si<br/>
                    <input type="radio" name="isAdmin" value="No">No<br/>
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
                <button type="submit" class="btn btn-primary">Registrar usuario</button>
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
