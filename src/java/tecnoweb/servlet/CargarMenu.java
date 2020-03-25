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
import javax.servlet.http.HttpSession;
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Usuario;

/**
 *
 * @author Jesús
 */
@WebServlet(name = "CargarMenu", urlPatterns = {"/CargarMenu"})
public class CargarMenu extends HttpServlet {

    @EJB
    private CategoriaFacade categoriaFacade;

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
        String goTo;
        RequestDispatcher rd;
        Usuario user = (Usuario) session.getAttribute("usuario");
        
        //Si es admin, se redirige a menuAdmin.jsp
        if(user.getIsAdmin()){
            goTo = "menuAdmin.jsp";
        }else{ //Sino, a menu.jsp y se cargan los productos y categorias en la sesion
            goTo = "menu.jsp";
            List<Producto> productos = productoFacade.findAll();
            session.setAttribute("productos", productos);
            List<Categoria> categorias = categoriaFacade.findAll();
            session.setAttribute("categorias",categorias);
        }
        rd = request.getRequestDispatcher(goTo);
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
