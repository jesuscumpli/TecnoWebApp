/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Subcategoria;
import tecnoweb.entity.Usuario;

/**
 *
 * @author Jesús
 */
@WebServlet(name = "FiltrarMenu", urlPatterns = {"/FiltrarMenu"})
public class FiltrarMenu extends HttpServlet {

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
        RequestDispatcher rd;
        Usuario user = (Usuario) session.getAttribute("usuario");
        if(user==null){
            response.sendRedirect("login.jsp");
        }else{
            
            //List<Producto> productos = (List<Producto>) session.getAttribute("productos");
            List<Producto> productos = (List<Producto>) session.getAttribute("productos");
            
            ///// FILTRO SUBCATEGORIA (LATERAL IZQUIERDO) *********
            String filtro_subcat =  request.getParameter("subcatSelected");
            
            if(filtro_subcat!=null && !filtro_subcat.isEmpty()){
                Integer codSubcat = (Integer) Integer.parseInt(filtro_subcat);
                Subcategoria subcatSelected = this.subcategoriaFacade.find(codSubcat);
                session.setAttribute("subcatSelected", subcatSelected);     //Actualizamos o introducimos subcategoria seleccionada
                session.setAttribute("catSelected", subcatSelected.getIdCategoria());
                productos = this.productoFacade.findBySubcategoria(codSubcat);
            }else{            ///// FILTRO BUSQUEDA (NAVBAR)  *************
                Subcategoria subcatSelected = (Subcategoria) session.getAttribute("subcatSelected");
                String strCat = request.getParameter("catSelected");
                
                //RESETEAMOS LOS PRODUCTOS DE SUBCATEGORIA (esto es para cuando se filtra desde Navbar)
                if(subcatSelected!=null){ //Si hay subcategoria seleccionada, reseteamos los productos de esa subcategoria
                    productos = this.productoFacade.findBySubcategoria(subcatSelected.getIdSubcategoria());
                    
                }else if(strCat!=null && !strCat.equals("Todos")){    //Si no hay subcategoria seleccionada, pero si hay categoria
                    Integer codCat = (Integer) Integer.parseInt(strCat);
                    Categoria catSelected = this.categoriaFacade.find(codCat);
                    session.setAttribute("catSelected", catSelected);
                    productos = this.productoFacade.findByCategoria(codCat);
                    
                }else{  //CARGAMOS TODOS
                    session.removeAttribute("catSelected");
                    productos = this.productoFacade.findAll();
                }
            }
            /////******************************
            
            // BUSQUEDA
            String filtroBusq = request.getParameter("busquedaFiltro");
            
            if(filtroBusq!=null && !filtroBusq.trim().isEmpty()){
                //Filtrar con operaciones privadas adicionales
                List<Producto> filtroTitulo = this.filtrarTitulo(productos,filtroBusq);         //TITULO
                List<Producto> filtroDesc = this.filtrarDescripcion(productos, filtroBusq);     //DESCRIPCION
                List<Producto> filtroClaves = this.filtrarPalabrasClave(productos, filtroBusq); //PALABRAS CLAVES
                //Union de los productos
                productos = this.unirFiltros(filtroTitulo,filtroDesc,filtroClaves);
                request.setAttribute("prevBusqueda", filtroBusq);
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
            this.productoFacade.ordenarProductos(orden, productos);
            session.setAttribute("productos", productos); //Actualizamos atributo
        }
        
        rd = request.getRequestDispatcher("menu.jsp");
        rd.forward(request, response); 
    }
    
    private List<Producto> filtrarTitulo(List<Producto> productos, String filtro){
        List<Producto> result = new ArrayList<>();
        for(Producto p: productos){
            if(p.getTitulo().toLowerCase().contains(filtro.toLowerCase())){
                result.add(p);
            }
        }
        return result;
    }
    
    private List<Producto> filtrarDescripcion(List<Producto> productos, String filtro){
        List<Producto> result = new ArrayList<>();
        for(Producto p: productos){
            if(p.getDescripcion().toLowerCase().contains(filtro.toLowerCase())){
                result.add(p);
            }
        }
        return result;
    }
    
    private List<Producto> filtrarPalabrasClave(List<Producto> productos, String filtro){
        List<Producto> result = new ArrayList<>();
        for(Producto p: productos){
            Iterator<Palabraclave> it = p.getPalabraclaveList().iterator();
            boolean found = false;
            while(found && it.hasNext()){
                Palabraclave clave = it.next();
                if(clave.getValor().equalsIgnoreCase(filtro)){
                    found = true;
                    result.add(p);
                }
            }
        }
        return result;
    }
    
    private List<Producto> unirFiltros(List<Producto> p1, List<Producto> p2, List<Producto> p3){
        Set<Producto> set = new HashSet<>();
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
