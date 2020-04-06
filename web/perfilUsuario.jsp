<%-- 
    Document   : perfilUsuario
    Created on : 29-mar-2020, 18:13:48
    Author     : Ángel PezDeCristal
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        Usuario usr = (Usuario) session.getAttribute("usuario");
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
        String fechaNac = format.format(usr.getFechaNac());
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil de Usuario</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloPerfil.css">
    </head>
    <body>
        
        <!-- NAVBAR INICIO -->
        <nav class="navbar navbar-expand-lg navbar-light bg-dark" id="navbarInicio">
            <div class="navbar-brand"><h1 class="text-primary d-inline">Tecno </h1><h1 class="text-white d-inline"> Web</h1></div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <!--<li class="nav-item active">
                        <a class="nav-link text-white font-weight-bold h4 my-0 py-0 ml-5" href="#">Home<span class="sr-only">(current)</span></a>
                    </li>-->
                    <!--<li>
                        <label id="bienvenida" class="text-white font-italic h1 my-0 py-0 ml-5"> ☆¡Bienvenido a nuestra tienda <%=usr.getNombre()%>!☆ </label>
                    </li>-->
                    <!--<li class="nav-item">
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
                    </li>-->
                </ul>
                <div class="nav-item float-right float-sm-left pl-0">
                    <img class="d-inline-block align-middle" id="imagenPerfilNavBar" src="<%=usr.getFotoUsuario()%>" alt="Italian Trulli"/>
                    <div class="d-inline-block mx-2 p-2 text-white text-center align-middle" id="divPerfil">
                        <div class="dropdown">
                            <a class="dropdown my-0 py-0 h4 text-white" href="#" id="perfilDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <%=usr.getNombre()%>
                            </a>
                            <div class="dropdown-menu bg-secondary" id="dropdownNavbar" aria-labelledby="perfilDropdown">
                                <a class="dropdown-item text-white" href="./AccederPerfilUsuario">Perfil</a>
                                <a class="dropdown-item text-white" href="#">Mis Productos</a>
                                <a class="dropdown-item text-white" href="#">Mis Valoraciones</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item text-white" href="./CerrarSesion">Cerrar Sesi&oacute;n</a>
                            </div>
                        </div>
                        <!--<small class="mt-0 pt-0"><%if(usr.getIsAdmin()){%>Administrador<%}else{%>Usuario<%}%></small>-->
                        <a class="nav-link text-white my-0 py-0" href="./CerrarSesion">Cerrar Sesión</a>
                    </div>
                </div>
            </div>
         </nav>
        
        <!---------------------------------------------------------------------------------------------------------------------------------->
        
        <div class="row">
            
            <!-- MARGEN LATERAL IZQUIERDO 
            <div class="col text-white" id="margenIzdo"></div>-->

            <!-- PANEL DE DATOS E INFORMACIÓN -->
            <div class="container panel-primary" id="centro">
                <div class="panel-heading" id="titulo">Información del Usuario</div>
                <div class="panel-body" id="contenido">

                    <!-- PRIMER BLOQUE -->
                    <div class="row">
                        <!-- IMAGEN -->
                        <div id="imagen">
                            <img id="imagenPerfil" src="<%=usr.getFotoUsuario()%>" alt="Italian Trulli"/>
                        </div>
                        <!-- EMAIL & NOMBRE -->
                        <div id="primerBloque">
                            </br></br>
                            <label class="parametro">Correo Electr&oacute;nico</label></br>
                            <label id="dato"><%=usr.getEmailUsuario()%></label></br>
                            <label class="parametro">Nombre</label></br>
                            <%
                               if(usr.getNombre() == null || usr.getNombre().trim().equals("")){
                            %>
                                <label><em>No ha sido introducido</em></label>
                            <%
                               } else {
                            %>
                                <label id="dato"><%=usr.getNombre()%></label>
                            <%
                               }
                            %>
                                </br>

                                <label class="parametro">Apellidos</label></br>
                            <%
                               if(usr.getApellidos()== null || usr.getApellidos().trim().equals("")){
                            %>
                                <label id="dato"><em>No han sido introducido</em></label>
                            <%
                               } else {
                            %>
                                <label id="dato"><%=usr.getApellidos()%></label>
                            <%
                               }
                            %>
                            </br>
                            <label class="parametro">Fecha de Nacimiento</label></br>
                            <label id="dato"><%=fechaNac%></label></br>
                        </div>
                    </div>
                            
                    <!-- SEGUNDO BLOQUE -->
                    <div id="editButton">
                        <a href="./editarPerfilUsuario.jsp" class="btn btn-primary float-right" value="Editar Perfil">Editar Perfil</a>
                    </div>
                </div> <!-- Segundo Bloque -->
                        
            </div> <!-- contenido -->
                
                
                    
                    
                    
                </div>
            </div>

            <!-- MARGEN LATERAL DERECHO
            <div class="col text-white" id="margenDcho"></div>-->
            
        </div>
        
        <!---------------------------------------------------------------------------------------------------------------------------------->
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
