/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.servlet.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tecnoweb.dao.PalabraclaveFacade;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Producto;

/**
 *
 * @author haylo
 */
@WebServlet(name = "EditarProductoAdmin", urlPatterns = {"/EditarProductoAdmin"})
public class EditarProductoAdmin extends HttpServlet {

    @EJB
    private ProductoFacade productoFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private PalabraclaveFacade palabraClaveFacade;
    
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
        
        HttpSession session = request.getSession();
        Producto producto;
        String statusNombre = "", statusDescripcion = "", statusPrecio = "";
        String goTo = "";
        
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
        } else {
           
            Integer idProducto = Integer.parseInt(request.getParameter("idProducto"));

            producto = this.productoFacade.find(idProducto);
            String nombreProducto, photoURL, precio, descripcion, palabrasClave;
            
            nombreProducto = request.getParameter("nombreProducto");
            precio = request.getParameter("precio");
            descripcion = request.getParameter("descripcion");
            photoURL = request.getParameter("photoURL");
            palabrasClave = request.getParameter("palabrasClave");
            
            boolean error = false;
            boolean changePhoto = (photoURL != null) && !photoURL.trim().equals("");
            
            if(nombreProducto.trim().equals("")) statusNombre = "Nombre no valido.";
            if(precio.trim().equals("")) statusPrecio = "Precio no valido.";
            if(descripcion.trim().equals("")) statusDescripcion = "Descripcion no valida.";

            if(!statusDescripcion.equals("") || !statusNombre.equals("") || !statusPrecio.equals("")) error = true;
            
            
            if(!error){
                producto.setTitulo(new String(nombreProducto.getBytes("ISO-8859-1"),"UTF8"));
                producto.setPrecio(new Double(precio));
                producto.setDescripcion(new String(descripcion.getBytes("ISO-8859-1"),"UTF8"));
                palabrasClave = new String(palabrasClave.getBytes("ISO-8859-1"),"UTF8");
                
                if(changePhoto) producto.setFotoProducto(new String(photoURL.getBytes("ISO-8859-1"),"UTF8"));
                
                String[] tags = null;
                if( palabrasClave.isEmpty()){
                    tags = new String[0];
                }else{
                    tags = palabrasClave.split(",");
                }

                List<Palabraclave> listaClaves = new ArrayList<>();

                for (String valor : tags) {
                    Palabraclave p = palabraClaveFacade.findByValue(valor.trim());
                    if (p == null) {        //Si no existe la creo
                        p = new Palabraclave(0);
                        p.setValor(valor.trim());
                        this.palabraClaveFacade.create(p);
                    }
                    listaClaves.add(p);
                }
                producto.setPalabraclaveList(listaClaves);
                
                goTo = "FiltrarMenu";
            } else {
                
                request.setAttribute("statusNombre", statusNombre);
                request.setAttribute("statusDescripcion", statusDescripcion);
                request.setAttribute("statusPrecio", statusPrecio);
                request.setAttribute("photoURL", photoURL);
                goTo = "edicionUsuarioAdmin.jsp";
            }
           
            this.productoFacade.edit(producto);
            
            RequestDispatcher rd = request.getRequestDispatcher(goTo);
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
