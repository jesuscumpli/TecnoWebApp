/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.perfil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.entity.Usuario;

/**
 *
 * @author DeuneB07
 */
@WebServlet(name = "DarDeBajaUsuario", urlPatterns = {"/DarDeBajaUsuario"})
public class DarDeBajaUsuario extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;
    
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
        Usuario usrBaja, usrSession;
        String goTo = "";
        
        usrSession = (Usuario) session.getAttribute("usuario");
        
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
        } else {
           
            String idUsuario;
            if(request.getAttribute("idUsuario") == null) idUsuario = usrSession.getIdUsuario().toString();
            else idUsuario = (String) request.getAttribute("idUsuario");
            
            if(idUsuario == null){ //No se ha encontrado el parametro
                response.sendRedirect("perfilUsuario.jsp");
            } else {
                usrBaja = this.usuarioFacade.find(new Integer(idUsuario));
                if(usrBaja.equals(usrSession)){
                    goTo ="CerrarSesion";
                } else {
                    goTo = "menuAdmin.jsp";
                }
                this.usuarioFacade.remove(usrBaja);
                response.sendRedirect(goTo);
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
