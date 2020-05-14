/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import tecnoweb.classes.Filtro;
import tecnoweb.dto.CategoriaDTO;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.ProductosService;
import tecnoweb.service.SubcategoriasService;

/**
 *
 * @author Jesús
 */
@Named(value = "menuBean")
@SessionScoped
public class MenuBean implements Serializable{

    @EJB
    private SubcategoriasService subcategoriasService;

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
    protected Integer idCatSelected;
    protected SubcategoriaDTO subcatSelected;
    protected String busqueda = "";
    
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
            this.idCatSelected = -1; //TODOS
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

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
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

    public SubcategoriaDTO getSubcatSelected() {
        return subcatSelected;
    }

    public void setSubcatSelected(SubcategoriaDTO subcatSelected) {
        this.subcatSelected = subcatSelected;
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

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public Integer getIdCatSelected() {
        return idCatSelected;
    }

    public void setIdCatSelected(Integer idCatSelected) {
        this.idCatSelected = idCatSelected;
    }


    
    //********** MÉTODOS ******************************************************************************+
    
    public String seleccionarSubcategoria(SubcategoriaDTO subcat){
        this.subcatSelected = subcat;
        this.idCatSelected = subcat.getIdCategoria().getIdCategoria();
        /*this.productos = this.productosService.findBySubcategoriaMenuDTO(subcat.getIdSubcategoria());     
        //ORDENAR POR:
        Filtro.ordenarProductosMenuDTO(orden, productos);*/
        return this.filtrar();
    }
    
    public String quitarSubcategoria(){
        this.subcatSelected = null;
        /*this.productos = this.productosService.findByCategoriaMenuDTO(idCatSelected);
        //Ordenar por:
        Filtro.ordenarProductosMenuDTO(orden, productos);*/
        return this.filtrar();
    }
    
    public String filtrar(){
        
        //RESETEAR PRODUCTOS
        if(this.subcatSelected!=null){ //Reseteamos productos de subcategoria, si está seleccionada
            productos = this.productosService.findBySubcategoriaMenuDTO(subcatSelected.getIdSubcategoria());
        }else if(this.idCatSelected>=0){ //Reseteamos productos de categoria, si está seleccionada
            productos = this.productosService.findByCategoriaMenuDTO(idCatSelected);
        }else{  //Sino, TODOS
            productos = this.productosService.findAllMenuDTO();
        }
        
        // BUSQUEDA (Search)
            /*
                Lógica de la búsqueda: 
                        - Buscar por títulos
                        - Buscar por descripción
                        - Buscar por palabras claves
                        - Unir las tres búsquedas
            */
            
        if(busqueda!=null && !busqueda.trim().isEmpty()){
            //Filtrar con operaciones staticas adicionales
            List<ProductoMenuDTO> filtroTitulo = Filtro.filtrarTituloMenuDTO(productos, busqueda);        //TITULO
            List<ProductoMenuDTO> filtroDesc = Filtro.filtrarDescripcionMenuDTO(productos, busqueda);     //DESCRIPCION
            List<ProductoMenuDTO> filtroClaves = Filtro.filtrarPalabrasClaveMenuDTO(productos, busqueda); //PALABRAS CLAVES

            //Union de los productos
            productos = this.unirFiltrosMenuDTO(filtroTitulo, filtroDesc, filtroClaves);    //Método privado
        }
        
        //ORDENAR POR:
        Filtro.ordenarProductosMenuDTO(orden, productos);
        
        return null;
    }
    
    private List<ProductoMenuDTO> unirFiltrosMenuDTO(List<ProductoMenuDTO> p1, List<ProductoMenuDTO> p2, List<ProductoMenuDTO> p3){
        Set<ProductoMenuDTO> set = new HashSet<>();
        set.addAll(p1);
        set.addAll(p2);
        set.addAll(p3);
        return new ArrayList<>(set);
    }
}
