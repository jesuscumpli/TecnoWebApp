<%-- 
    Document   : evaluacion
    Created on : 24-mar-2020, 19:21:21
    Author     : luisr
--%>

<%@page import="tecnoweb.dto.ProductoMenuDTO"%>
<%@page import="tecnoweb.dto.ValoracionDTO"%>
<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page import="tecnoweb.entity.Valoracion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ProductoMenuDTO producto = (ProductoMenuDTO)request.getAttribute("producto");
    UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
    if (usuario == null || usuario.getIsAdmin()) {
        response.sendRedirect("login.jsp");  
        return;
    }
    ValoracionDTO valoracion = (ValoracionDTO)request.getAttribute("valoracion");
    String nombreProducto, comentario, descripcion;
    int idUsuario, idProducto, idValoracion;
    Double precio, nota;
    List<ValoracionDTO> listaValoraciones = (List<ValoracionDTO>) request.getAttribute("listaValoraciones");
    
    nombreProducto= producto.getTitulo();

    idUsuario= usuario.getIdUsuario();
    idProducto=producto.getIdProducto();
    descripcion = producto.getDescripcion();
    precio = producto.getPrecio();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
    
    if(valoracion==null){
        nota = 0.0;
        comentario= "";
        idValoracion = 0;
    }else{
        nota = valoracion.getNota();
        comentario= valoracion.getComentario();
        idValoracion = valoracion.getIdValoracion();
    }
    String status = (String) request.getAttribute("status");
    
%>
<html>
    <head>
        <title>Valoración: <%=nombreProducto%></title>
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
                        <a class="nav-link text-white" href="./FiltrarMenu">Home</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a class="nav-link text-white" href="listadoProducto.jsp" >Mis Productos</a>
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
                        <a class="nav-link text-white my-0 py-0" href="./CerrarSesion">Cerrar Sesión</a>
                    </div>
                </div>
            </div>
         </nav>
        
        <!----------------------------------------------------------------->
        
        <div  id="margenIzq">
            
        </div>
        
        <div id="contenedorCentral">
        <div id="evaluacion" class="border border-primary rounded container-fluid">
        <h2> Producto: <%= nombreProducto%></h2>
        
        <div class="row">
        <div id="imagen" class="col">
            <img src="<%=producto.getFotoProducto()%>" class="img-thumbnail" id="producto" alt="...">
        </div>
        
        <div id="descripcion" class="col">
             <label class="font-weight-bold">Descripción:</label>
             <p>
                 <%=descripcion %>
             </p>
        </div>
        </div>
             
        <div id="precio">
            <h5 class="font-weight-bold">Precio: <%=precio%>€</h5>
        </div>
        
        
            
        <div id="datos" > 
        <form method="post" action="./GuardarValoracionProducto?idProducto=<%=idProducto%>&idValoracion=<%=idValoracion%>" name="datos" accept-charset="UTF-8">
            
            <label for="comentario">Comentario</label>
            <textarea class="form-control" id="comentario" name="comentario" rows="6"><%=comentario%></textarea>
            
            <div>
                <label id="nota" for="nota" style="margin-top: 25px">Nota</label>
                <select class="form-control form-control-lg" id="nota" name="nota" >
                    <% for(int i=0;i<=10;i++){ 
                        if(i==nota){%>
                        <option selected><%=i%></option>
                        <% }else{ %>
                        <option><%=i%></option>
                        <% }} %>
                </select>
            </div>
               
           <div id="botones">
                <input class="btn btn-success" type="submit" value="Enviar" />
                <input class="btn btn-warning" type="reset" value="Limpiar"/>
                <a class="btn btn-danger" type="button" href="./FiltrarMenu" >Volver</a>
            </div>
            <%if(status!=null){%> <small> <%=status%> </small><%}%>
        </form>
        </div>
        
        </div>
                
            
        <%
            for(ValoracionDTO v: listaValoraciones){
                UsuarioDTO u = v.getUsuario() ;
                String coment = v.getComentario();
                String fechaPublicacion = format.format(v.getFechaPublicacion());
        %>
        
        <div id="cajaValoracion" class="badge">
            <div id="Usuario" class="h3" > 
                <span class="ml-2"><%=u.getNombre()+" "+u.getApellidos() %></span>
            </div>
            <div id="cuerpoValoracion" class="row text-center">
                <div id="comentarioProducto" class="col">
                    <label class="font-weight-bold">Descripción:</label>
                    <p> <%=coment %></p>
                </div>
                <div id="notaProducto" class="col">
                    <label class="font-weight-bold">Nota: </label>
                    <h5 class="font-weight-bold"> <%= v.getNota().intValue() %></h5>
                    <br>
                    <label class="font-weight-bold">Fecha de publicación:</label> 
                    <h5 class="font-weight-bold"> <%= fechaPublicacion %></h5>
                </div>
            </div>
        </div>
        
        <% } %>
        
        
        </div>
                
        <div id="margenDer">
                    
        </div>
        
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
