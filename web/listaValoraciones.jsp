<%-- 
    Document   : listaValoraciones
    Created on : 13-abr-2020, 1:22:42
    Author     : alvar
--%>

<%@page import="tecnoweb.dto.ValoracionDTO"%>
<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page import="tecnoweb.entity.Valoracion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Valoraciones</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloValoracion.css">
    </head>
    <body>
        <%
            UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
            if (usuario == null || usuario.getIsAdmin()) {
                response.sendRedirect("login.jsp");  
                return;
            }
            List<ValoracionDTO> listaValoraciones = (List) request.getAttribute("listaValoraciones");
            if (listaValoraciones != null && !listaValoraciones.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
            for(ValoracionDTO v: listaValoraciones){
                UsuarioDTO u = v.getUsuario() ;
                String coment = v.getComentario();
                String fechaPublicacion = format.format(v.getFechaPublicacion());
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
                        <a class="nav-link text-white" href="FiltrarMenu">Home</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="listadoProducto.jsp" >Mis Productos</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white h5 font-weight-bold" href="#">Mis Valoraciones</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="perfilUsuario.jsp">Mi Perfil</a>
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
                                <a class="dropdown-item text-white" href="listadoProducto.jsp">Mis Productos</a>
                                <a class="dropdown-item text-white" href="#">Mis Valoraciones</a>
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
        
        <div id="cajaValoracion" class="badge">
            <div id="Usuario" class="h3" > 
                <span class="ml-2"><%=u.getNombre()+" "+u.getApellidos() %></span>
            </div>
            <div id="cuerpoValoracion" class="row text-center">
                <div id="comentarioProducto" class="col">
                    <label class="font-weight-bold">Descripción:</label>
                    <p> <%=coment %></p>
                </div>
                <div id="notaProducto" class="col">
                    <label class="font-weight-bold">Nota: </label>
                    <h5 class="font-weight-bold"> <%= v.getNota().intValue() %></h5>
                    <br>
                    <label class="font-weight-bold">Fecha de publicación:</label> 
                    <h5 class="font-weight-bold"> <%= fechaPublicacion %></h5>
                </div>
            </div>
        </div>
        
        <%
            }
        }  
        else {
        %>
            <h1> Aun no hay valoraciones para tu producto :C </h1>
        <%
        }
        %>
            
        
    </body>
</html>
