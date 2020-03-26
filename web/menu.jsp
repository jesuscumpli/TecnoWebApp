<%-- 
    Document   : menu
    Created on : 23-mar-2020, 18:25:23
    Author     : Jesús
--%>

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
    </head>
    <%
        Usuario usuario;
        String status;
        
        // Si el usuario está dentro de la sesión quiere decir que ya hizo login
        // por lo que se le redirige a menu.jsp
        usuario = (Usuario)session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("login.jsp");  
            return;
        }
        List<Producto> productos = (List<Producto>)session.getAttribute("productos");
        List<Categoria> categorias = (List<Categoria>)session.getAttribute("categorias");
    %>
    <body>
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
                <div class="nav-item">
                    <a class="nav-link text-white float-right float-sm-left pl-0" href="./CerrarSesion">Cerrar Sesión</a>
                </div>
            </div>
         </nav>
        
        <!----------------------------------------------->
        
        <div class="row">

        <!-- LATERAL IZQUIERDO: CATEGORIAS   -->
            <div class="col text-white" id="latIzq">
                <h2 class="font-weight-bold text-center">Categorias</h2>
                <ul>
        <%
                for(Categoria cat: categorias){
        %>
                    <li class="font-weight-bold">
                        <a class="text-white" data-toggle="collapse" href="#collapseCategoria<%=cat.getIdCategoria()%>" aria-expanded="false" aria-controls="collapseCategoria">
                            <%=cat.getNombreCategoria() %>
                        </a>
                        <ul class="collapse" id="collapseCategoria<%=cat.getIdCategoria()%>">
                        <%
                            for(Subcategoria subcat: cat.getSubcategoriaList()){
                        %>
                            <li class="font-italic font-weight-light">
                                <a class="text-white" href="#">
                                    <%=subcat.getNombreSubcategoria() %>
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
            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow">
                  <form class="form-inline my-2 my-lg-0 w-50">
                      <input class="form-control mr-sm-2 w-75" type="search" placeholder="Busque su producto aqu&iacute;..." aria-label="Search">
                      <button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Buscar</button>
                  </form>

                  <div class="w-50"> 
                    Ordenar por:

                    <div class="dropdown show" id="ordenarPor">
                      <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Dropdown link
                      </a>

                      <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="#">Fecha</a>
                        <a class="dropdown-item" href="#">Precio</a>
                        <a class="dropdown-item" href="#">Nombre</a>
                      </div>
                    </div>
                  </div>
                </nav>
                
                
                
            </div>
        <!-- LATERAL DERECHO -->
        
            <div class="col" id="latDer">
                <div class="m-2 p-2 text-white text-center" id="divPerfil">
                    <img id="imagenPerfil" src="<%=usuario.getFotoUsuario()%>" alt="Italian Trulli"/>
                    <h4 class="mb-0 pt-0"><%=usuario.getNombre()%></h4>
                    <small class="mt-0 pt-0"><%if(usuario.getIsAdmin()){%>Administrador<%}else{%>Usuario<%}%></small>
                </div>
            </div>
        
        </div>   
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
