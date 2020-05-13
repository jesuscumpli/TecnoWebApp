/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import tecnoweb.classes.Filtro;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.ProductosService;

/**
 *
 * @author Jesús
 */
@Named(value = "menuBean")
@SessionScoped
public class MenuBean implements Serializable{

    @EJB
    private CategoriasService categoriasService;

    @EJB
    private ProductosService productosService;
    
    @Inject
    private UsuarioBean usuarioBean;
    
    protected List<ProductoMenuDTO> productos;
    protected List<CategoriaMenuDTO> categorias;
    protected String status = "";
    protected String orden;
    
    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());

    /**
     * Creates a new instance of MenuBean
     */
    public MenuBean() {
    }
    
    @PostConstruct
    public void init () {
        UsuarioDTO usuario = this.usuarioBean.getUsuario();
        if (usuario == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {
            this.productos = this.productosService.findAllMenuDTO();
            this.categorias = this.categoriasService.findAllMenuDTO();
            this.orden = "Fecha";
            Filtro.ordenarProductosMenuDTO(orden, productos);
        }
    }

    public ProductosService getProductosService() {
        return productosService;
    }

    public void setProductosService(ProductosService productosService) {
        this.productosService = productosService;
    }

    public List<ProductoMenuDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoMenuDTO> productos) {
        this.productos = productos;
    }

    public List<CategoriaMenuDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaMenuDTO> categorias) {
        this.categorias = categorias;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
