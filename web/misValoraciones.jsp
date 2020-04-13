<%-- 
    Document   : misValoraciones
    Created on : 07-abr-2020, 12:37:01
    Author     : Jesús
--%>

<%@page import="tecnoweb.classes.Filtro"%>
<%@page import="java.util.Collections"%>
<%@page import="tecnoweb.entity.Valoracion"%>
<%@page import="javax.ejb.EJB"%>
<%@page import="tecnoweb.dao.ValoracionFacade"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="tecnoweb.entity.Categoria"%>
<%@page import="tecnoweb.entity.Subcategoria"%>
<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mis Valoraciones</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estrellas.css">
        <link rel="stylesheet" href="css/estiloMenu.css">
    </head>
    <%
        Usuario usuario;
        
        usuario = (Usuario)session.getAttribute("usuario");
        if (usuario == null || usuario.getIsAdmin()) {
            response.sendRedirect("login.jsp");  
            return;
        }
    %>
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
        
        <div class="row">
        <!-- LATERAL IZQUIERDO: CATEGORIAS   -->
        <div class="col" id="latIzq">
        </div>
        
        <!-- CONTAINER -->
        <div  id="centro">
        <%
            
            List<Valoracion> misVals = usuario.getValoracionList();
            Filtro.ordenarValoracionesPorFecha(misVals);
            for(Valoracion v: misVals){
                Producto p = v.getProducto();
                Subcategoria subP = p.getIdSubcategoria();
                Categoria catP = subP.getIdCategoria();
                
                //Fecha subida
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
                String dateString = format.format(v.getFechaPublicacion());
                //Nota media
                int notaMedia = (p.getNotaMedia()).intValue()/2;
                int miNota = v.getNota().intValue()/2;
        %>
            
            <div id="cajaProducto" class="bg-light shadow" >
                <div id="tituloProducto" class="h3" >
                        <span class="ml-2"><%=p.getTitulo()%></span>
                        <span class="badge badge-danger"><%=catP.getNombreCategoria()%></span>  <!--Badge de cateogoria-->
                        <span class="badge badge-info"><%=subP.getNombreSubcategoria()%></span>  <!--Badge de subcategoria-->
                </div>
                <div id="cuerpoProducto" class="row text-center">
                    <img id="imagenProducto" src="<%=p.getFotoProducto()%>" class="col figure-img img-fluid rounded" alt="...">
                    <div id="descripcionProducto" class="col d-none d-md-block">
                            
                            <!-- PUNTUACION CON ESTRELLAS (MI VALORACION)-->
                            <form>
                              <label class="font-weight-bold">Mi Valoracion:</label>
                              <p disabled class="valoracionMedia" id="mediaProducto<%=p.getIdProducto()%>">
                                <input id="radio1" type="radio" name="estrellas<%=p.getIdProducto()%>" value="5"
                                     <%if(miNota==5){%>checked<%}%> disabled><!--
                                --><label for="radio1">★</label><!--
                                --><input id="radio2" type="radio" name="estrellas<%=p.getIdProducto()%>" value="4"
                                     <%if(miNota==4){%>checked<%}%> disabled><!--
                                --><label for="radio2">★</label><!--
                                --><input id="radio3" type="radio" name="estrellas<%=p.getIdProducto()%>" value="3"
                                     <%if(miNota==3){%>checked<%}%> disabled><!--
                                --><label for="radio3">★</label><!--
                                --><input id="radio4" type="radio" name="estrellas<%=p.getIdProducto()%>" value="2"
                                     <%if(miNota==2){%>checked<%}%> disabled><!--
                                --><label for="radio4">★</label><!--
                                --><input id="radio5" type="radio" name="estrellas<%=p.getIdProducto()%>" value="1"
                                     <%if(miNota==1){%>checked<%}%> disabled><!--
                                --><label for="radio5">★</label>
                              </p>
                              <small>(<%=v.getNota().intValue()%>)</small>
                            </form>
                            <label class="d-block font-weight-bold">Mi comentario: </label>
                            <p class="text-truncate"> <%=v.getComentario() %></p>
                            <span class="d-block text-secondary small">Fecha Publicaci&oacute;n: <%= dateString %> </span>
                    </div>
                    <div id="precioProducto" class="col">
                            <label class="font-weight-bold">Precio: </label>
                            <h5 class="font-weight-bold"> <%=p.getPrecio()%>€</h5>
                            <!-- PUNTUACION CON ESTRELLAS (Nota Media)-->
                            <form>
                              <label class="font-weight-bold">Media:</label>
                              <p disabled class="valoracionMedia" id="mediaProducto<%=p.getIdProducto()%>">
                                <input id="radio1" type="radio" name="estrellas<%=p.getIdProducto()%>" value="5"
                                     <%if(notaMedia==5){%>checked<%}%> disabled><!--
                                --><label for="radio1">★</label><!--
                                --><input id="radio2" type="radio" name="estrellas<%=p.getIdProducto()%>" value="4"
                                     <%if(notaMedia==4){%>checked<%}%> disabled><!--
                                --><label for="radio2">★</label><!--
                                --><input id="radio3" type="radio" name="estrellas<%=p.getIdProducto()%>" value="3"
                                     <%if(notaMedia==3){%>checked<%}%> disabled><!--
                                --><label for="radio3">★</label><!--
                                --><input id="radio4" type="radio" name="estrellas<%=p.getIdProducto()%>" value="2"
                                     <%if(notaMedia==2){%>checked<%}%> disabled><!--
                                --><label for="radio4">★</label><!--
                                --><input id="radio5" type="radio" name="estrellas<%=p.getIdProducto()%>" value="1"
                                     <%if(notaMedia==1){%>checked<%}%> disabled><!--
                                --><label for="radio5">★</label>
                              </p>
                              <small>(<%=p.getNotaMedia()%>)</small>
                            </form>
                            <label class="font-weight-bold">Vendedor:</label>
                        <%if(p.getIdUsuario()!=null){%>
                            <span><%=p.getIdUsuario().getNombre()%></span>
                        <%}else{%>
                            <span class="text-secondary font-italic"> Anónimo </span>
                        <%}%>
                    </div>
                    <div id="divMas" class="col">
                        <a id="verMas" class="btn btn-info" href="./CargarProducto?idProducto=<%=p.getIdProducto()%>"  role="button" >Ver más</a>
                    </div>
                </div>
            </div>
        <%
            }
        %>
        </div>
        
        <!--Lateral derecho-->
        <div class="col" id="latDer">
        </div>
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>        
    </body>
</html>
