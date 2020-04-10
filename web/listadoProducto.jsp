<%-- 
    Document   : listadoProducto.jsp
    Created on : 29-mar-2020, 21:10:02
    Author     : alvar
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page import="java.util.List"%>
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
    <%
        Usuario usuario;
        List<Producto> productos;
        // Si el usuario está dentro de la sesión quiere decir que ya hizo login
        // por lo que se le redirige a menu.jsp
        usuario = (Usuario)session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("login.jsp");  
            return;
        }
        productos = (List) session.getAttribute("misProductos");
        //productos = usuario.getProductoList();
        if (productos == null) {
            productos = (List<Producto>) usuario.getProductoList();
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
                        <a class="nav-link text-white h5 font-weight-bold" href="#" >Mis Productos</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="misValoraciones.jsp">Mis Valoraciones</a>
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
                                <a class="dropdown-item text-white" href="#">Mis Productos</a>
                                <a class="dropdown-item text-white" href="misValoraciones.jsp">Mis Valoraciones</a>
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
        
        <table border="1">
            <thead>
                <tr>
                    <th>Titulo</th>
                    <th>Descripcion</th>
                    <th>Foto</th>
                    <th>Fecha</th>
                    <th>Precio</th>
                    <th>Categoria</th>
                    <th>Subcategoria</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for(Producto p: productos){
                %>
                <tr>
                    <td> <%=p.getTitulo()%> </td>
                    <td> <%=p.getDescripcion() %> </td>
                    <td> <%=p.getFotoProducto() %> </td>
                    <%
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
                        String dateString = format.format(p.getFechaSubida());
                    %>
                    <td> <%=dateString %> </td>
                    <td> <%= Double.toString(p.getPrecio()) %> </td>
                    <td> <%= p.getIdSubcategoria().getIdCategoria().getNombreCategoria() %> </td> 
                    <td> <%= p.getIdSubcategoria().getNombreSubcategoria() %> </td> 
                    <td><a href="ProductoEditar?id=<%=p.getIdProducto()%>">Editar</a></td>
                    <td><a href="ProductoBorrar?id=<%=p.getIdProducto()%>">Borrar</a></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <a href="ProductoCrear">Nuevo producto</a>
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
