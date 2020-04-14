/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.productos;

import java.io.IOException;
import java.io.PrintWriter;
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
import tecnoweb.dto.ProductoDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Usuario;
import tecnoweb.entity.Valoracion;
import tecnoweb.service.ProductosService;
import tecnoweb.service.ValoracionesService;

/**
 *
 * @author alvar
 */
@WebServlet(name = "CargarValoraciones", urlPatterns = {"/CargarValoraciones"})
public class CargarValoraciones extends HttpServlet {
    @EJB
    private ValoracionFacade valoracionFacade;

    @EJB
    private ProductoFacade productoFacade;
    
    @EJB 
    private ProductosService productosService;
    
    @EJB
    private ValoracionesService valoracionesService;
    
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
        HttpSession sesion = request.getSession();
        RequestDispatcher rd;
        
        int idProducto=Integer.parseInt(request.getParameter("id"));
        //Producto producto=productoFacade.find(idProducto); ANTES DEL DTO
        ProductoMenuDTO producto = this.productosService.find(idProducto);
        //List<Valoracion> listaValoraciones = valoracionFacade.findListaValoraciones(idProducto); ANTES DEL DTO
        List<ValoracionDTO> listaValoraciones = this.valoracionesService.findListaValoraciones(idProducto);
        
        request.setAttribute("producto", producto);
        request.setAttribute("listaValoraciones",listaValoraciones);

        rd = request.getRequestDispatcher("listaValoraciones.jsp");
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
