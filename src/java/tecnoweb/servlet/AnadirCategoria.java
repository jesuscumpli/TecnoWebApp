/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tecnoweb.entity.Categoria;
import javax.ejb.EJB;
import tecnoweb.dao.CategoriaFacade;

/**
 *
 * @author haylo
 */
@WebServlet(name = "AnadirCategoria", urlPatterns = {"/AnadirCategoria"})
public class AnadirCategoria extends HttpServlet {

    @EJB
    private CategoriaFacade categoriaFacade;
    
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
        
        //Editar
        String str = request.getParameter("idCate");
        
        Categoria cate;
        boolean esNuevo = false;
        
        if(str == null){
            cate = new Categoria(0);
            esNuevo = true;
        }else{
            cate = this.categoriaFacade.find(new Integer(str));
        }
        
        String src;
        
        src = request.getParameter("nombre");
        cate.setNombreCategoria(src);

        //cate.setSubcategoriaList(null);
        
        if(esNuevo){
            this.categoriaFacade.create(cate);
        }else{
            this.categoriaFacade.edit(cate);
        }
        
        response.sendRedirect("ListadoCategoriasAdmin");
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
