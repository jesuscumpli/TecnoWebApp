<%-- 
    Document   : menu
    Created on : 23-mar-2020, 18:25:23
    Author     : Jesús
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="tecnoweb.entity.Subcategoria"%>
<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Categoria"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloMenu.css">
        <link rel="stylesheet" href="css/estrellas.css">
    </head>
    <%
        Usuario usuario;
        String status;
        
        // Si el usuario está dentro de la sesión quiere decir que ya hizo login
        // por lo que se le redirige a menu.jsp
        usuario = (Usuario)session.getAttribute("usuario");
        Subcategoria subcatSelected = (Subcategoria) session.getAttribute("subcatSelected");
        Categoria catSelected = (Categoria) session.getAttribute("catSelected");
        if (usuario == null) {
            response.sendRedirect("login.jsp");  
            return;
        }
        String orden = (String) session.getAttribute("orden");
        List<Producto> productos = (List<Producto>)session.getAttribute("productos");
        List<Categoria> categorias = (List<Categoria>)session.getAttribute("categorias");
    %>
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
                        <label id="bienvenida" class="text-white font-italic h1 my-0 py-0 ml-5"> ☆¡Bienvenido a nuestra tienda <%=usuario.getNombre()%>!☆ </label>
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
                    <img class="d-inline-block align-middle" id="imagenPerfil" src="<%=usuario.getFotoUsuario()%>" alt="Italian Trulli"/>
                    <div class="d-inline-block mx-2 p-2 text-white text-center align-middle" id="divPerfil">
                        <div class="dropdown">
                            <a class="dropdown my-0 py-0 h4 text-white" href="#" id="perfilDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <%=usuario.getNombre()%>
                            </a>
                            <div class="dropdown-menu bg-secondary" id="dropdownNavbar" aria-labelledby="perfilDropdown">
                                <a class="dropdown-item text-white" href="./AccederPerfilUsuario">Perfil</a>
                                <a class="dropdown-item text-white" href="#">Mis Productos</a>
                                <a class="dropdown-item text-white" href="#">Mis Valoraciones</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item text-white" href="./CerrarSesion">Cerrar Sesi&oacute;n</a>
                            </div>
                        </div>
                        <!--<small class="mt-0 pt-0"><%if(usuario.getIsAdmin()){%>Administrador<%}else{%>Usuario<%}%></small>-->
                        <a class="nav-link text-white my-0 py-0" href="./CerrarSesion">Cerrar Sesión</a>
                    </div>
                </div>
            </div>
         </nav>
        
        <!----------------------------------------------->
        
        <div class="row">

        <!-- LATERAL IZQUIERDO: CATEGORIAS   -->
            <div class="col text-white d-none d-lg-block" id="latIzq">
                <h2 class="font-weight-bold text-center">Categorias</h2>
                <ul class="listaCat">
        <%
                for(Categoria cat: categorias){
        %>
                    <li class="font-weight-bold">
                        <a class="text-white" data-toggle="collapse" href="#collapseCategoria<%=cat.getIdCategoria()%>" aria-expanded="false" aria-controls="collapseCategoria">
                            <%=cat.getNombreCategoria() %>
                        </a>
                        <ul class="listaSub collapse" id="collapseCategoria<%=cat.getIdCategoria()%>">
                        <%
                            for(Subcategoria subcat: cat.getSubcategoriaList()){
                        %>
                            <li class="font-italic font-weight-light">
                                <a class="text-white" href="./FiltrarMenu?subcatSelected=<%=subcat.getIdSubcategoria()%>">
                                    <%=subcat.getNombreSubcategoria()%>(<%=subcat.getProductoList().size()%>)
                                </a>
                            </li>
                        <%
                            }
                        %>    
                        </ul>    
                    </li>
        <%
                }
        %>    
                </ul>
            </div>
        
        <!-- CONTAINER -->
            <div id="centro">
                
            <!--BUSCADOR / FILTRO -->    
            <nav id="navbarBuscador" class="navbar navbar-expand-lg navbar-light shadow">
                  <form class="form-inline my-2 my-lg-0 w-100" action="./FiltrarMenu">
                    <div class="w-50 d-inline-block">
                        <div class="input-group mr-2">
                            <div class="input-group-prepend">
                                <select class="custom-select input-group-text" id="categoriaBuscador" name="catSelected" 
                                    <%if(subcatSelected!=null){%>disabled<%}%>>
                                    <option <%if(catSelected==null){%>selected<%}%> value="Todos"> Todos </option>
                <%
                    for(Categoria c: categorias){
                %>
                                    <option <%if(catSelected!=null && c.equals(catSelected)){%>
                                            selected
                                       <%}%> 
                                            value="<%=c.getIdCategoria()%>"> <%=c.getNombreCategoria()%></option>
                <%
                    }
                %>
                                </select>
                            </div>
                <%
                    String prevBusqueda = (String) request.getAttribute("prevBusqueda");
                %>
                          <input name="busquedaFiltro" class="form-control mr-sm-2 w-75" type="search" placeholder="Busque su producto aqu&iacute;..." aria-label="Search" aria-describedby="basic-addon3"
                          <%if(prevBusqueda!=null){%>value="<%=prevBusqueda%>" <%}%>>
                        </div>
                        <%if(subcatSelected!=null){%>
                        <div class="ml-2">
                            <label class="text-secondary d-inline">Subcategoría: </label>
                            <span class="badge badge-info"><%=subcatSelected.getNombreSubcategoria()%></span>
                            <a href="./QuitarSubcategoria" class=" text-danger small font-weight-bold border border-danger px-1">Quitar</a>
                        </div>
                        <%}%>
                    </div>
                      
                    <div class="w-50 d-inline-block"> 
                        <span class="d-inline-block align-middle ml-1">Ordenar por:</span>
                      <select class="custom-select d-inline-block font-italic" name="orden">
                        <option <%if(orden.equals("Fecha")){%>selected<%}%> name="orden" value="Fecha">Fecha</option>
                        <option <%if(orden.equals("Precio")){%>selected<%}%> name="orden" value="Precio">Precio</option>
                        <option <%if(orden.equals("Valoracion")){%>selected<%}%> name="orden" value="Valoracion">Valoracion</option>
                      </select>
                      <button id="botonBuscar" class="d-inline-block btn btn-primary ml-2 my-2 my-sm-0" type="submit">Buscar</button>
                      
                    </div>
                     
                  </form>
                </nav>
                
        <%
            for(Producto p: productos){
                Subcategoria subP = p.getIdSubcategoria();
                Categoria catP = subP.getIdCategoria();
                String desc = null;
                if(p.getDescripcion().length()>200){
                    desc = p.getDescripcion().substring(0,200) + "...";
                }else{
                    desc = p.getDescripcion();
                }
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
                String dateString = format.format(p.getFechaSubida());
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
                            <label class="font-weight-bold">Descripción:</label>
                            <p> <%=desc %></p>
                        </div>
                        <div id="precioProducto" class="col">
                            <label class="font-weight-bold">Precio: </label>
                            <h5 class="font-weight-bold"> <%=p.getPrecio()%>€</h5>
                            
                            <!-- PUNTUACION CON ESTRELLAS-->
                            <form>
                              <label class="font-weight-bold">Valoraci&oacute;n:</label>
                              <p class="valoracionMedia" id="mediaProducto<%=p.getIdProducto()%>">
                                <input id="radio1" type="radio" name="estrellas<%=p.getIdProducto()%>" value="5"><!--
                                --><label for="radio1">★</label><!--
                                --><input id="radio2" type="radio" name="estrellas<%=p.getIdProducto()%>" value="4"><!--
                                --><label for="radio2">★</label><!--
                                --><input id="radio3" type="radio" name="estrellas<%=p.getIdProducto()%>" value="3"><!--
                                --><label for="radio3">★</label><!--
                                --><input id="radio4" type="radio" name="estrellas<%=p.getIdProducto()%>" value="2"><!--
                                --><label for="radio4">★</label><!--
                                --><input id="radio5" type="radio" name="estrellas<%=p.getIdProducto()%>" value="1"><!--
                                --><label for="radio5">★</label>
                              </p>
                            </form>
                            <label class="font-weight-bold">Vendedor:</label>
                            <span><%=p.getIdUsuario().getNombre()%> </span>
                            <span class="d-block text-secondary"> <%= dateString %> </span>
                        </div >
                        <div id="divMas" class="col">
                            <a id="verMas" class="btn btn-primary" href="./CargarProducto?idProducto=<%=p.getIdProducto()%>"  role="button" >Ver más</a>
                        </div>
                    </div>
                </div>
        <%
            }
        %>        
            </div>
        <!-- LATERAL DERECHO -->
        <!--
            <div class="col" id="latDer">
                <div class="m-2 p-2 text-white text-center" id="divPerfil">
                    <img id="imagenPerfil" src="<%=usuario.getFotoUsuario()%>" alt="Italian Trulli"/>
                    <h4 class="mb-0 pt-0"><%=usuario.getNombre()%></h4>
                    <small class="mt-0 pt-0"><%if(usuario.getIsAdmin()){%>Administrador<%}else{%>Usuario<%}%></small>
                </div>
            </div> -->
        
        </div>   
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
