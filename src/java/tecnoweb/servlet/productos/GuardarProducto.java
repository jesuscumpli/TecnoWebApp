/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.productos;

import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.SubcategoriaFacade;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import tecnoweb.dao.PalabraclaveFacade;
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.dto.PalabraclaveDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Subcategoria;
import tecnoweb.entity.Usuario;
import tecnoweb.service.PalabrasclaveService;
import tecnoweb.service.ProductosService;
import tecnoweb.service.SubcategoriasService;

/**
 *
 * @author alvar
 */
@WebServlet(name = "GuardarProducto", urlPatterns = {"/GuardarProducto"})
public class GuardarProducto extends HttpServlet {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private ProductoFacade productoFacade;
    
    @EJB
    private ProductosService productosService;
    
    @EJB 
    private SubcategoriaFacade subcategoriaFacade;
    
    @EJB
    private SubcategoriasService subcategoriasService;
    
    @EJB
    private PalabraclaveFacade palabraClaveFacade;
    
    @EJB
    private PalabrasclaveService palabrasClaveService;
    

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
        String status;
        
        if (usuario ==null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {
            ProductoMenuDTO producto;
            boolean esCrearNuevo = false;
            SubcategoriaDTO subcategoria;
            
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String precio = request.getParameter("precio");
            String subcategoriaString = request.getParameter("subcategoria");
            String palabrasClave = request.getParameter("palabrasClave");
            String url = request.getParameter("url");
            String productoId = request.getParameter("productoId");
            
            nombre = new String(nombre.getBytes("ISO-8859-1"),"UTF8");
            descripcion = new String(descripcion.getBytes("ISO-8859-1"),"UTF8");
            subcategoriaString = new String(subcategoriaString.getBytes("ISO-8859-1"),"UTF8" );
            palabrasClave = new String(palabrasClave.getBytes("ISO-8859-1"),"UTF8");  
            url = new String(url.getBytes("ISO-8859-1"),"UTF8");
            productoId = new String(productoId.getBytes("ISO-8859-1"),"UTF8");
            
            if (nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || palabrasClave.isEmpty()) {
                if (precio.isEmpty()) {
                    status = "El precio debe ser un valor numérico";
                } else {
                    status = "Faltan campos por rellenar";
                }
                
                request.setAttribute("status",status);
                request.setAttribute("nombre", nombre);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("precio", precio);
                request.setAttribute("palabrasClave", palabrasClave);
                request.setAttribute("url", url);
                request.setAttribute("productoId", productoId);
                
                subcategoriaString = new String(subcategoriaString.getBytes("ISO-8859-1"),"UTF8");
                
                SubcategoriaDTO subcategoriaAux = this.subcategoriasService.findByName(subcategoriaString);
                request.setAttribute("subcategoria", subcategoriaAux);
                
                RequestDispatcher rd = request.getRequestDispatcher("anadirProducto.jsp");
                rd.forward(request, response);
            } else {            
            
                String str = request.getParameter("productoId");
                this.productosService.createOrUpdate(productoId, nombre, descripcion, precio, url, subcategoriaString, palabrasClave, usuario.getIdUsuario());
 
                ///Actualizar productos
                int idUsuario = usuario.getIdUsuario();
                //List<Producto> misProductos = this.productoFacade.findByIdUsuario(idUsuario);      ANTES DEL DTO
                List<ProductoMenuDTO> misProductos = this.productosService.findByIdUsuario(idUsuario);
                session.setAttribute("misProductos", misProductos);
                response.sendRedirect("listadoProducto.jsp"); 
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
