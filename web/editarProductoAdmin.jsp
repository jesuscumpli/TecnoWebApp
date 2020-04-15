<%-- 
    Document   : editarProductoAdmin
    Created on : 08-abr-2020, 0:07:55
    Author     : haylo
--%>

<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="tecnoweb.dto.PalabraclaveDTO"%>
<%@page import="tecnoweb.dto.ProductoMenuDTO"%>
<%@page import="tecnoweb.entity.Palabraclave"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Valoracion"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ProductoMenuDTO producto = (ProductoMenuDTO)request.getAttribute("producto");
    UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
    if (usuario == null || !usuario.getIsAdmin()) {
            response.sendRedirect("login.jsp");  
            return;
    } 
    String nombreProducto, descripcion, palabrasClave = "";
    int idUsuario, idProducto;
    Double precio;
    
    nombreProducto= producto.getTitulo();

    idUsuario= usuario.getIdUsuario();
    idProducto=producto.getIdProducto();
    descripcion = producto.getDescripcion();
    precio = producto.getPrecio();
    //palabrasClave = (String) request.getAttribute("palabrasClave");
    if (producto.getPalabraclaveList() != null && !producto.getPalabraclaveList().isEmpty()) {
        for (PalabraclaveDTO p : producto.getPalabraclaveList()) {
            palabrasClave += p.getValor() + ",";
        }
        if(palabrasClave.length()>0){palabrasClave = palabrasClave.substring(0,palabrasClave.length()-1);}
    }


    String statusNombre = (String) request.getAttribute("statusNombre");
    if(statusNombre == null) statusNombre = "";
    String statusDescripcion = (String) request.getAttribute("statusDescripcion");
    if(statusDescripcion == null) statusDescripcion = "";
    String statusPrecio = (String) request.getAttribute("statusPrecio");
    if(statusPrecio == null) statusPrecio = "";
%>
<html>
    <head>
        <title>Editar: <%=nombreProducto%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloValoracion.css">
    </head>
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
                        <a class="nav-link text-white  h5 font-weight-bold" href="FiltrarMenu">Productos</a>
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
        
        
        <div  id="margenIzq">
            
        </div>
        
        <div id="contenedorCentral">
            
            <form id="buttonAceptar" method="post" action="EditarProductoAdmin?idProducto=<%= idProducto %>" name="aceptarEditar" accept-charset="UTF-8">
            
        <div id="evaluacion" class="border border-primary rounded container-fluid">
        <h2> Producto: <%= nombreProducto%></h2>
        <span class="small text-danger font-italic font-weight-bold"> <%=statusNombre%> </span>
        <input id="inpData" type="nombreProducto" class="form-control" name="nombreProducto" aria-describedby="nombreHelp" placeholder="Introduzca aquí el nombre del producto..." maxlength=50 value="<%=nombreProducto%>">
        </br>
        
        <div class="row">
        <div id="imagen" class="col">
            <img id="imgVista" src="<%=producto.getFotoProducto()%>" class="img-thumbnail" id="producto" alt="...">
            </br>
            <small class="form-text text-white m-0">Introduce aquí una url con tu foto de perfil</small>
            <div id="cambiarImagen" class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon3">URL:</span>
              </div>
              <input id="url" name="photoURL" type="text" class="form-control" id="basic-url" aria-describedby="basic-addon3">
                <div class="input-group-append">
                    <button type="button" class="btn btn-success" id="basic-addon2" onclick="cambiarImagenJS()">Ver</button>
                </div>
            </div>
        </div>
        
        <div id="descripcion" class="col">
             <label class="font-weight-bold">Descripción:</label>
             <p>
             <span class="small text-danger font-italic font-weight-bold"> <%=statusDescripcion%> </span>
             <textarea id="inpData" class="form-control" name="descripcion" aria-describedby="descripcionHelp" rows="10" cols="30"><%=descripcion %></textarea>
             </P>
        </div>
        </div>
             
        <div class="row">
        <div id="precio2" class="col">
            <h5 class="font-weight-bold">Precio:</h5>
            <span class="small text-danger font-italic font-weight-bold"> <%=statusPrecio%> </span>
            <input id="inpData" class="form-control" name="precio" aria-describedby="precioHelp" maxlength=10 value="<%=precio%>">
        </div>
        
        <div id="palabrasClave" class="col">
            <h5 class="font-weight-bold">Palabras clave: </h5>
            <span class="small text-danger font-italic font-weight-bold"> </span>
            <input id="inpData" class="form-control" name="palabrasClave" aria-describedby="precioHelp" value="<%=palabrasClave%>">
        </div>
        </div>
                
           <div id="botones">
                <input class="btn btn-success" type="submit" value="Aceptar cambios" />
                <a class="btn btn-danger" type="button" href="./FiltrarMenu" >Volver</a>
            </div>
            
       
        </div>
         </form>
        </div>
                
        <div id="margenDer">
                    
        </div>
        
        
        <!---------------------------------------------------------------------------------------------------------------------------------->
        <script> 
            function cambiarImagenJS(){
                var url = document.getElementById("url").value;
                if(url === null || url === "") {
                    document.getElementById("imgVista").src = '<%= usuario.getFotoUsuario() %>';
                } else {
                    document.getElementById("imgVista").src = url;
                }
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
