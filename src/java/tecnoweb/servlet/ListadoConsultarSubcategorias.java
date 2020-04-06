/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet;

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
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Subcategoria;

/**
 *
 * @author haylo
 */
@WebServlet(name = "ListadoConsultarSubcategorias", urlPatterns = {"/ListadoConsultarSubcategorias"})
public class ListadoConsultarSubcategorias extends HttpServlet {

    @EJB
    private CategoriaFacade categoriaFacade;
    
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
        
        response.setContentType("text/html;charset=UTF-8");
        
        String idCategoria = request.getParameter("idCategoria");
        request.setAttribute("idCategoria", Integer.parseInt(idCategoria));
        //if(idCategoria == null){
        //    idCategoria = (String) request.getAttribute("idCategoria");
        //}
        Categoria c = this.categoriaFacade.find(Integer.parseInt(idCategoria));
        
        List<Categoria> lista2 = categoriaFacade.findAll();
        request.setAttribute("categorias", lista2);

        List<Subcategoria> lista = this.subcategoriaFacade.findByCategoria(c.getIdCategoria());
        request.setAttribute("subcategorias", lista);
        
        RequestDispatcher rd = request.getRequestDispatcher("listadoCategoriasAdmin.jsp");
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
