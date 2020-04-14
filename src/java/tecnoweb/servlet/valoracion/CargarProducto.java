/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.valoracion;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.ValoracionFacade;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Usuario;
import tecnoweb.entity.Valoracion;
import tecnoweb.service.ProductosService;
import tecnoweb.service.ValoracionesService;

/**
 *
 * @author luisr
 */
@WebServlet(name = "CargarProducto", urlPatterns = {"/CargarProducto"})
public class CargarProducto extends HttpServlet {

    @EJB
    private ValoracionesService valoracionesService;
    
    //@EJB
    //private ValoracionFacade valoracionFacade;

    
    //@EJB
    //private ProductoFacade productoFacade;
    
    @EJB
    private ProductosService productosService;

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
        
        HttpSession sesion = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd;
        
        int idProducto=Integer.parseInt(request.getParameter("idProducto"));
        UsuarioDTO usuario= (UsuarioDTO)sesion.getAttribute("usuario");
        int idUsuario =usuario.getIdUsuario();
        ProductoMenuDTO producto = productosService.findByIdProducto(idProducto);
        
        ValoracionDTO valoracion = valoracionesService.findValoracion(idUsuario,idProducto);
        //Valoracion valoracion= valoracionFacade.findValoracion(idUsuario,idProducto);
        List<ValoracionDTO> listaValoraciones = valoracionesService.findListaValoraciones(idProducto);
        //List<Valoracion> listaValoraciones = valoracionFacade.findListaValoraciones(idProducto);
        //
        
        request.setAttribute("producto", producto);
        request.setAttribute("valoracion",valoracion);
        request.setAttribute("listaValoraciones",listaValoraciones);
        
        if(usuario.getIsAdmin()){
            rd = request.getRequestDispatcher("editarProductoAdmin.jsp");
        } else{
            rd = request.getRequestDispatcher("valoracion.jsp");
        }
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
