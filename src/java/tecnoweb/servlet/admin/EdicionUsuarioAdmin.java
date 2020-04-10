/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
 * @author haylo
 */
@WebServlet(name = "EdicionUsuarioAdmin", urlPatterns = {"/EdicionUsuarioAdmin"})
public class EdicionUsuarioAdmin extends HttpServlet {

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
        Usuario usr;
        String statusEmail = "", statusNombre = "", statusApellido = "", statusFechaNac = "";
        String goTo = "";
        
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
        } else {
           
            Integer idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

            usr = this.usuarioFacade.find(idUsuario);
            String email, nombre, apellidos, photoURL;
            
            email = request.getParameter("email");
            nombre = request.getParameter("nombre");
            apellidos = request.getParameter("apellidos");
            photoURL = request.getParameter("photoURL");
            
            boolean error = false;
            boolean changePhoto = (photoURL != null) && !photoURL.trim().equals("");
            
            if(!email.contains("@") || email.trim().equals("")) statusEmail = "Correo no valido.";
            if(nombre.trim().equals("")) statusNombre = "Nombre no valido.";
            if(apellidos.trim().equals("")) statusApellido = "Campo Apellidos no valido.";
            
            
            String[] datos = request.getParameter("fechaNac").split("[- ]");
            Calendar c = Calendar.getInstance();
            c.set(Integer.parseInt(datos[0]),Integer.parseInt(datos[1])-1, Integer.parseInt(datos[2]));
            Date fecha = c.getTime();
            Date now = new Date();
            
            long diffInMillies = Math.abs(now.getTime() - fecha.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            long years = diff/365;

            if(!now.after(fecha)) statusFechaNac = "No puedes nacer despues de hoy";
            if(years<18) statusFechaNac = "No cumple con la mayoria de edad";
            
            if(!statusEmail.equals("") || !statusNombre.equals("") || !statusApellido.equals("") 
                     || !statusFechaNac.equals("")) error = true;
            
            if(!error){
               
                usr.setEmailUsuario(new String(email.getBytes("ISO-8859-1"),"UTF8"));
                usr.setNombre(new String(nombre.getBytes("ISO-8859-1"),"UTF8"));
                usr.setApellidos(new String(apellidos.getBytes("ISO-8859-1"),"UTF8"));
                usr.setFechaNac(fecha);
                if(changePhoto) usr.setFotoUsuario(new String(photoURL.getBytes("ISO-8859-1"),"UTF8"));
                
                goTo = "ListadoUsuarioAdmin";
            } else {
                
                request.setAttribute("statusEmail", statusEmail);
                request.setAttribute("statusNombre", statusNombre);
                request.setAttribute("statusApellido", statusApellido); 
                request.setAttribute("statusFechaNac", statusFechaNac);
                //request.setAttribute("statusPhoto", statusPhoto);
                goTo = "edicionUsuarioAdmin.jsp";
            }
           
            this.usuarioFacade.edit(usr);
            
            RequestDispatcher rd = request.getRequestDispatcher(goTo);
            rd.forward(request, response);
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
