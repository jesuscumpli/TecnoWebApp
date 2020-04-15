/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package tecnoweb.servlet;

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
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.entity.Producto;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.ProductosService;
import tecnoweb.service.ValoracionesService;

/**
 *
 * @author Jesús
 */
@WebServlet(name = "CargarMenu", urlPatterns = {"/CargarMenu"})
public class CargarMenu extends HttpServlet {

    @EJB
    private ValoracionesService valoracionesService;

    @EJB
    private CategoriasService categoriasService;

    @EJB
    private ProductosService productosService;

//    @EJB
//    private CategoriaFacade categoriaFacade;

//    @EJB
//    private ProductoFacade productoFacade;

    
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
        UsuarioDTO user = (UsuarioDTO) session.getAttribute("usuario");
        
        //Si es nulll, se redirige a login.jsp
        if(user==null){
            goTo = "login.jsp";
        //Si es admin, se redirige a menuAdmin.jsp
        }else if(user.getIsAdmin()){
            goTo = "menuAdmin.jsp";
        }else{ //Sino, a menu.jsp y se cargan todos los productos y categorias en la sesion
            goTo = "menu.jsp";
            int idUsuario = user.getIdUsuario();
            //List<Producto> misProductos = this.productoFacade.findByIdUsuario(idUsuario);      //ANTES, SIN DTO
            List<ProductoMenuDTO> misProductos = this.productosService.findByIdUsuario(idUsuario);
            session.setAttribute("misProductos", misProductos);
            List<ValoracionDTO> misValoraciones = this.valoracionesService.findByIdUsuario(idUsuario);
            session.setAttribute("misValoraciones", misValoraciones);
        }

        //List<Producto> productos = productoFacade.findAll();      //ANTES, SIN DTO
        List<ProductoMenuDTO> productos = this.productosService.findAllMenuDTO();
        session.setAttribute("productos", productos);   //En session, porque es usado en varias partes (filtros), además que debe ser cargado siempre al principio de menu.jsp

        //List<Categoria> categorias = categoriaFacade.findAll();
        List<CategoriaMenuDTO> categorias = this.categoriasService.findAllMenuDTO();
        session.setAttribute("categorias",categorias); //En session, porque es usado en varias partes, además que debe ser cargado siempre al principio de menu.jsp

        String orden = "Fecha"; 
        session.setAttribute("orden",orden);        //Ordenar por Fecha, por defecto
        //Filtro.ordenarProductos(orden, productos);                //ANTES, SIN DTO
        Filtro.ordenarProductosMenuDTO(orden, productos);  //Ordenamos sobre la lista, en vez de ordenarlo con SQL
      
        //rd = request.getRequestDispatcher(goTo);
        //rd.forward(request, response);
        response.sendRedirect(goTo);
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
