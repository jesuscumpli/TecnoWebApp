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
        if (productos == null) {
            productos = (List<Producto>) usuario.getProductoList();
        }
        
    %>
    <body>
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
    </body>
</html>
