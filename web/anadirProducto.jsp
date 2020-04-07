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
        }
  
        if (categoriaSeleccionada != null) {
            nombre = (String) request.getAttribute("nombre");
            precio = (String) request.getAttribute("precio");
            descripcion = (String) request.getAttribute("descripcion");
            idCategoria = categoriaSeleccionada.getIdCategoria();
            palabrasClave = (String) request.getAttribute("palabrasClave");
            url = (String) request.getAttribute("url");
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
            if (producto.getPalabraclaveList() != null) {
                for (Palabraclave p : producto.getPalabraclaveList()) {
                    palabrasClave += p.getValor() + ",";
                }
                palabrasClave = palabrasClave.substring(0,palabrasClave.length()-1);
            } 
        }
        List<Categoria> listaCategorias = (List) session.getAttribute("listaCategorias");
     %>
    <body>
        <h1>Complete los campos para su nuevo producto:</h1>               
        <form method="get" action="GuardarProducto" accept-charset="UTF-8">
            <input type="hidden" name="productoId" value="<%=productoId%>" />
            Nombre del producto: <input type = "text" maxlength = "15" size="15" name = "nombre" value="<%=nombre%>"/> <br/>
            Precio: <input type = "number" maxlength = "15" size="15" name = "precio" value="<%=precio%>"/> <br/>
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
