<%-- 
    Document   : listadoCategoriasAdmin
    Created on : 30-mar-2020, 21:13:26
    Author     : haylo
--%>

<%@page import="tecnoweb.entity.Usuario"%>
<%@page import="tecnoweb.entity.Subcategoria"%>
<%@page import="tecnoweb.entity.Categoria"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Categorias Listado Page</title>
        <link rel="stylesheet" href="css/estiloCategoriasAdmin.css">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        
    </head>
    <%
        Usuario usuario = (Usuario)session.getAttribute("usuario");
        if (usuario == null || !usuario.getIsAdmin()) {
                response.sendRedirect("login.jsp");  
                return;
        } 
        //BORRAR
        List<Categoria> categorias = (List<Categoria>)request.getAttribute("categorias");
        
        List<Subcategoria> subcategorias = (List<Subcategoria>)request.getAttribute("subcategorias");
        boolean consulta=false;
        if(subcategorias!=null){
            consulta=true;
        }
        
        Integer idCategoria = (Integer) request.getAttribute("idCategoria");
        
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
                        <a class="nav-link text-white" href="menuAdmin.jsp">Home</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="./ListadoUsuarioAdmin" >Usuarios</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="FiltrarMenu">Productos</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white h5 font-weight-bold" href="./ListadoCategoriasAdmin">Categorias</a>
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
        
        <h2 class="text-center bg-light">Listado Categorías (vista Admin):</h2>

        <div class="row">
            <form method="post" accept-charset="UTF-8" action="AnadirCategoria">
                <input type="text" name="nombre"></input>
                <input type="submit" value="Añadir nueva categoría"/>
            </form>
        </div>
        
        
         <div class="row">
         <div id="categorias">
         
         
         <table class="table text-center" >
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre Categoría</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
         
        <%
                for(Categoria cat: categorias){
        %>
                <tr>
                    <th scope="row"><%= cat.getIdCategoria() %></td>
                    <td><input type="text" value="<%= cat.getNombreCategoria()%>" id="nombreCategoria<%=cat.getIdCategoria()%>"></td>
                    <%if(!cat.getNombreCategoria().equals("Por definir")){%> <td><a href="BorrarCategoria?idCategoria=<%= cat.getIdCategoria() %>">Borrar</a></td> <%}%>
                    <%if(!cat.getNombreCategoria().equals("Por definir")){%> <td><button onclick="editarCategoria(<%=cat.getIdCategoria()%>)" >Editar</button></td> <%}%>
                    <%if(!cat.getNombreCategoria().equals("Por definir")){%><td><a href="ListadoConsultarSubcategorias?idCategoria=<%= cat.getIdCategoria() %>">Consultar subcategorías</a></td><%}%>
                </tr>          
        <%
                }
        %>    
                        <script>
                            function editarCategoria(idCat){
                                var input = document.getElementById("nombreCategoria"+idCat);
                                var nombre = input.value;
                                location.href = "AnadirCategoria?idCate="+idCat+"&nombre="+nombre;
                            }
                        </script>
        
        </tbody>
        </table>
        </div>
        
         
        
        
        <div id="subcategorias">
            
            <%
                if(consulta){   
            %>
            
            <div class="row">
                <form method="post" accept-charset="UTF-8" action="AnadirSubcategoria?idCategoria=<%= idCategoria%>">
                    <input type="text" name="nombre"></input>
                    <input type="submit" value="Añadir nueva subcategoría"/>
                </form>
            </div>
        
            
            <table class="table text-center" >
            <thead>
                <tr>
                    <th scope="col">ID </th>
                    <th scope="col">Nombre Subcategoría</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
         
            <%
                    for(Subcategoria subcat: subcategorias){
            %>
                    <tr>
                        <th scope="row"><%= subcat.getIdSubcategoria() %></td>
                        <td><input type="text" value="<%= subcat.getNombreSubcategoria()%>" id="nombreSubcategoria<%=subcat.getIdSubcategoria()%>"></td>
                        <td><a href="BorrarSubcategoria?idSubcategoria=<%= subcat.getIdSubcategoria() %>">Borrar</a></td>   
                        <td><button onclick="editarSubcategoria(<%=subcat.getIdCategoria().getIdCategoria()%>,<%=subcat.getIdSubcategoria()%>)">Editar</button></td>
                        </tr>         
            <%
                    }
            %>    
                        <script>
                            function editarSubcategoria(idCat, idSubcat){
                                var input = document.getElementById("nombreSubcategoria"+idSubcat);
                                var nombre = input.value;
                                location.href = "AnadirSubcategoria?idSubcate="+idSubcat+"&nombre="+nombre+"&idCategoria="+idCat;
                            }
                        </script>
            
            </tbody>
            </table>
            <%
                    }
            %>    
            
        
        </div>
        </div>
            
            
                
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
