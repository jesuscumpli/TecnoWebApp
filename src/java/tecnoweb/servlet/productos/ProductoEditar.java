/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.productos;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.ProductoDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Producto;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.ProductosService;

/**
 *
 * @author alvar
 */
@WebServlet(name = "ProductoEditar", urlPatterns = {"/ProductoEditar"})
public class ProductoEditar extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ProductoEditar.class.getName());
    
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private ProductosService productosService;
    @EJB
    private CategoriasService categoriasService;
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
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String str;
        ProductoMenuDTO producto;
        
        if (session.getAttribute("usuario")==null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {        
            str = request.getParameter("id");
            if (str == null) {
                LOG.log(Level.SEVERE, "No se ha encontrado el cliente a editar");
                response.sendRedirect("menu.jsp");            
            } else {
               // producto = this.productoFacade.find(new Integer(str)); ANTES SIN DTO
               producto = this.productosService.find(new Integer(str));
                if (producto == null) { //Esta situación no debería darse
                    LOG.log(Level.SEVERE, "No se ha encontrado el cliente a editar");
                    response.sendRedirect("menu.jsp");
                } else {                    
                    request.setAttribute("producto", producto);
                    CategoriaMenuDTO categoriaSeleccionada = this.categoriasService.findById(producto.getIdSubcategoria().getIdCategoria().getIdCategoria());
                    session.setAttribute("categoriaSeleccionada", categoriaSeleccionada);
                    
                    RequestDispatcher rd = request.getRequestDispatcher("anadirProducto.jsp");
                    rd.forward(request, response);
                }       
            }
        }
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
