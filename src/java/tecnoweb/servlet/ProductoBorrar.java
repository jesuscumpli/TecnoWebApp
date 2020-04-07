/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Usuario;

/**
 *
 * @author alvar
 */
@WebServlet(name = "ProductoBorrar", urlPatterns = {"/ProductoBorrar"})
public class ProductoBorrar extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ProductoBorrar.class.getName());
    
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
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String str;
        Producto producto;
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {        
            str = request.getParameter("id");
            if (str == null) {
                LOG.log(Level.SEVERE, "No se ha encontrado el producto a borrar");
                response.sendRedirect("menu.jsp");            
            } else {
                producto = this.productoFacade.find(new Integer(str));
                if (producto == null) { //Esta situación no debería darse
                    LOG.log(Level.SEVERE, "No se ha encontrado el producto a borrar");
                    response.sendRedirect("menu.jsp");
                } else {
                    this.productoFacade.remove(producto);
                    
                    ////////////Actualizar pagina
                    int idUsuario = u.getIdUsuario();
                    List<Producto> misProductos = this.productoFacade.findByIdUsuario(idUsuario);      
                    session.setAttribute("misProductos", misProductos);
                    
                    response.sendRedirect("listadoProducto.jsp");   
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
