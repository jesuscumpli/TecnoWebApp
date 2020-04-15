/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.admin;

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
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Usuario;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author haylo
 */
@WebServlet(name = "BorrarUsuario", urlPatterns = {"/BorrarUsuario"})
public class BorrarUsuario extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(BorrarUsuario.class.getName());    
    
    @EJB
    private UsuariosService usuariosService;
    
    //@EJB
    //private UsuarioFacade usuarioFacade;
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
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if(usuario==null){
            response.sendRedirect("login.jsp");
            return;
        }        
        
        UsuarioDTO userB;
        String str = request.getParameter("id");        

        if (str == null) {
            LOG.log(Level.SEVERE, "No se ha encontrado el usuario a borrar");
            response.sendRedirect("ListadoUsuarioAdmin");            
        }else{
            userB = this.usuariosService.findByIdDTO(new Integer(str));
            if(userB==null){
                LOG.log(Level.SEVERE, "No se ha encontrado el usuario a borrar");
                response.sendRedirect("ListadoUsuarioAdmin");
            }else {
                this.usuariosService.remove2(userB);
                response.sendRedirect("ListadoUsuarioAdmin");   
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
