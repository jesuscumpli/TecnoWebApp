/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet;

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
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.PalabraclaveFacade;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Subcategoria;
import tecnoweb.entity.Usuario;

/**
 *
 * @author alvar
 */
@WebServlet(name = "GuardarProducto", urlPatterns = {"/GuardarProducto"})
public class GuardarProducto extends HttpServlet {
    @EJB
    private ProductoFacade productoFacade;
    
    @EJB 
    private SubcategoriaFacade subcategoriaFacade;
    
    @EJB
    private PalabraclaveFacade palabraClaveFacade;
    

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
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String status;
        
        if (usuario ==null) { // Se ha llamado al servlet sin haberse autenticado
            response.sendRedirect("login.jsp");            
        } else {
            Producto producto;
            boolean esCrearNuevo = false;
            Subcategoria subcategoria;
            
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String precio = request.getParameter("precio");
            String subcategoriaString = request.getParameter("subcategoria");
            String palabrasClave = request.getParameter("palabrasClave");
            String url = request.getParameter("url");
            
            nombre = new String(nombre.getBytes("ISO-8859-1"),"UTF8");
            descripcion = new String(descripcion.getBytes("ISO-8859-1"),"UTF8");
            subcategoriaString = new String(subcategoriaString.getBytes("ISO-8859-1"),"UTF8" );
            palabrasClave = new String(palabrasClave.getBytes("ISO-8859-1"),"UTF8");  
            url = new String(url.getBytes("ISO-8859-1"),"UTF8");
            
            
            if (nombre==null || nombre.isEmpty() || descripcion==null || descripcion.isEmpty() || precio==null || precio.isEmpty() || palabrasClave==null || palabrasClave.isEmpty()) {
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
                
                subcategoriaString = new String(subcategoriaString.getBytes("ISO-8859-1"),"UTF8");
                
                Subcategoria subcategoriaAux = this.subcategoriaFacade.findByName(subcategoriaString);
                request.setAttribute("subcategoria", subcategoriaAux);
                
                RequestDispatcher rd = request.getRequestDispatcher("anadirProducto.jsp");
                rd.forward(request, response);
            } else {            
            
                String str = request.getParameter("productoId");
                if (str == null || str.isEmpty()) { // Creación de un nuevo producto
                    producto = new Producto(0); 
                    esCrearNuevo = true;
                } else {
                    producto = this.productoFacade.find(new Integer(str));
                }

                producto.setTitulo(nombre);
                producto.setDescripcion(descripcion);
                producto.setPrecio(new Double(precio));
                producto.setFechaSubida(new Date(System.currentTimeMillis()));
                producto.setFotoProducto(url);
                subcategoria = this.subcategoriaFacade.findByName(subcategoriaString);
                producto.setIdSubcategoria(subcategoria);

                String[] tags = palabrasClave.trim().split(",");

                List<Palabraclave> listaClaves = new ArrayList<>();

                for (String valor : tags) {
                    Palabraclave p = palabraClaveFacade.findByValue(valor);
                    if (p == null) {        //Si no existe la creo
                        p = new Palabraclave(0);
                        p.setValor(valor);
                        this.palabraClaveFacade.create(p);
                    }
                    listaClaves.add(p);
                }
                producto.setPalabraclaveList(listaClaves);
                producto.setIdUsuario(usuario);



                if (esCrearNuevo) {
                    this.productoFacade.create(producto);
                } else {
                    this.productoFacade.edit(producto);
                }

                ///Actualizar productos
                int idUsuario = usuario.getIdUsuario();
                List<Producto> misProductos = this.productoFacade.findByIdUsuario(idUsuario);      
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
