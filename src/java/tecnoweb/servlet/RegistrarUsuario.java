/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.entity.Usuario;

/**
 *
 * @author Jesús
 */
@WebServlet(name = "RegistrarUsuario", urlPatterns = {"/RegistrarUsuario"})
public class RegistrarUsuario extends HttpServlet {

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

        String goTo="menu.jsp",status,email,pwd;
        
        Usuario nuevo;
        RequestDispatcher rd;
        
        email = request.getParameter("email");
        pwd = request.getParameter("password1");
        
        // comprobamos si el usuario está en la BD
        nuevo = this.usuarioFacade.findByEmailUsuario(email);
        if (nuevo == null) { // el usuario no está, es correcto
            String pwd2 = request.getParameter("password2");
            if(!pwd.equals(pwd2)){
                goTo="registro.jsp";
                status="Las contraseñas no coinciden";
            }else{
                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                String fechaNac = request.getParameter("fechaNac");
                String url = request.getParameter("imagen");
                
                if(fechaNac==null){
                    goTo="registro.jsp";
                    status="Fecha de nacimiento no introducida";
                }else{
                    
                    Date fecha;
                    fecha = new Date(2000);
                    
                    nuevo = new Usuario(5,email,pwd,fecha,false);
                    
                    if(nombre!=null && !nombre.trim().equals("")){
                        nuevo.setNombre(nombre.trim());
                    }
                    if(apellidos!=null && !apellidos.trim().equals("")){
                        nuevo.setApellidos(apellidos.trim());
                    }
                    
                    /*Añadir foto*/
                    
                    /*Insertar a la Base de Datos*/
                    usuarioFacade.create(nuevo);
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", nuevo); // introducimos el nuevo usuario en la sesión para saber que está autenticado
                }
            }                      
        } else { // el usuario está ya registrado
            goTo="registro.jsp";
            status="El email ya está registrado en la aplicación";
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
