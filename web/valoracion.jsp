<%-- 
    Document   : evaluacion
    Created on : 24-mar-2020, 19:21:21
    Author     : luisr
--%>

<%@page import="tecnoweb.entity.Usuario"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page import="tecnoweb.entity.Valoracion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Producto producto = (Producto)request.getAttribute("producto");
    Usuario usuario = (Usuario)session.getAttribute("usuario");
    Valoracion valoracion = (Valoracion)request.getAttribute("valoracion");
    String nombreProducto, comentario, descripcion;
    int idUsuario, idProducto, idValoracion;
    Double precio, nota;
    
    nombreProducto= producto.getTitulo();

    idUsuario= usuario.getIdUsuario();
    idProducto=producto.getIdProducto();
    descripcion = producto.getDescripcion();
    precio = producto.getPrecio();
    
    
    if(valoracion==null){
        nota = 0.0;
        comentario= "";
        idValoracion = 0;
    }else{
        nota = valoracion.getNota();
        comentario= valoracion.getComentario();
        idValoracion = valoracion.getValoracionPK().getIdValoracion();
    }
    
   
    
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
         <nav class="navbar navbar-expand-lg navbar-light bg-dark" id="navbarInicio">
            <div class="navbar-brand"><h2 class="text-primary d-inline">Tecno </h2><h2 class="text-white d-inline"> Web</h2></div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link text-white" href="#">Home<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="#" >Link</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Dropdown
                        </a>
                        <div class="dropdown-menu bg-secondary" id="dropdownNavbar" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item text-white" href="#">Action</a>
                            <a class="dropdown-item text-white" href="#">Another action</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item text-white" href="#">Something else here</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="./ListarUsuarios">Listar Usuarios</a>
                    </li>
                </ul>
            </div>
         </nav>
        
        <!----------------------------------------------------------------->
        
        <div id="evaluacion" class="border border-primary rounded container-fluid">
        <h2> Producto: <%= nombreProducto%></h2>
        
        <div id="imagen">
            <img src="<%=producto.getFotoProducto()%>" class="img-thumbnail" id="producto" alt="...">
        </div>
        
        <div id="descripcion">
             <label class="font-weight-bold">Descripción:</label>
             <p>
                 <%=descripcion %>
             </p>
        </div>
             
        <div id="precio">
            <label class="font-weight-bold">Precio: </label>
            <h5 class="font-weight-bold"> <%=precio%>€</h5>
        </div>
        
        
            
        <div id="datos" > 
        <form method="post" action="./GuardarValoracionProducto?idProducto=<%=idProducto%>&idValoracion=<%=idValoracion%>" name="datos" accept-charset="UTF-8">
            
            <div id="botones">
                <input class="btn btn-success" type="submit" value="Enviar" />
                <input class="btn btn-warning" type="reset" value="Limpiar"/>
                <a class="btn btn-danger" type="button" href="./menu.jsp" >Volver</a>
            </div>
            
            <label for="comentario">Comentario</label>
            <textarea class="form-control" id="comentario" name="comentario" rows="6"><%=comentario%></textarea>
            
            <div>
            
                <select class="form-control form-control-lg" id="nota" name="nota" >
                    <% for(int i=0;i<=10;i++){ 
                        if(i==nota){%>
                        <option selected><%=i%></option>
                        <% }else{ %>
                        <option><%=i%></option>
                        <% }} %>
                </select>
                <label id="nota" for="nota" style="margin-top: 25px">Nota</label>
            </div>
           
            
        </form>
        </div>
        
        </div>
        
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
