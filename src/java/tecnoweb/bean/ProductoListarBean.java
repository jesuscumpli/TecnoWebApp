/*
@Author: Álvaro Nieto González
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.IOException;import java.io.Serializable;
import java.util.List;
;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.logging.Logger;
import javax.inject.Inject;
import tecnoweb.classes.Filtro;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.service.ProductosService;


@Named(value = "productoListarBean")
@RequestScoped
public class ProductoListarBean implements Serializable {
    @Inject
    private MenuBean menuBean;
    
    @EJB
    private ProductosService productosService;
    
    @Inject
    private UsuarioBean usuarioBean;
    
    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());

    protected List<ProductoMenuDTO> listaProductos;
    
    
    /**
     * Creates a new instance of ProductoBean
     */
    public ProductoListarBean() {
    }
    
    @PostConstruct
    public void init(){
        UsuarioDTO usuario = this.usuarioBean.getUsuario();
        if (usuario == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {
            this.listaProductos = this.productosService.findByIdUsuario(usuario.getIdUsuario());
            Filtro.ordenarProductosMenuDTO("Fecha", listaProductos);
        }
    }

    public ProductosService getProductosService() {
        return productosService;
    }

    public void setProductosService(ProductosService productosService) {
        this.productosService = productosService;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public List<ProductoMenuDTO> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoMenuDTO> listaProductos) {
        this.listaProductos = listaProductos;
    }
    
    public String doBorrar (ProductoMenuDTO producto) {
        this.listaProductos.remove(producto);
        this.productosService.remove(producto);   
        this.menuBean.getProductos().remove(producto);
        return "listadoProducto";
    }
}
