/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Usuario;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author haylo
 */
@WebServlet(name = "AnadirUsuario", urlPatterns = {"/AnadirUsuario"})
public class AnadirUsuario extends HttpServlet {

    @EJB
    private UsuariosService usuariosService;
    
    //@EJB
    //private UsuarioFacade usuarioFacade;
    
    private static final Logger LOG = Logger.getLogger(AnadirUsuario.class.getName());
    
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

        String goTo="ListadoUsuarioAdmin",status=null,email,pwd;
        
        UsuarioDTO nuevo;
        RequestDispatcher rd;
        
        email = request.getParameter("email");
        pwd = request.getParameter("password1");
        
        ///////////////
        String isAdmin = request.getParameter("isAdmin");
        boolean is = false;
        ///////////////
        
       try{
            if(email==null || email.isEmpty()) throw new RuntimeException("Faltan parámetros: email");
            if(pwd==null || pwd.isEmpty()) throw new RuntimeException("Faltan parámetros: contraseña");
             
            // comprobamos si el usuario está en la BD
            nuevo = this.usuariosService.findByEmailUsuario(email);
            if (nuevo == null) { // el usuario no está, es correcto
                String pwd2 = request.getParameter("password2");
                if(!pwd.equals(pwd2)){
                    goTo="registroUsuarioAdmin.jsp";
                    status="Las contraseñas no coinciden";
                }else{
                    //nuevo = new Usuario(0);

                    String nombre = request.getParameter("nombre");
                    String apellidos = request.getParameter("apellidos");
                    String fechaNac = request.getParameter("fechaNac");
                    String url = request.getParameter("url");

                    if(fechaNac==null){
                        goTo="registroUsuarioAdmin.jsp";
                        status="Fecha de nacimiento no introducida";
                    }else{

                        //Fecha
                        String[] datos = request.getParameter("fechaNac").split("[- ]");
                        Calendar c = Calendar.getInstance();
                        c.set(Integer.parseInt(datos[0]),Integer.parseInt(datos[1])-1, Integer.parseInt(datos[2]));
                        Date fecha = c.getTime();
                        Date now = new Date();
                        long diffInMillies = Math.abs(now.getTime() - fecha.getTime());
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        long years = diff/365;
                        
                        if(!now.after(fecha)){
                            throw new RuntimeException("Fecha de nacimiento incorrecta");
                        }
                        if(years<18){
                            throw new RuntimeException("Debes ser mayor de 18 años.");
                        }

                        pwd = new String(pwd.getBytes("ISO-8859-1"),"UTF8");
                        
                        ///////////////
                        if(isAdmin.equals("Si")){
                            is=true;
                        }
                        ///////////////
                        
                        //Creamos usuario nuevo
                        //nuevo = new Usuario(0,email,pwd,fecha,is);

                        //Nombre
                        if(nombre!=null && !nombre.trim().equals("")){
                            nombre = new String(nombre.getBytes("ISO-8859-1"),"UTF8");
                            //nuevo.setNombre(nombre.trim());
                        }

                        //Apellidos
                        if(apellidos!=null && !apellidos.trim().equals("")){
                            apellidos = new String(apellidos.getBytes("ISO-8859-1"),"UTF8");
                            //nuevo.setApellidos(apellidos.trim());
                        }

                        /*Añadir foto*/
                        if(url==null || url.trim().equals("")){
                            url="https://benidorm.org/wp-content/img/cabecera/perfil-anonimo.jpg";
                        }
                        //nuevo.setFotoUsuario(url);

                        /*Insertar a la Base de Datos*/
                        //this.usuariosService.create(nuevo);
                        this.usuariosService.createOrUpdate(0,email,nombre,apellidos,fecha,pwd,url,is);

                    }
                }                      
            } else { // el usuario está ya registrado
                goTo="registroUsuarioAdmin.jsp";
                status="El email ya está registrado en la aplicación";
            }
        }catch(RuntimeException e){
            goTo="registroUsuarioAdmin.jsp";
            status=e.getMessage();
        }finally{
            if(status!=null) request.setAttribute("status", status);
            rd = request.getRequestDispatcher(goTo);
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
