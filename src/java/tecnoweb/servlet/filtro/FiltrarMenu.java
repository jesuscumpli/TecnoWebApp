/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.filtro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tecnoweb.classes.Filtro;
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.dto.CategoriaDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Subcategoria;
import tecnoweb.entity.Usuario;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.ProductosService;
import tecnoweb.service.SubcategoriasService;

/**
 *
 * @author Jesús
 */
@WebServlet(name = "FiltrarMenu", urlPatterns = {"/FiltrarMenu"})
public class FiltrarMenu extends HttpServlet {

    @EJB
    private CategoriasService categoriasService;

    @EJB
    private SubcategoriasService subcategoriasService;

    @EJB
    private ProductosService productosService;

    @EJB
    private CategoriaFacade categoriaFacade;

    @EJB
    private SubcategoriaFacade subcategoriaFacade;

    @EJB
    private ProductoFacade productoFacade;
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        UsuarioDTO user = (UsuarioDTO) session.getAttribute("usuario");
        if(user==null){
            response.sendRedirect("login.jsp");
            return;
        }else{
            List<ProductoMenuDTO> productos;
            
///// FILTRO SUBCATEGORIA (SI PULSAMOS EN EL LATERAL IZQUIERDO) ******************************************
            String filtro_subcat =  request.getParameter("subcatSelected");
            
            if(filtro_subcat!=null && !filtro_subcat.isEmpty()){
                Integer codSubcat = (Integer) Integer.parseInt(filtro_subcat);
                //Subcategoria subcatSelected = this.subcategoriaFacade.find(codSubcat);    //ANTES, SIN USAR DTO
                SubcategoriaDTO subcatSelected = this.subcategoriasService.find(codSubcat);
                session.setAttribute("subcatSelected", subcatSelected);                //Actualizamos o introducimos subcategoria seleccionada
                session.setAttribute("catSelected", subcatSelected.getIdCategoria());   //Actualizamos la categoria seleccionada a la misma que subcategoria
                //productos = this.productoFacade.findBySubcategoria(codSubcat);        //ANTES, SIN USAR DTO
                productos = this.productosService.findBySubcategoriaMenuDTO(codSubcat);
                
///// FILTRO BUSQUEDA ( SI PULSAMOS EN EL SEARCH DEL NAVBAR CENTRAL) ************************************+
            }else{           
                SubcategoriaDTO subcatSelected = (SubcategoriaDTO) session.getAttribute("subcatSelected");
                String strCat = request.getParameter("catSelected");
                
            //RESETEAMOS LOS PRODUCTOS DE SUBCATEGORIA 
                if(subcatSelected!=null){ //Si hay subcategoria seleccionada, reseteamos los productos de esa subcategoria
                    //productos = this.productoFacade.findBySubcategoria(subcatSelected.getIdSubcategoria());       //ANTES, SIN USAR DTO
                    productos = this.productosService.findBySubcategoriaMenuDTO(subcatSelected.getIdSubcategoria());
                    
                }else if(strCat!=null && !strCat.equals("Todos")){    //Si no hay subcategoria seleccionada, pero si hay categoria reseteamos productos con la categoria
                    Integer codCat = (Integer) Integer.parseInt(strCat);
                    //Categoria catSelected = this.categoriaFacade.find(codCat);        //ANTES, SIN USAR DTO
                    CategoriaDTO catSelected = this.categoriasService.find(codCat);
                    session.setAttribute("catSelected", catSelected);
                    //productos = this.productoFacade.findByCategoria(codCat);      //ANTES, SIN USAR DTO
                    productos = this.productosService.findByCategoriaMenuDTO(codCat);
                    
                }else{  //CARGAMOS TODOS (Sólo es posible cuando catSelected es null o bien se ha seleccionado como TODOS)
                    session.removeAttribute("catSelected");
                    //productos = this.productoFacade.findAll();                //ANTES, SIN USAR DTO
                    productos = this.productosService.findAllMenuDTO();
                }
            }

            
            // BUSQUEDA (Search)
            /*
                Lógica de la búsqueda: 
                        - Buscar por títulos
                        - Buscar por descripción
                        - Buscar por palabras claves
                        - Unir las tres búsquedas
            */
            String filtroBusq = request.getParameter("busquedaFiltro");
            
            if(filtroBusq!=null && !filtroBusq.trim().isEmpty()){
                //Filtrar con operaciones staticas adicionales
                
                //List<Producto> filtroTitulo = Filtro.filtrarTitulo(productos,filtroBusq);                     //TITULO (Antes)
                List<ProductoMenuDTO> filtroTitulo = Filtro.filtrarTituloMenuDTO(productos, filtroBusq);        //TITULO
                //List<Producto> filtroDesc = Filtro.filtrarDescripcion(productos, filtroBusq);                 //DESCRIPCION (Antes)
                List<ProductoMenuDTO> filtroDesc = Filtro.filtrarDescripcionMenuDTO(productos, filtroBusq);     //DESCRIPCION
                //List<Producto> filtroClaves = Filtro.filtrarPalabrasClave(productos, filtroBusq);             //PALABRAS CLAVES (Antes)
                List<ProductoMenuDTO> filtroClaves = Filtro.filtrarPalabrasClaveMenuDTO(productos, filtroBusq); //PALABRAS CLAVES
                
                //Union de los productos
                //productos = this.unirFiltros(filtroTitulo,filtroDesc,filtroClaves);           //Método privado (Antes)
                productos = this.unirFiltrosMenuDTO(filtroTitulo, filtroDesc, filtroClaves);    //Método privado
                //request.setAttribute("prevBusqueda", filtroBusq);
            }
            
            //ORDEN
            String filtroOrden = request.getParameter("orden");
            String orden = (String) session.getAttribute("orden");
            
            if(filtroOrden!=null && !filtroOrden.equals(orden)){    //Si ha cambiado el filtro de ordenar
                orden = filtroOrden;
                session.setAttribute("orden", orden);
            }
            
            //ORDENAR POR: *******************
            //if(orden==null || orden.isEmpty()){orden = "Fecha";}  No debe ocurrir nunca
            //Filtro.ordenarProductos(orden, productos);                    //ANTES
            Filtro.ordenarProductosMenuDTO(orden, productos);
            session.setAttribute("productos", productos);                   //Actualizamos atributo productos
        }
        
        //rd = request.getRequestDispatcher("menu.jsp");
        //rd.forward(request, response);
        String filtroBusq = request.getParameter("busquedaFiltro");
        if(user.getIsAdmin()){
            if(filtroBusq!=null && !filtroBusq.trim().isEmpty()){
                response.sendRedirect("listadoProductosAdmin.jsp?prevBusqueda="+filtroBusq);
            }else{
                response.sendRedirect("listadoProductosAdmin.jsp");
            }
        }else{
            if(filtroBusq!=null && !filtroBusq.trim().isEmpty()){
                response.sendRedirect("menu.jsp?prevBusqueda="+filtroBusq); 
            }else{
                response.sendRedirect("menu.jsp");
            }
        }
    }
    
    private List<Producto> unirFiltros(List<Producto> p1, List<Producto> p2, List<Producto> p3){
        Set<Producto> set = new HashSet<>();
        set.addAll(p1);
        set.addAll(p2);
        set.addAll(p3);
        return new ArrayList<>(set);
    }
    
    private List<ProductoMenuDTO> unirFiltrosMenuDTO(List<ProductoMenuDTO> p1, List<ProductoMenuDTO> p2, List<ProductoMenuDTO> p3){
        Set<ProductoMenuDTO> set = new HashSet<>();
        set.addAll(p1);
        set.addAll(p2);
        set.addAll(p3);
        return new ArrayList<>(set);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
