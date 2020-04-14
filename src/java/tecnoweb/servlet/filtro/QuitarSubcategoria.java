/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.filtro;

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
import tecnoweb.classes.Filtro;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dto.CategoriaDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.entity.Categoria;
import tecnoweb.service.ProductosService;

/**
 *
 * @author Jesús
 */
@WebServlet(name = "QuitarSubcategoria", urlPatterns = {"/QuitarSubcategoria"})
public class QuitarSubcategoria extends HttpServlet {

    @EJB
    private ProductosService productosService;

    @EJB
    private ProductoFacade productoFacade;

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
        RequestDispatcher rd;
 
        CategoriaDTO catSelected = (CategoriaDTO) session.getAttribute("catSelected");
        
        //Borrar subcategoria seleccionada
        session.removeAttribute("subcatSelected");
        
        //Buscar productos de la categoria
        //List<Producto> productos = this.productoFacade.findByCategoria(catSelected.getIdCategoria());         //ANTES, SIN USAR DTO
        List<ProductoMenuDTO> productos = this.productosService.findByCategoriaMenuDTO(catSelected.getIdCategoria());
        session.setAttribute("productos", productos);
        
        //Ordenar por:
        String orden = (String) session.getAttribute("orden");
        //Filtro.ordenarProductos(orden, productos);                //ANTES, SIN USAR DTO
        Filtro.ordenarProductosMenuDTO(orden, productos);
        
        //rd = request.getRequestDispatcher("menu.jsp");
        //rd.forward(request, response);
        response.sendRedirect("menu.jsp");
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
