<%-- 
    Document   : editarPerfilUsuario
    Created on : 30-mar-2020, 18:27:23
    Author     : DeuneB07
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        Usuario usr = (Usuario) session.getAttribute("usuario");
        if (usr == null) {
            response.sendRedirect("login.jsp");  
            return;
        } 
        String nombre, apellidos, email;
        
        if(usr.getNombre().trim().equals("")) nombre = "";
        else nombre = usr.getNombre();
        
        if(usr.getApellidos().trim().equals("")) apellidos = "";
        else apellidos = usr.getApellidos();

        email = usr.getEmailUsuario();
        
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        String fechaNac = format.format(usr.getFechaNac());
        
        String statusEmail = (String) request.getAttribute("statusEmail"); 
        String statusPwdOrig = (String) request.getAttribute("statusPwdOrig");
        String statusPwdNew = (String) request.getAttribute("statusPwdNew");
        String statusNombre = (String) request.getAttribute("statusNombre");
        String statusApellido = (String) request.getAttribute("statusApellido");
        String statusFechaNac = (String) request.getAttribute("statusFechaNac");
        if(statusEmail == null) statusEmail = "";
        if(statusPwdOrig == null) statusPwdOrig = "";
        if(statusPwdNew == null)statusPwdNew = "";
        if(statusNombre == null) statusNombre = "";
        if(statusApellido == null) statusApellido = "";
        if(statusFechaNac == null) statusFechaNac = "";

    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Perfil Usuario</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloModiPerfil.css">
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
                <%if(!usr.getIsAdmin()){%>
                    <li class="nav-item active mx-2">
                        <a class="nav-link text-white" href="menu.jsp">Home</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="listadoProducto.jsp" >Mis Productos</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="misValoraciones.jsp">Mis Valoraciones</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white h5 font-weight-bold" href="perfilUsuario.jsp">Mi Perfil</a>
                    </li>
                <%}else{%>
                    <li class="nav-item active mx-2">
                        <a class="nav-link text-white h5 font-weight-bold" href="menuAdmin.jsp">Home</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="./ListadoUsuarioAdmin" >Usuarios</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="FiltrarMenu">Productos</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="./ListadoCategoriasAdmin">Categorias</a>
                    </li>
                <%}%>
                </ul>
                <div class="nav-item float-right float-sm-left pl-0">
                    <img class="d-inline-block align-middle" id="imagenPerfilNavBar" src="<%=usr.getFotoUsuario()%>" alt="Italian Trulli"/>
                    <div class="d-inline-block mx-2 p-2 text-white text-center align-middle" id="divPerfil">
                        <div class="dropdown">
                            <a class="dropdown my-0 py-0 h4 text-white" href="#" id="perfilDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <%=usr.getNombre()%>
                            </a>
                            <div class="dropdown-menu bg-secondary" id="dropdownNavbar" aria-labelledby="perfilDropdown">
                                <a class="dropdown-item text-white" href="perfilUsuario.jsp">Perfil</a>
                            <%if(!usr.getIsAdmin()){%>
                                <a class="dropdown-item text-white" href="listadoProducto.jsp">Mis Productos</a>
                                <a class="dropdown-item text-white" href="misValoraciones.jsp">Mis Valoraciones</a>
                            <%}else{%>
                                <a class="dropdown-item text-white" href="./ListadoUsuarioAdmin">Usuarios</a>
                                <a class="dropdown-item text-white" href="FiltrarMenu">Productos</a>
                                <a class="dropdown-item text-white" href="./ListadoCategoriasAdmin">Categorias</a>
                            <%}%>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item text-white" href="./CerrarSesion">Cerrar Sesi&oacute;n</a>
                            </div>
                        </div>
                        <a class="nav-link text-white my-0 py-0" href="./CerrarSesion">Cerrar Sesión</a>
                    </div>
                </div>
            </div>
         </nav>
        
        <!---------------------------------------------------------------------------------------------------------------------------------->
        
        <div class="row">

            <!-- PANEL DE DATOS E INFORMACIÓN -->
            <div class="container panel-primary" id="centro">
                <div class="panel-heading" id="titulo">Información del Usuario</div>
                <div class="panel-body" id="contenido">

                    <form id="buttonAceptar" method="post" action="EditarPerfilUsuario" name="aceptarEditar" accept-charset="UTF-8">
                        <!-- PRIMER BLOQUE -->
                        <div class="row">
                            <!-- IMAGEN -->
                            <div id="imagen">
                                <img id="imagenPerfil" src="<%=usr.getFotoUsuario()%>" alt="Sin Imagen"/>
                                </br>
                                <small class="form-text text-white m-0">Introduce aquí una url con tu foto de perfil</small>
                                <div id="cambiarImagen" class="input-group mb-3">
                                  <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon3">URL:</span>
                                  </div>
                                  <input id="url" name="photoURL" type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">
                                    <div class="input-group-append">
                                        <button type="button" class="btn btn-success" id="basic-addon2" onclick="cambiarImagenJS()">Ver</button>
                                    </div>
                                </div>
                            </div>
                            <!-- EMAIL & NOMBRE -->
                            <div id="primerBloque">
                                </br></br>
                                <label class="parametro">Correo Electr&oacute;nico</label>
                                <span class="small text-danger font-italic font-weight-bold"> <%=statusEmail%> </span></br>
                                <input id="inpData" type="email" class="form-control" name="email" aria-describedby="emailHelp" placeholder="Introduzca aquí su email..." maxlength=50 value="<%=email%>">
                                </br>
                                
                                <label class="parametro">Nombre</label>
                                <span class="small text-danger font-italic font-weight-bold"> <%=statusNombre%> </span></br>
                                <input id="inpData" type="nombre" class="form-control" name="nombre" aria-describedby="nombreHelp" placeholder="Introduzca aquí su nombre..." maxlength=50 value="<%=nombre%>">
                                </br>

                                <label class="parametro">Apellidos</label>
                                <span class="small text-danger font-italic font-weight-bold"> <%=statusApellido%></br>
                                <input id="inpData" type="apellido" class="form-control" name="apellidos" aria-describedby="apellidosHelp" placeholder="Introduzca aquí sus apellidos..." maxlength=100 value="<%=apellidos%>">
                                </br>
                                <label class="parametro">Fecha de Nacimiento</label>
                                <input type="date" class="form-control" name="fechaNac" value="<%=fechaNac%>"></br>
                                <span class="small text-danger font-italic font-weight-bold"> <%=statusFechaNac%></span>
                                </br>
                                
                            </div>
                        </div>
                        <!-- SEGUNDO BLOQUE -->
                        <div class="row" id="segundoBloque">
                            <div id="segundoIzda">
                                <label><em>Solo rellenar si desea cambiar la contrase&ntilde;a</em></label></br>
                                <label class="parametro">Contrase&ntilde;a Actual</label>
                                <span class="small text-danger font-italic font-weight-bold"> <%=statusPwdOrig%></span>
                                <input id="inpPwd" type="password" class="form-control" name="passwordOrig" placeholder="Introduzca su Contrase&ntilde;a actual..." maxlength=50>
                                <label class="parametro">Nueva Contrase&ntilde;a</label>
                                <span class="small text-danger font-italic font-weight-bold"> <%=statusPwdNew%></span>
                                <input id="inpPwd" type="password" class="form-control" name="passwordNew" placeholder="Introduzca su nueva Contrase&ntilde;a..." maxlength=50>
                                <label class="parametro">Repita su Nueva Contrase&ntilde;a</label></br>
                                <input id="inpPwd" type="password" class="form-control" name="passwordNewTwo" placeholder="Repita su Contrase&ntilde;a..." maxlength=50>
                                </br>
                            </div>
                            
                            <div id="segundaCentro"></div>
                            <div id="segundoDcha">
                                <button type="submit" class="btn btn-primary btn-sm btn-success float-right">Aceptar Cambios</button>
                                </br>
                                <div id="buttonBaja">
                                    <a href="./DarDeBajaUsuario" class="btn btn-primary btn-sm btn-danger float-right">Darse de Baja</a>
                                    </br>
                                </div>
                                <div id="buttonCancelar">
                                    <a href="./perfilUsuario.jsp" class="btn btn-primary btn-sm btn-dark float-right" value="Cancelar Perfil">Cancelar Cambios</a>
                                    </br>
                                </div>
                            </div>
                        </div> <!-- Segundo Bloque -->
                        
                    </form>           
                </div> <!-- contenido -->
                </div>
            </div>

            <!-- MARGEN LATERAL DERECHO
            <div class="col text-white" id="margenDcho"></div>-->
            
        </div>
        
        <!---------------------------------------------------------------------------------------------------------------------------------->
        <script> 
            function cambiarImagenJS(){
                var url = document.getElementById("url").value;
                if(url === null || url === "") {
                    document.getElementById("imagenPerfil").src = '<%= usr.getFotoUsuario() %>';
                } else {
                    document.getElementById("imagenPerfil").src = url;
                }
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>