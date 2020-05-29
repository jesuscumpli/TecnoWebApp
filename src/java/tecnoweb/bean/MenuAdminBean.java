/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.ProductosService;
import tecnoweb.service.SubcategoriasService;

/**
 *
 * @author haylo
 */
@Named(value = "menuAdminBean")
@SessionScoped
public class MenuAdminBean implements Serializable{
    @Inject
    private MenuBean menuBean;

    @EJB
    private SubcategoriasService subcategoriasService;

    @EJB
    private CategoriasService categoriasService;

    @EJB
    private ProductosService productosService;
    
    @Inject
    private UsuarioBean usuarioBean;
    
    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());
    
    protected CategoriaMenuDTO categoriaSeleccionada;
    protected SubcategoriaDTO subcategoriaSeleccionada;
    protected UsuarioDTO usuarioSeleccionado;
    protected ProductoMenuDTO productoSeleccionado;
    private String clavesSeleccionada;
    
    /**
     * Creates a new instance of MenuAdminBean
     */
    public MenuAdminBean() {
    }
    
    @PostConstruct
    public void init (){
        categoriaSeleccionada = null;
    }

    public SubcategoriasService getSubcategoriasService() {
        return subcategoriasService;
    }

    public void setSubcategoriasService(SubcategoriasService subcategoriasService) {
        this.subcategoriasService = subcategoriasService;
    }

    public CategoriasService getCategoriasService() {
        return categoriasService;
    }

    public void setCategoriasService(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
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

    public CategoriaMenuDTO getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(CategoriaMenuDTO categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public SubcategoriaDTO getSubcategoriaSeleccionada() {
        return subcategoriaSeleccionada;
    }

    public void setSubcategoriaSeleccionada(SubcategoriaDTO subcategoriaSeleccionada) {
        this.subcategoriaSeleccionada = subcategoriaSeleccionada;
    }

    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public ProductoMenuDTO getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoMenuDTO productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public String getClavesSeleccionada() {
        return clavesSeleccionada;
    }

    public void setClavesSeleccionada(String clavesSeleccionada) {
        this.clavesSeleccionada = clavesSeleccionada;
    }

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
    }
    
    //**************************************************************************
    
    public String editarUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado){
        this.usuarioSeleccionado = usuarioSeleccionado;
        return "editarUsuarioListadoAdmin";
    }
    
    public String doBorrar(ProductoMenuDTO prod) {
        this.productosService.remove(prod);
        menuBean.getProductos().remove(prod);
        //productos.remove(prod);    
        return "listadoProductosAdmin";
    } 
    
    public String doEditar(ProductoMenuDTO prod) {
        this.productoSeleccionado = prod;
        return "editarProductoAdmin";
    }
    
}
