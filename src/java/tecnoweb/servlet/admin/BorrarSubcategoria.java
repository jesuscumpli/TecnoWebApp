/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.admin;

import java.io.IOException;
import java.util.ArrayList;
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
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Subcategoria;

/**
 *
 * @author haylo
 */
@WebServlet(name = "BorrarSubcategoria", urlPatterns = {"/BorrarSubcategoria"})
public class BorrarSubcategoria extends HttpServlet {

    @EJB
    private ProductoFacade productoFacade;

    private static final Logger LOG = Logger.getLogger(BorrarUsuario.class.getName()); 

    @EJB
    private SubcategoriaFacade subcategoriaFacade;
    
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
        
        HttpSession session = request.getSession();
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
        if(usuario==null){
            response.sendRedirect("login.jsp");
            return;
        }
        
        Subcategoria subcategoria;       
        Integer str = Integer.parseInt(request.getParameter("idSubcategoria"));
      
        subcategoria = this.subcategoriaFacade.find(str);
        if(subcategoria==null){
            LOG.log(Level.SEVERE, "No se ha encontrado el usuario a borrar");
            response.sendRedirect("ListadoCategoriasAdmin");
        }else {
            //Movemos los productos de dentro a la subcategoria Por Definir
            Subcategoria porDefinir = this.subcategoriaFacade.findByName("Por definir");
            for(Producto p: subcategoria.getProductoList()){
                p.setIdSubcategoria(porDefinir);
                this.productoFacade.edit(p);
            }
            subcategoria.setProductoList(new ArrayList<>());
            String idesito = subcategoria.getIdCategoria().toString();
            request.setAttribute("idCategoria", idesito);
            this.subcategoriaFacade.remove(subcategoria);
            
            
            RequestDispatcher rd = request.getRequestDispatcher("ListadoConsultarSubcategorias?idCategoria="+subcategoria.getIdCategoria().getIdCategoria());
            //RequestDispatcher rd = request.getRequestDispatcher("listadoCategoriasAdmin.jsp");
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
