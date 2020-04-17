<%-- 
    Document   : registroUsuarioAdmin
    Created on : 07-abr-2020, 0:02:57
    Author     : haylo
--%>

<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="tecnoweb.entity.Usuario"%>
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
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
            if (usuario == null || !usuario.getIsAdmin()) {
                response.sendRedirect("login.jsp");  
                return;
            }
            String status = (String) request.getAttribute("status");
            if(status==null){
                status = "";
            }
        %>
        
        <!-- NAVBAR INICIO -->
        <nav class="navbar navbar-expand-lg navbar-light bg-dark" id="navbarInicio">
            <div class="navbar-brand"><h1 class="text-primary d-inline">Tecno </h1><h1 class="text-white d-inline"> Web</h1></div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse ml-5" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto mx-2">
                    <li class="nav-item active mx-2">
                        <a class="nav-link text-white" href="menuAdmin.jsp">Home</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white h5 font-weight-bold" href="./ListadoUsuarioAdmin" >Usuarios</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="FiltrarMenu">Productos</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="./ListadoCategoriasAdmin">Categorias</a>
                    </li>
                </ul>
                <div class="nav-item float-right float-sm-left pl-0">
                    <img class="d-inline-block align-middle" id="imagenPerfilNavBar" src="<%=usuario.getFotoUsuario()%>" alt="Italian Trulli"/>
                    <div class="d-inline-block mx-2 p-2 text-white text-center align-middle" id="divPerfil">
                        <div class="dropdown">
                            <a class="dropdown my-0 py-0 h4 text-white" href="#" id="perfilDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <%=usuario.getNombre()%>
                            </a>
                            <div class="dropdown-menu bg-secondary" id="dropdownNavbar" aria-labelledby="perfilDropdown">
                                <a class="dropdown-item text-white" href="perfilUsuario.jsp">Perfil</a>
                                <a class="dropdown-item text-white" href="./ListadoUsuarioAdmin">Usuarios</a>
                                <a class="dropdown-item text-white" href="FiltrarMenu">Productos</a>
                                <a class="dropdown-item text-white" href="./ListadoCategoriasAdmin">Categorias</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item text-white" href="./CerrarSesion">Cerrar Sesi&oacute;n</a>
                            </div>
                        </div>
                        <a class="nav-link text-white my-0 py-0" href="./CerrarSesion">Cerrar Sesión</a>
                    </div>
                </div>
            </div>
         </nav>
        
        <!----------------------------------------------->
        
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
                if(url===null || url===""){
                    document.getElementById("imgVista").src = 'https://748073e22e8db794416a-cc51ef6b37841580002827d4d94d19b6.ssl.cf3.rackcdn.com/not-found.png';
                } else{
                    document.getElementById("imgVista").src = url;
                }
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
