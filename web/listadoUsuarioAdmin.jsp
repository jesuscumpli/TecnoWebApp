<%-- 
    Document   : listadoUsuarioAdmin
    Created on : 27-mar-2020, 21:08:38
    Author     : Pacoyes
--%>

<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <html>
    <%
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario == null || !usuario.getIsAdmin()) {
            response.sendRedirect("login.jsp");  
            return;
        }
        List<UsuarioDTO> lista = (List) request.getAttribute("usuarios");
    %>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado Usuario Admin Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
    </head>
    <body>
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
        
        <h2 class="text-center bg-light">Listado Usuarios (vista Admin):</h2>

        <div id="divRegistro">
            <a  class="btn btn-primary text-black font-weight-bold" href=".\registroUsuarioAdmin.jsp">Añadir nuevo usuario</a>
        </div>
        
        <table class="table text-center" >
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Apellidos</th>
                    <th scope="col">Fecha Nacimiento</th>
                    <th scope="col">Administrador</th>
                    <th scope="col">Email</th>
                    <th scope="col">Foto</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
    <%
      for(UsuarioDTO u: lista){
    %>
                <tr>
                    <th scope="row"><%= u.getIdUsuario() %></td>
                    <td><%= u.getNombre() %></td>
                    <td><%= u.getApellidos() %></td>
                    <td><%= u.getFechaNac().toString() %></td>
                    <td><% if(u.getIsAdmin()){%>True<%}else{%>False<%}%></td>
                    <td><%= u.getEmailUsuario() %></td>
                    <td><img src="<%if(u.getFotoUsuario()!=null){%><%=u.getFotoUsuario()%><%}else{%>#<%}%>" height="25px" widtht="25px"></td>
                    <td><a class="btn btn-danger" href="BorrarUsuario?id=<%= u.getIdUsuario() %>">Borrar</a></td>   
                    <td><a class="btn btn-secondary" href="EditarUsuario?idUsuario=<%= u.getIdUsuario() %>">Editar</a></td>
                </tr>
    <%
       }
    %>
            </tbody>
        </table>    
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"  crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>

    </body>
</html>
