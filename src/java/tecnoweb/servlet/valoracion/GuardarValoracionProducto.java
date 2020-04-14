/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.valoracion;

import java.io.IOException;
import java.util.Date;
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
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.dao.ValoracionFacade;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Usuario;
import tecnoweb.entity.Valoracion;
import tecnoweb.service.ProductosService;
import tecnoweb.service.UsuariosService;
import tecnoweb.service.ValoracionesService;

/**
 *
 * @author luisr
 */
@WebServlet(name = "GuardarValoracionProducto", urlPatterns = {"/GuardarValoracionProducto"})
public class GuardarValoracionProducto extends HttpServlet {

    @EJB
    private UsuariosService usuariosService;

    @EJB
    private ProductosService productosService;

    @EJB
    private ValoracionesService valoracionesService;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private ProductoFacade productoFacade;

    @EJB
    private ValoracionFacade valoracionFacade;
    
    

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
        
        String goTo, status=null;
        RequestDispatcher rd;
        HttpSession sesion = request.getSession();
        Boolean esNueva;
        
        UsuarioDTO u;
        ProductoMenuDTO p;
        ValoracionDTO nueva;
        int idUsuario, idProducto, idValoracion;
        String comentario;
        Double nota;
        Date fechaPublicacion;
        
        
        u=(UsuarioDTO)sesion.getAttribute("usuario");
        idProducto=Integer.parseInt(request.getParameter("idProducto"));
        List<ValoracionDTO> listaValoraciones = this.valoracionesService.findListaValoraciones(idProducto);
        p = this.productosService.find(idProducto);
        nueva = null;
        idUsuario = u.getIdUsuario();
        idValoracion = Integer.parseInt(request.getParameter("idValoracion"));
        fechaPublicacion = new Date();
        
        comentario = request.getParameter("comentario");

        if (comentario != null) {

            comentario = new String(comentario.getBytes("ISO-8859-1"),"UTF8");

        }
        
        nota = Double.parseDouble(request.getParameter("nota"));
        goTo = "valoracion.jsp";
        esNueva = false;
        
        try {
            
            nueva = this.valoracionesService.findValoracion(idUsuario, idProducto);
            
            if(nueva==null){
                nueva = new ValoracionDTO();
                nueva.setProducto(p);
                nueva.setUsuario(u);
                esNueva = true;
            } else{
                listaValoraciones.remove(nueva);
            }
            
            if(!comentario.equals(nueva.getComentario())){
                nueva.setComentario(comentario);
            }
            
            if(nota!=nueva.getNota()){
                nueva.setNota(nota);
            }
            
            nueva.setFechaPublicacion(fechaPublicacion);
            
           if(esNueva){
                this.valoracionesService.create(nueva);
                status = "¡Se ha envidado la valoración correctamente!";
           }else{
               status = "¡Se ha actualizado la valoración correctamente!";
               this.valoracionesService.edit(nueva);
           }
           
        } catch(RuntimeException e){
            status=e.getMessage();
        }finally{
            request.setAttribute("producto", p);
            request.setAttribute("status",status);
            nueva = this.valoracionesService.findValoracion(idUsuario, idProducto);
            listaValoraciones = valoracionesService.findListaValoraciones(idProducto);
            request.setAttribute("valoracion", nueva);
            request.setAttribute("listaValoraciones", listaValoraciones);
            
            //Actualizamos mis valoraciones
            List<ValoracionDTO> misValoraciones = this.valoracionesService.findByIdUsuario(idUsuario);
            sesion.setAttribute("misValoraciones", misValoraciones);
            
            rd = request.getRequestDispatcher(goTo);
            rd.forward(request, response);
            //response.sendRedirect(goTo);
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
