<%-- 
    Document   : menu
    Created on : 23-mar-2020, 18:25:23
    Author     : Jesús
--%>

<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina Principal</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloMenu.css">
    </head>
    <%
        UsuarioDTO usuario;
        
        usuario = (UsuarioDTO) session.getAttribute("usuario");
        if (usuario == null || !usuario.getIsAdmin()) {
            response.sendRedirect("login.jsp");  
            return;
        } 
        String status = (String) request.getAttribute("status");
        if(status==null){
            status="";
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
                        <a class="nav-link text-white h5 font-weight-bold" href="#">Home</a>
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
        
        <div class="row">

        <!-- LATERAL IZQUIERDO: CATEGORIAS   -->
            <div class="col" id="latIzq">
                
            </div>
        
        <!-- CONTAINER -->
            <div id="centro" class="row">
                <div class="col d-inline-block" style="width: 33%">
                    <div class="card w-100" style="width: 18rem;">
                        <img id="imgCard" src="https://i2.wp.com/helpmybusinesspos.info/wp-content/uploads/2015/02/Usuario-permisos_mybusinesspos-300x200.png?resize=300%2C200" class="card-img-top" alt="...">
                        <div class="card-body">
                            <p class="card-text">Administre aquí el listado de USUARIOS de Tecno Web. Añadir un nuevo usuario. Editar y Borrar un usuario existente.</p>
                            <a href="./ListadoUsuarioAdmin" class="btn btn-success w-100">USARIOS</a>
                        </div>
                    </div>
                </div>
                <div class="col d-inline-block" style="width: 33%">
                    <div class="card w-100" style="width: 18rem;">
                        <img id="imgCard" src="https://a2kartro.com/wp-content/uploads/2016/04/cajas-basica-productos.png" class="card-img-top" alt="...">
                        <div class="card-body">
                            <p class="card-text">Administre aquí el listado de PRODUCTOS de Tecno Web. Sólo Editar y Borrar un producto existente en la página web.</p>
                            <a href="./FiltrarMenu" class="btn btn-success w-100">PRODUCTOS</a>
                        </div>
                    </div>
                </div>
                    
                <div class="col d-inline-block" style="width: 33%">
                    <div class="card w-100" style="width: 18rem;">
                        <img id="imgCard" src="https://gmartos.es/wp-content/uploads/2019/06/logo.png" class="card-img-top" alt="...">
                        <div class="card-body">
                            <p class="card-text">Administre aquí el listado de CATEGORÍAS y SUBCATEGORÍAS de Tecno Web. Añadir, Editar y Borrar una categoría.</p>
                            <a href="./ListadoCategoriasAdmin" class="btn btn-success w-100">CATEGORÍAS</a>
                        </div>
                    </div>
                </div>
                <div class="row mt-5">
                    <div class="col text-center">
                        <label class="font-weight-bold mb-0">Limpiar Palabras Claves Sin Usar:</label>
                        <small class="text-success"><%=status %></small>
                        <br>
                        <label class="small">Elimine las palabras claves de la base de datos que han dejado de usarse por los productos...</label>
                        <a href="./LimpiarPalabrasClaves" class="btn btn-info w-100">LIMPIAR PALABRAS CLAVES</a>
                    </div>
                </div>
 
            </div>
            
        <!-- LATERAL DERECHO -->
        
            <div class="col" id="latDer">
            </div>
        
        </div>   
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
