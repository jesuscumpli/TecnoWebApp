<%-- 
    Document   : menu
    Created on : 23-mar-2020, 18:25:23
    Author     : Jesús
--%>

<%@page import="tecnoweb.dto.UsuarioDTO"%>
<%@page import="tecnoweb.dto.CategoriaMenuDTO"%>
<%@page import="tecnoweb.dto.CategoriaDTO"%>
<%@page import="tecnoweb.dto.SubcategoriaDTO"%>
<%@page import="tecnoweb.dto.ProductoMenuDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="tecnoweb.entity.Subcategoria"%>
<%@page import="java.util.List"%>
<%@page import="tecnoweb.entity.Categoria"%>
<%@page import="tecnoweb.entity.Producto"%>
<%@page import="tecnoweb.entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina Principal</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estiloGeneral.css">
        <link rel="stylesheet" href="css/estiloMenu.css">
        <link rel="stylesheet" href="css/estrellas.css">
    </head>
    <%
        UsuarioDTO usuario;
        
        usuario = (UsuarioDTO)session.getAttribute("usuario");
        SubcategoriaDTO subcatSelected = (SubcategoriaDTO) session.getAttribute("subcatSelected");
        CategoriaDTO catSelected = (CategoriaDTO) session.getAttribute("catSelected");
        if (usuario == null || usuario.getIsAdmin()) {
            response.sendRedirect("login.jsp");  
            return;
        }
        String orden = (String) session.getAttribute("orden");
        List<ProductoMenuDTO> productos = (List<ProductoMenuDTO>)session.getAttribute("productos");
        List<CategoriaMenuDTO> categorias = (List<CategoriaMenuDTO>)session.getAttribute("categorias");
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
                        <a class="nav-link text-white h5 font-weight-bold" href="#">Home</a>
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
        
        <!----------------------------------------------->
        
        <div class="row">

        <!-- LATERAL IZQUIERDO: CATEGORIAS   -->
            <div class="col text-white d-none d-lg-block" id="latIzq">
                <h2 class="font-weight-bold text-center">Categorias</h2>
                <h6 class="text-center small"> Seleccione aquí la subcategoría a buscar</h6>
                <ul class="listaCat">
        <%
                for(CategoriaMenuDTO cat: categorias){
        %>
                    <li class="font-weight-bold">
                        <a class="text-white" data-toggle="collapse" href="#collapseCategoria<%=cat.getIdCategoria()%>" aria-expanded="false" aria-controls="collapseCategoria">
                            <%=cat.getNombreCategoria() %>
                        </a>
                        <ul class="listaSub collapse" id="collapseCategoria<%=cat.getIdCategoria()%>">
                        <%
                            for(SubcategoriaDTO subcat: cat.getSubcategoriaList()){
                        %>
                            <li class="font-italic font-weight-light">
                                <a class="text-white" href="./FiltrarMenu?subcatSelected=<%=subcat.getIdSubcategoria()%>">
                                    <%=subcat.getNombreSubcategoria()%>(<%=subcat.getNumProds()%>)
                                </a>
                            </li>
                        <%
                            }
                        %>    
                        </ul>    
                    </li>
        <%
                }
        %>    
                </ul>
            </div>
        
        <!-- CONTAINER -->
            <div id="centro">
                
            <!--BUSCADOR / FILTRO -->    
            <nav id="navbarBuscador" class="navbar navbar-expand-lg navbar-light shadow">
                  <form class="form-inline my-2 my-lg-0 w-100" action="./FiltrarMenu">
                    <div class="w-50 d-inline-block">
                        <div class="input-group mr-2">
                            <div class="input-group-prepend">
                                <select class="custom-select input-group-text" id="categoriaBuscador" name="catSelected" 
                                    <%if(subcatSelected!=null){%>disabled<%}%>>
                                    <option <%if(catSelected==null){%>selected<%}%> value="Todos"> Todos </option>
                <%
                    for(CategoriaMenuDTO c: categorias){
                %>
                                    <option <%if(catSelected!=null && c.getIdCategoria() == catSelected.getIdCategoria()){%>
                                            selected
                                       <%}%> 
                                            value="<%=c.getIdCategoria()%>"> <%=c.getNombreCategoria()%></option>
                <%
                    }
                %>
                                </select>
                            </div>
                <%
                    String prevBusqueda = (String) request.getAttribute("prevBusqueda");
                %>
                          <input name="busquedaFiltro" class="form-control mr-sm-2 w-75" type="search" placeholder="Busque su producto aqu&iacute;..." aria-label="Search" aria-describedby="basic-addon3"
                          <%if(prevBusqueda!=null){%>value="<%=prevBusqueda%>" <%}%>>
                        </div>
                        <%if(subcatSelected!=null){%>
                        <div class="ml-2">
                            <label class="text-secondary d-inline">Subcategoría: </label>
                            <span class="badge badge-info"><%=subcatSelected.getNombreSubcategoria()%></span>
                            <a href="./QuitarSubcategoria" class=" text-danger small font-weight-bold border border-danger px-1">Quitar</a>
                        </div>
                        <%}%>
                    </div>
                      
                    <div class="w-50 d-inline-block"> 
                        <span class="d-inline-block align-middle ml-1">Ordenar por:</span>
                      <select class="custom-select d-inline-block font-italic" name="orden">
                        <option <%if(orden.equals("Fecha")){%>selected<%}%> name="orden" value="Fecha">Fecha</option>
                        <option <%if(orden.equals("Precio")){%>selected<%}%> name="orden" value="Precio">Precio</option>
                        <option <%if(orden.equals("Valoracion")){%>selected<%}%> name="orden" value="Valoracion">Valoracion</option>
                      </select>
                      <button id="botonBuscar" class="d-inline-block btn btn-primary ml-2 my-2 my-sm-0" type="submit">Buscar</button>
                      
                    </div>
                     
                  </form>
                </nav>
                
        <%
            //Paginacion: Solo mostraremos 10 productos como maximo por pagina
            String str = request.getParameter("pag");
            int pag = 0;
            if(str!=null){
                pag = Integer.parseInt(str);
            }
            List<ProductoMenuDTO> mostrados = productos.subList(pag*10, Math.min(pag*10+10,productos.size()));
            for(ProductoMenuDTO p: mostrados){
                SubcategoriaDTO subP = p.getIdSubcategoria();
                CategoriaDTO catP = subP.getIdCategoria();
                String desc = null;
                if(p.getDescripcion().length()>200){
                    desc = p.getDescripcion().substring(0,200) + "...";
                }else{
                    desc = p.getDescripcion();
                }
                //Fecha subida
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
                String dateString = format.format(p.getFechaSubida());
                //Nota media
                int notaMedia = (p.getNotaMedia()).intValue()/2;
        %>
                <div id="cajaProducto" class="bg-light shadow" >
                    <div id="tituloProducto" class="h3" > 
                        <span class="ml-2"><%=p.getTitulo()%></span>
                        <span class="badge badge-danger"><%=catP.getNombreCategoria()%></span>  <!--Badge de cateogoria-->
                        <span class="badge badge-info"><%=subP.getNombreSubcategoria()%></span>  <!--Badge de subcategoria-->
                    </div>
                    <div id="cuerpoProducto" class="row text-center">
                        <img id="imagenProducto" src="<%=p.getFotoProducto()%>" class="col figure-img img-fluid rounded" alt="...">
                        <div id="descripcionProducto" class="col d-none d-md-block">
                            <label class="font-weight-bold">Descripción:</label>
                            <p> <%=desc %></p>
                        </div>
                        <div id="precioProducto" class="col">
                            <label class="font-weight-bold">Precio: </label>
                            <h5 class="font-weight-bold"> <%=p.getPrecio()%>€</h5>
                            
                            <!-- PUNTUACION CON ESTRELLAS-->
                            <form>
                              <label class="font-weight-bold">Valoraci&oacute;n:</label>
                              <p disabled class="valoracionMedia" id="mediaProducto<%=p.getIdProducto()%>">
                                <input id="radio1" type="radio" name="estrellas<%=p.getIdProducto()%>" value="5"
                                     <%if(notaMedia==5){%>checked<%}%> disabled><!--
                                --><label for="radio1">★</label><!--
                                --><input id="radio2" type="radio" name="estrellas<%=p.getIdProducto()%>" value="4"
                                     <%if(notaMedia==4){%>checked<%}%> disabled><!--
                                --><label for="radio2">★</label><!--
                                --><input id="radio3" type="radio" name="estrellas<%=p.getIdProducto()%>" value="3"
                                     <%if(notaMedia==3){%>checked<%}%> disabled><!--
                                --><label for="radio3">★</label><!--
                                --><input id="radio4" type="radio" name="estrellas<%=p.getIdProducto()%>" value="2"
                                     <%if(notaMedia==2){%>checked<%}%> disabled><!--
                                --><label for="radio4">★</label><!--
                                --><input id="radio5" type="radio" name="estrellas<%=p.getIdProducto()%>" value="1"
                                     <%if(notaMedia==1){%>checked<%}%> disabled><!--
                                --><label for="radio5">★</label>
                              </p>
                              <small>(<%=p.getNotaMedia()%>)</small>
                            </form>
                            <label class="font-weight-bold">Vendedor:</label>
                        <%if(p.getIdUsuario()!=null){%>
                            <span><%=p.getIdUsuario().getNombre()%></span>
                        <%}else{%>
                            <span class="text-secondary font-italic"> Anónimo </span>
                        <%}%>
                            <span class="d-block text-secondary"> <%= dateString %> </span>
                        </div >
                        <%if(p.getIdUsuario()==null || p.getIdUsuario().getIdUsuario()!=usuario.getIdUsuario()){ %>
                        <div id="divMas" class="col">
                            <a id="verMas" class="btn btn-warning text-white" href="./CargarProducto?idProducto=<%=p.getIdProducto()%>"  role="button" >Comprar</a>
                        </div>
                            <%}else{%><span class="font-weight-light align-middle m-5"> ¡Es tu producto!</span> <%}%>
                    </div>
                </div>
        <%
            }
        %> 
        <!-- PAGINACION-->
        <% 
            if(productos.size()>10){
            int nPags = productos.size()/10;
            if(productos.size()%10>0){ nPags++;} 
        %>
                <div class="nav justify-content-center">
                    <nav aria-label="...">
                        <ul class="pagination">

                          <li class="page-item <%if(pag==0){%> disabled <%}%>">
                            <a class="page-link" href="menu.jsp?pag=<%=pag-1%>" tabindex="-1">Previous</a>
                          </li>
                        <%
                              for(int i=1; i<=nPags; i++){
                        %>
                          <%if(i-1==pag){%>
                             <li class="page-item active">
                                 <a class="page-link" href="#"><%=i%><span class="sr-only">(current)</span></a>
                             </li>
                          <%}else{%>
                             <li class="page-item"><a class="page-link" href="menu.jsp?pag=<%=i-1%>"><%=i%></a></li>
                          <%}%>  
                        <%
                           } 
                        %>    
                          
                          <li class="page-item <%if(pag==nPags-1){%> disabled <%}%>">
                            <a class="page-link" href="menu.jsp?pag=<%=pag+1%>">Next</a>
                          </li>
                        </ul>
                     </nav>    
                </div>
        <%
            }
        %>
            </div>
        <!-- LATERAL DERECHO -->
        <!--
            <div class="col" id="latDer">
                <div class="m-2 p-2 text-white text-center" id="divPerfil">
                    <img id="imagenPerfil" src="<%=usuario.getFotoUsuario()%>" alt="Italian Trulli"/>
                    <h4 class="mb-0 pt-0"><%=usuario.getNombre()%></h4>
                    <small class="mt-0 pt-0"><%if(usuario.getIsAdmin()){%>Administrador<%}else{%>Usuario<%}%></small>
                </div>
            </div> -->
        
        </div>   
        
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
    </body>
</html>
