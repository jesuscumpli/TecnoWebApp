/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.admin;

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
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dto.ProductoDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Usuario;
import tecnoweb.service.ProductosService;

/**
 *
 * @author haylo
 */
@WebServlet(name = "BorrarProductoAdmin", urlPatterns = {"/BorrarProductoAdmin"})
public class BorrarProductoAdmin extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(BorrarUsuario.class.getName()); 
    
    @EJB
    private ProductosService productosService;
    
    //@EJB
    //private ProductoFacade productoFacade;
    
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
        
        ProductoMenuDTO producto;
        Integer str = Integer.parseInt(request.getParameter("idProducto"));
        producto = this.productosService.findByIdProducto(str);
        //producto = this.productoFacade.find(str);
        
        if(producto==null){
            LOG.log(Level.SEVERE, "No se ha encontrado el producto a borrar");
            response.sendRedirect("listadoProductosAdmin.jsp");
        }else {
            //this.productoFacade.remove(producto);
            this.productosService.remove(producto);
            
            RequestDispatcher rd = request.getRequestDispatcher("FiltrarMenu");
            rd.forward(request, response); 
        }
        
        /*
        String str = request.getParameter("idProducto");
        
        if (str == null) {
            LOG.log(Level.SEVERE, "No se ha encontrado el producto a borrar");
            response.sendRedirect("listadoProductosAdmin.jsp");            
        }else{
            producto = this.productoFacade.find(new Integer(str));
            if(producto==null){
                LOG.log(Level.SEVERE, "No se ha encontrado el producto a borrar");
                response.sendRedirect("listadoProductosAdmin.jsp");
            }else {
                this.productoFacade.remove(producto);
                response.sendRedirect("listadoProductosAdmin.jsp");   
            }
        }
        */     
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
