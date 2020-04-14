/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.perfil;

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
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Usuario;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author DeuneB07
 */
@WebServlet(name = "EditarPerfilUsuario", urlPatterns = {"/EditarPerfilUsuario"})
public class EditarPerfilUsuario extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private UsuariosService usuariosService;
    
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
        UsuarioDTO usr;
        String statusEmail = "", statusPwdOrig = "", statusPwdNew = "", statusNombre = "", statusApellido = "", statusFechaNac = "";
        String goTo = "";
        
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
        } else {
           
            usr = (UsuarioDTO) session.getAttribute("usuario");
            String email, nombre, apellidos, pwdOrig, pwdNew, pwdNewTwo, photoURL;
            
            email = request.getParameter("email");
            nombre = request.getParameter("nombre");
            apellidos = request.getParameter("apellidos");
            pwdOrig = request.getParameter("passwordOrig");
            pwdNew = request.getParameter("passwordNew");
            pwdNewTwo = request.getParameter("passwordNewTwo");
            photoURL = request.getParameter("photoURL");
            
            boolean error = false;
            boolean changePass = !pwdOrig.equals("") || !pwdNew.equals("") || !pwdNewTwo.equals("");
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
            
            if(changePass){
                if(pwdOrig.trim().equals("") || !pwdOrig.equals(usr.getPassword())) statusPwdOrig = "Contrasena no Coincide con la Original.";
                if(!pwdNew.trim().equals(pwdNewTwo)) statusPwdNew = "Las contrasenas no coinciden.";
                if(pwdNew.trim().equals("")) statusPwdNew = "No puede introducir una contrasena vacia.";
            }
            
            if(!statusEmail.equals("") || !statusNombre.equals("") || !statusApellido.equals("") 
                    || !statusPwdOrig.equals("") || !statusPwdNew.equals("") || !statusFechaNac.equals("")) error = true;
            
            if(!error){
               
                usr.setEmailUsuario(new String(email.getBytes("ISO-8859-1"),"UTF8"));
                usr.setNombre(new String(nombre.getBytes("ISO-8859-1"),"UTF8"));
                usr.setApellidos(new String(apellidos.getBytes("ISO-8859-1"),"UTF8"));
                usr.setFechaNac(fecha);
                if(changePass) usr.setPassword(new String(pwdNew.getBytes("ISO-8859-1"),"UTF8"));
                if(changePhoto) usr.setFotoUsuario(new String(photoURL.getBytes("ISO-8859-1"),"UTF8"));
                
                //this.usuarioFacade.edit(usr);
                this.usuariosService.createOrUpdate(new Integer(usr.getIdUsuario()), usr.getEmailUsuario(), usr.getNombre(), 
                        usr.getApellidos(), usr.getFechaNac(), usr.getPassword(), usr.getFotoUsuario(), usr.getIsAdmin());
                
                goTo = "perfilUsuario.jsp";
            } else { 
                request.setAttribute("statusEmail", statusEmail);
                request.setAttribute("statusNombre", statusNombre);
                request.setAttribute("statusApellido", statusApellido);
                request.setAttribute("statusPwdOrig", statusPwdOrig);
                request.setAttribute("statusPwdNew", statusPwdNew);
                request.setAttribute("statusFechaNac", statusFechaNac);
                //request.setAttribute("statusPhoto", statusPhoto);
                goTo = "editarPerfilUsuario.jsp";
            }
            
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
