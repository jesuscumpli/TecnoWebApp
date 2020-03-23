/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Jesús
 */
@WebServlet(name = "InicioSesionServlet", urlPatterns = {"/InicioSesionServlet"})
public class InicioSesionServlet extends HttpServlet {

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
        String email, pwd, status = null, goTo="menu.jsp";
        Usuario usuario;
        RequestDispatcher rd;
        
        email = request.getParameter("email");
        pwd = request.getParameter("password");
        
        if(email==null || pwd==null){
           status = "Faltan campos por rellenar, vuelva a intentarlo";
           request.setAttribute("status", status);
           goTo = "login.jsp";
           rd = request.getRequestDispatcher(goTo);
           rd.forward(request, response);
           return;
        }
        
        // comprobamos si el usuario está en la BD
        usuario = this.usuarioFacade.findByEmailUsuario(email);
        if (usuario == null) { // el usuario no está
           status = "El Email no está registrado en nuestra aplicación";
           request.setAttribute("status", status);
           goTo = "login.jsp";
        } else if (!pwd.equals(usuario.getPassword())) {  // el usuario está y pero la clave no es correcta
           status = "La contraseña es incorrecta, vuelva a intentarlo";
           request.setAttribute("status", status);
           goTo = "login.jsp";                       
        } else { // el usuario está y la clave es correcta
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario); // introducimos el usuario en la sesión para saber que está autenticado
        }
        
        rd = request.getRequestDispatcher(goTo);
        rd.forward(request, response);   
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
