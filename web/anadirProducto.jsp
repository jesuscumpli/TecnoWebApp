<%-- 
    Document   : anadirProducto
    Created on : 26-mar-2020, 18:49:36
    Author     : alvar
--%>

<%@page import="tecnoweb.dto.CategoriaMenuDTO"%>
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
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloValoracion.css">
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
 
        <div id="contenedorCentral">
        <div id="evaluacion" class="border border-primary rounded container-fluid">
        <form method="get" class="was-validated" action="GuardarProducto" accept-charset="UTF-8">
            <input type="hidden" name="productoId" value="<%=productoId%>" />
            
            <div class="form-row">
                 <div class="col">
                    <label for="imputNombreProducto">Nombre del producto</label>
                    <input class="form-control" id ="imputNombreProducto" type = "text" name = "nombre" required value="<%=nombre%>"/> <br/>
                    <div class="invalid-feedback">Nombre requerido</div>
                 </div>
                 <div class="col">
                    <label for="imputPrecio"> Precio </label>
                    <input class="form-control" type = "number" name = "precio" required="" value="<%=precio%>"/> <br/>
                    <div class="invalid-feedback">Precio requerido</div>
                 </div>
            </div>
            
            <div class="form-group"
                 <label for="imputDescripcion"> Descripcion </label>
                 <textarea class="form-control" id="imputDescripcion" placeholder="A&ntilde;ade una descripcion a tu producto" name= "descripcion" required rows="10" cols="30"><%=descripcion%></textarea> <br/>
                 <div class="invalid-feedback">Descripcion requerida</div>
            </div>
            
            <div class="form-group"
                 <label for="imputPClave"> Palabras clave </label>
                <input class="form-control" id="imputPClave" aria-describedby="pclaveHelp" required placeholder="A&ntilde;ade palabras clave para facilitar la busqueda de tu producto" type = "text" name = "palabrasClave" value="<%=palabrasClave%>"/>
                 <small id="pclaveHelp" class="form-text text-muted">Separe las palabras clave por comas</small>
            </div>
                 
            <div class="form-inline">
                
                <div class="form-group">
                  <label for="inputCategoria">Categoria</label>
                  <select id="inputCategoria" name="categoria" class="custom-select">
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
                </div>
                
                <%  
                if (categoriaSeleccionada!=null) {
                %>
                <div class="form-group">
                  <label for="inputSubcategoria">Subcategoria</label>
                  <select id="inputSubcategoria" name="subcategoria" class="custom-select">
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
                </div>
                <% 
                }
                %>
              <button type="submit" formaction="GuardarCategoria" class="btn btn-success mb-2"> Ver subcategorias</button>
              </div>
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
                <img id="imgVista" src="<%=url%>" class="img-thumbnail rounded mx-auto d-block w-5 h-5">
               
            
                <%
                    if (categoriaSeleccionada!=null) {
                      
                 %>
                <input type="submit" class="btn btn-primary" value="Enviar" />
                <%
                    }
                %>
                
                
                <%
            if (status !=null ) {
                %>
                        <%=status%>
                <%
                    }
                %>

        </form>
        </div>
        </div>
        
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
