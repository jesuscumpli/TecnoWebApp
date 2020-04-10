<%-- 
    Document   : anadirProducto
    Created on : 26-mar-2020, 18:49:36
    Author     : alvar
--%>

<%@page import="tecnoweb.entity.Palabraclave"%>
<%@page import="tecnoweb.entity.Subcategoria"%>
<%@page import="tecnoweb.entity.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>A&ntilde;adir producto</title>
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
    </head>
     <%
        String palabrasClave = "";
        String nombre = "";
        String descripcion = "";
        String productoId = "";
        String precio = "";
        String url = "";
        int idCategoria = -1;
        int idSubcategoria = -1;
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || usuario.getIsAdmin()) {
            response.sendRedirect("login.jsp");  
            return;
        }
        
        Producto producto = (Producto) request.getAttribute("producto");
        Categoria categoriaSeleccionada = (Categoria) request.getAttribute("categoria");
        String status = (String) request.getAttribute("status");
        
        if (status != null) {
            nombre = (String) request.getAttribute("nombre");
            precio = (String) request.getAttribute("precio");
            descripcion = (String) request.getAttribute("descripcion");
            Subcategoria aux = (Subcategoria) request.getAttribute("subcategoria");
            idCategoria = aux.getIdCategoria().getIdCategoria();
            idSubcategoria = aux.getIdSubcategoria();
            palabrasClave = (String) request.getAttribute("palabrasClave");
            url = (String) request.getAttribute("url");
            productoId = (String) request.getAttribute("productoId");
        }
  
        if (categoriaSeleccionada != null) {
            nombre = (String) request.getAttribute("nombre");
            precio = (String) request.getAttribute("precio");
            descripcion = (String) request.getAttribute("descripcion");
            idCategoria = categoriaSeleccionada.getIdCategoria();
            palabrasClave = (String) request.getAttribute("palabrasClave");
            url = (String) request.getAttribute("url");
            productoId = (String) request.getAttribute("productoId");
        }
        
        if (producto != null) {
            productoId = producto.getIdProducto() + "";
            nombre = producto.getTitulo();
            precio = producto.getPrecio() + "";
            descripcion = producto.getDescripcion();
            url = producto.getFotoProducto();
            idSubcategoria = producto.getIdSubcategoria().getIdSubcategoria();
            idCategoria = producto.getIdSubcategoria().getIdCategoria().getIdCategoria();
            categoriaSeleccionada = producto.getIdSubcategoria().getIdCategoria();
            if (producto.getPalabraclaveList() != null && !producto.getPalabraclaveList().isEmpty()) {
                for (Palabraclave p : producto.getPalabraclaveList()) {
                    palabrasClave += p.getValor() + ",";
                }
                if(palabrasClave.length()>0){palabrasClave = palabrasClave.substring(0,palabrasClave.length()-1);}
            } 
        }
        List<Categoria> listaCategorias = (List) session.getAttribute("categorias");
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
                        <a class="nav-link text-white  h5 font-weight-bold" href="listadoProducto.jsp" >Mis Productos</a>
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
                                <a class="dropdown-item text-white" href="listadoProducto.jsp">Mis Productos</a>
                                <a class="dropdown-item text-white" href="misValoraciones.jsp">Mis Valoraciones</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item text-white" href="./CerrarSesion">Cerrar Sesi&oacute;n</a>
                            </div>
                        </div>
                        <a class="nav-link text-white my-0 py-0" href="./CerrarSesion">Cerrar Sesi&oacute;n</a>
                    </div>
                </div>
            </div>
        </nav>
        
        
        <h1>Complete los campos para su nuevo producto:</h1>               
        <form method="post" action="GuardarProducto" accept-charset="UTF-8">
            <input type="hidden" name="productoId" value="<%=productoId%>" />
            Nombre del producto: <input type = "text" maxlength = "50" size="15" name = "nombre" value="<%=nombre%>"/> <br/>
            Precio: <input type="number" step="0.01" maxlength = "10" size="15" name = "precio" value="<%=precio%>"/> <br/>
            <textarea name= "descripcion" rows="10" cols="30"><%=descripcion%></textarea> <br/>
            Palabras clave: <input type = "text" name = "palabrasClave" value="<%=palabrasClave%>"/> Separe por comas
            <br/>
            
        <select name="categoria"> 
        <%
            for (Categoria c: listaCategorias) {
                String seleccionado = "";
                if (idCategoria != -1 && idCategoria == c.getIdCategoria()) {
                     seleccionado = "selected";   
                     categoriaSeleccionada = c;    
                }
        %>
            <option <%=seleccionado%> value="<%= c.getNombreCategoria() %>"><%= c.getNombreCategoria() %></option>
        <%
        }
        %> 
        </select>
        
         <%  
             if (categoriaSeleccionada!=null) {
                 
         %>
         <select name="subcategoria">
        <%
            for (Subcategoria sc: categoriaSeleccionada.getSubcategoriaList()) {
                String seleccionado = "";
                    if (idSubcategoria != -1 && idSubcategoria == sc.getIdSubcategoria()) {
                         seleccionado = "selected";       
                    }
        %>
        <option <%=seleccionado%> value="<%= sc.getNombreSubcategoria() %>"><%= sc.getNombreSubcategoria() %></option>
        <%
            }
        %>
         </select>
        <br/>
            <input type="submit" value="Enviar" />
        <% 
            }
        %>
        <br/>
            <input type="submit" formaction="GuardarCategoria" value="Ver subcategorias"/>
        <br/>
        <% 
            if (status !=null ) {
        %>
                <%=status%>
        <%
            }
        %>
        
        <hr class="bg-white">
                <label class="mb-0">Foto perfil</label>
                <!--<small class="form-text text-white m-0">Introduce aquí una foto de perfil si lo desea</small>
                <div class="file-upload-wrapper">
                    <input type="file" name="imagen" class="file-upload" />
                </div>-->
                <small class="form-text text-white m-0">Introduce aquí una url con tu foto de perfil</small>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon3">URL:</span>
                  </div>
                  <input id="url" value="<%=url%>" name="url" type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">
                    <div class="input-group-append">
                        <button type="button" class="btn btn-success" id="basic-addon2" onclick="cambiarImagenJS()">Ver</button>
                    </div>
                </div>
                  <%
                      if (url == null || url.isEmpty()) {
                          url = "https://748073e22e8db794416a-cc51ef6b37841580002827d4d94d19b6.ssl.cf3.rackcdn.com/not-found.png";
                      }
                  %>
                <img id="imgVista" src="<%=url%>" class="img-thumbnail rounded mx-auto d-block w-15 h-15">
        <br/>
        
        </form>
        
        <script> 
            function cambiarImagenJS(){
                var url = document.getElementById("url").value;
                document.getElementById("imgVista").src = url;
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
