/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.inicio;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Usuario;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author Jesús
 */
@WebServlet(name = "RegistrarUsuario", urlPatterns = {"/RegistrarUsuario"})
public class RegistrarUsuario extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private UsuariosService usuarioService;
    
    private static final Logger LOG = Logger.getLogger(RegistrarUsuario.class.getName());
    
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

        String goTo="CargarMenu",status=null;
        
        UsuarioDTO nuevo;
        RequestDispatcher rd;
        
        String email = request.getParameter("email");
        String pwd = request.getParameter("password1");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String fechaNac = request.getParameter("fechaNac");
        String url = request.getParameter("url");
        
        try{
            //Lanzar excepciones
            if(email==null || email.isEmpty()) throw new RuntimeException("Faltan parámetros: email");
            if(pwd==null || pwd.isEmpty()) throw new RuntimeException("Faltan parámetros: contraseña");
            if(nombre==null || nombre.isEmpty()) throw new RuntimeException("Faltan parámetros: nombre");  //Obligamos a que pongan nombre, aunque en la bd es nulleable
            if(apellidos==null || apellidos.isEmpty()) throw new RuntimeException("Faltan parámetros: apellidos");
            if(fechaNac==null || fechaNac.isEmpty()) throw new RuntimeException("Faltan parámetros: fecha nacimiento");
             
            // comprobamos si el usuario está en la BD
            //nuevo = this.usuarioFacade.findByEmailUsuario(email);
            nuevo = this.usuarioService.findByEmailUsuario(email);
            if (nuevo == null) { // el usuario no está, es correcto
                String pwd2 = request.getParameter("password2");
                if(!pwd.equals(pwd2)){
                    goTo="registro.jsp";
                    status="Las contraseñas no coinciden";
                }else{
                    
                    nombre = new String(nombre.getBytes("ISO-8859-1"),"UTF8");
                    email = new String(email.getBytes("ISO-8859-1"),"UTF8");
                    pwd = new String(pwd.getBytes("ISO-8859-1"),"UTF8");
                    apellidos = new String(apellidos.getBytes("ISO-8859-1"),"UTF8");
                    
                    //Fecha
                    String[] datos = request.getParameter("fechaNac").split("[- ]");
                    Calendar c = Calendar.getInstance();
                    c.set(Integer.parseInt(datos[0]),Integer.parseInt(datos[1])-1, Integer.parseInt(datos[2]));
                    Date fecha = c.getTime();
                    
                    //Comprobar mayoría de edad
                    Date now = new Date();
                    long diffInMillies = Math.abs(now.getTime() - fecha.getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    long years = diff/365;

                    if(fecha.after(now)){
                        throw new RuntimeException("Fecha de nacimiento incorrecta");
                    }
                    if(years<18){
                        throw new RuntimeException("Debes ser mayor de 18 años.");
                    }
                    
                    //Creamos usuario nuevo
                    //nuevo = new Usuario(0,email,pwd,fecha,false);     //Antes, sin usar DTO

                    //Nombre
                    //nuevo.setNombre(nombre.trim());

                    //Apellidos
                    //nuevo.setApellidos(apellidos.trim());

                    /*Añadir foto*/
                    if(url==null || url.trim().equals("")){
                        url="https://benidorm.org/wp-content/img/cabecera/perfil-anonimo.jpg";  //Foto por defecto
                    }
                    //nuevo.setFotoUsuario(url);

                    /*Insertar a la Base de Datos*/
                    //this.usuarioFacade.create(nuevo);
                    this.usuarioService.createOrUpdate(0, email, nombre, apellidos, fecha, pwd, url, false);
                    nuevo = this.usuarioService.findByEmailUsuario(email);
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", nuevo);     // introducimos el nuevo usuario en la sesión para saber que está autenticado

                }                      
            } else { // el usuario está ya registrado
                goTo="registro.jsp";
                status="El email ya está registrado en la aplicación";
            }
        }catch(RuntimeException e){
            goTo="registro.jsp";
            status=e.getMessage();
        }finally{
            request.setAttribute("status", status);
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
