/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.admin;

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
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.dto.CategoriaDTO;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Subcategoria;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.SubcategoriasService;

/**
 *
 * @author haylo
 */
@WebServlet(name = "AnadirSubcategoria", urlPatterns = {"/AnadirSubcategoria"})
public class AnadirSubcategoria extends HttpServlet {

    @EJB
    private CategoriasService categoriasService;
    
    //@EJB
    //private CategoriaFacade categoriaFacade;
    
    @EJB
    private SubcategoriasService subcategoriasService;
    
    //@EJB
    //private SubcategoriaFacade subcategoriaFacade;
    
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
        
        //Editar
        String str = request.getParameter("idSubcate");
        
        SubcategoriaDTO subcate;
        boolean esNuevo = false;
        
        if(str == null){
            subcate = new SubcategoriaDTO();
            esNuevo = true;
        }else{
            subcate = this.subcategoriasService.find(new Integer(str));
        }
        
        String src;
        Integer idesito;
        idesito = Integer.parseInt(request.getParameter("idCategoria"));
        CategoriaDTO c;
        c = this.categoriasService.find(idesito);
        subcate.setIdCategoria(c);
        src = request.getParameter("nombre");
        subcate.setNombreSubcategoria(src);
        
        if(esNuevo){
            this.subcategoriasService.create(subcate);
        }else{
            this.subcategoriasService.edit(subcate);
        }
        
        List<CategoriaMenuDTO> categorias = this.categoriasService.findAllMenuDTO();
        session.setAttribute("categorias",categorias);
        
        //response.sendRedirect("ListadoConsultarSubcategorias");
        RequestDispatcher rd = request.getRequestDispatcher("ListadoConsultarSubcategorias?idCategoria="+subcate.getIdCategoria().getIdCategoria());
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
