<%-- 
    Document   : listadoUsuario
    Created on : 23-mar-2020, 11:45:28
    Author     : Jesús
--%>

<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        List<Usuario> lista = (List) request.getAttribute("usuarios");
    %>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listado Usuario Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
    </head>
    <body>
        
        <h2 class="text-center bg-light">Listado Usuarios:</h2>
        
        <table class="table text-center" >
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Apellidos</th>
                    <th scope="col">Fecha Nacimiento</th>
                    <th scope="col">Administrador</th>
                    <th scope="col">Email</th>
                    <th scope="col">Foto</th>
                </tr>
            </thead>
            <tbody>
    <%
      for(Usuario u: lista){
    %>
                <tr>
                    <th scope="row"><%= u.getIdUsuario() %></td>
                    <td><%= u.getNombre() %></td>
                    <td><%= u.getApellidos() %></td>
                    <td><%= u.getFechaNac().toString() %></td>
                    <td><% if(u.getIsAdmin()){%>True<%}else{%>False<%}%></td>
                    <td><%= u.getEmailUsuario() %></td>
                    <td><img src="<%if(u.getFotoUsuario()!=null){%><%=u.getFotoUsuario()%><%}else{%>#<%}%>" height="25px" widtht="25px"></td>
                </tr>
    <%
       }
    %>
            </tbody>
        </table>

        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"  crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
        
    </body>
</html>