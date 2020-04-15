/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.productos;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Categoria;
import tecnoweb.service.CategoriasService;

/**
 *
 * @author alvar
 */
@WebServlet(name = "GuardarCategoria", urlPatterns = {"/GuardarCategoria"})
public class GuardarCategoria extends HttpServlet {
    
    @EJB
    private CategoriaFacade categoriaFacade;
    
    @EJB
    private CategoriasService categoriasService;

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
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if(usuario==null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        CategoriaMenuDTO categoria;
        String cat = request.getParameter("categoria");
        if (cat != null) {

            cat = new String(cat.getBytes("ISO-8859-1"),"UTF8");

        }
        //categoria = this.categoriaFacade.findByName(cat); ANTES DE HACERLO CON DTO
        categoria = this.categoriasService.findByName(cat);
        
        String nombre = request.getParameter("nombre");
        if (nombre != null && !nombre.isEmpty()) {
               nombre = new String(nombre.getBytes("ISO-8859-1"),"UTF8");
        }
        
        String descripcion = request.getParameter("descripcion");
        if (descripcion != null && !descripcion.isEmpty()) {
               descripcion = new String(descripcion.getBytes("ISO-8859-1"),"UTF8");
            }
        
        String precio = request.getParameter("precio");
        if (precio != null && !precio.isEmpty()) {
               precio = new String(precio.getBytes("ISO-8859-1"),"UTF8");
            }
        
        String palabrasClave = request.getParameter("palabrasClave");
        if (palabrasClave != null && !palabrasClave.isEmpty()) {
               palabrasClave = new String(palabrasClave.getBytes("ISO-8859-1"),"UTF8");
            }
        
        String url = request.getParameter("url");
        if (url != null && !url.isEmpty()) {
               url = new String(url.getBytes("ISO-8859-1"),"UTF8");
            }
       
        String productoID = request.getParameter("productoId");
        if (productoID != null && !productoID.isEmpty()) {
               productoID = new String(productoID.getBytes("ISO-8859-1"),"UTF8");
        }
        
        if (categoria != null) {
             request.setAttribute("categoria", categoria);
        }
        
        request.setAttribute("productoId", productoID);
        request.setAttribute("nombre", nombre);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("precio", precio);
        request.setAttribute("palabrasClave", palabrasClave);
        request.setAttribute("url", url);
        
        RequestDispatcher rd = request.getRequestDispatcher("anadirProducto.jsp");
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
