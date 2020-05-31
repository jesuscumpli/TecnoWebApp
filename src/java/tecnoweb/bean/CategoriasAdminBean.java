/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.dto.CategoriaDTO;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Subcategoria;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.SubcategoriasService;

/**
 *
 * @author haylo
 */
@Named(value = "categoriasAdminBean")
@RequestScoped
public class CategoriasAdminBean {

    @EJB
    private CategoriasService categoriasService;
    
    @EJB
    private SubcategoriasService subcategoriasService;
    
    @EJB
    private SubcategoriaFacade subcategoriaFacade;
    
    @EJB
    private CategoriaFacade categoriaFacade;
    
    @Inject
    private MenuAdminBean menuAdminBean;
    
    @Inject
    private MenuBean menuBean;
    
    private static final Logger LOG = Logger.getLogger(UsuariosAdminBean.class.getName());

    protected List<CategoriaMenuDTO> listaCategorias;
    
    protected String nuevaCategoria;
    protected String nuevaSubcategoria;
    
    /**
     * Creates a new instance of categoriasAdminBean
     */
    public CategoriasAdminBean() {
    }
    
    @PostConstruct
    public void init () {
        this.listaCategorias = this.categoriasService.findAllMenuDTO();
        nuevaCategoria = "";
        nuevaSubcategoria = "";
        
    }
    
    public List<CategoriaMenuDTO> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaClientes(List<CategoriaMenuDTO> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public String getNuevaCategoria() {
        return nuevaCategoria;
    }

    public void setNuevaCategoria(String nuevaCategoria) {
        this.nuevaCategoria = nuevaCategoria;
    }

    public String getNuevaSubcategoria() {
        return nuevaSubcategoria;
    }

    public void setNuevaSubcategoria(String nuevaSubcategoria) {
        this.nuevaSubcategoria = nuevaSubcategoria;
    }

    public CategoriasService getCategoriasService() {
        return categoriasService;
    }

    public void setCategoriasService(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
    }

    public SubcategoriasService getSubcategoriasService() {
        return subcategoriasService;
    }

    public void setSubcategoriasService(SubcategoriasService subcategoriasService) {
        this.subcategoriasService = subcategoriasService;
    }

    public SubcategoriaFacade getSubcategoriaFacade() {
        return subcategoriaFacade;
    }

    public void setSubcategoriaFacade(SubcategoriaFacade subcategoriaFacade) {
        this.subcategoriaFacade = subcategoriaFacade;
    }

    public CategoriaFacade getCategoriaFacade() {
        return categoriaFacade;
    }

    public void setCategoriaFacade(CategoriaFacade categoriaFacade) {
        this.categoriaFacade = categoriaFacade;
    }

    public String doBorrar (CategoriaMenuDTO categoria) {      //No sería CategoriaMenuDTO???
                                                           //hay que editar tamb lo de "Por definir"
        Categoria cat = this.categoriaFacade.find(categoria.getIdCategoria());
        this.categoriaFacade.remove(cat);
        menuAdminBean.setCategoriaSeleccionada(null);
        menuBean.setCategorias(this.categoriasService.findAllMenuDTO());
        return "listadoCategoriasAdmin?faces-redirect=true";
    }
    
    public String doEditar (CategoriaMenuDTO categoria) {
         Categoria cat = this.categoriaFacade.find(categoria.getIdCategoria());
         cat.setNombreCategoria(categoria.getNombreCategoria());
         this.categoriaFacade.edit(cat);
         menuBean.setCategorias(this.categoriasService.findAllMenuDTO());
        return "listadoCategoriasAdmin?faces-redirect=true";
    }
    
    public String doAnadir (){
        CategoriaMenuDTO cat = new CategoriaMenuDTO();
        cat.setNombreCategoria(nuevaCategoria);
        this.categoriasService.createMenuDTO(cat);
        menuBean.setCategorias(this.categoriasService.findAllMenuDTO());
        return "listadoCategoriasAdmin?faces-redirect=true";
    }
    
    public String doConsultarSubcategorias (CategoriaMenuDTO categoria) {
        menuAdminBean.setCategoriaSeleccionada(categoria);
        return null;
    }
    
    public String doBorrarSubcategoria (SubcategoriaDTO subcategoria) {
        Subcategoria subc = this.subcategoriaFacade.find(subcategoria.getIdSubcategoria());
        this.subcategoriaFacade.remove(subc);
        
        //Actualizar categoria
        Categoria cate = this.categoriaFacade.find(menuAdminBean.getCategoriaSeleccionada().getIdCategoria());
        cate.getSubcategoriaList().remove(subc);
        this.categoriaFacade.edit(cate);
        menuAdminBean.setCategoriaSeleccionada(cate.getMenuDTO());
        menuBean.setCategorias(this.categoriasService.findAllMenuDTO());
        
        return "listadoCategoriasAdmin";
    }
    
    public String doEditarSubcategoria (SubcategoriaDTO subcategoria) {
        Subcategoria subc = this.subcategoriaFacade.find(subcategoria.getIdSubcategoria());
        subc.setNombreSubcategoria(subcategoria.getNombreSubcategoria());
        this.subcategoriaFacade.edit(subc);
        Categoria cate = this.categoriaFacade.find(subc.getIdCategoria().getIdCategoria());
        menuAdminBean.setCategoriaSeleccionada(cate.getMenuDTO());
        this.categoriasService.editMenuDTO(menuAdminBean.getCategoriaSeleccionada());
        this.listaCategorias = this.categoriasService.findAllMenuDTO();
        menuBean.setCategorias(this.categoriasService.findAllMenuDTO());
        return "listadoCategoriasAdmin";
    }
    
    public String doAnadirSub(){
        Subcategoria subcat = new Subcategoria();
        subcat.setNombreSubcategoria(nuevaSubcategoria);
        Categoria cate = this.categoriaFacade.find(menuAdminBean.getCategoriaSeleccionada().getIdCategoria());
        subcat.setIdCategoria(cate);
        this.subcategoriaFacade.create(subcat);
        //Actualizar categoria
        subcat = this.subcategoriaFacade.findByName(nuevaSubcategoria);
        cate.getSubcategoriaList().add(subcat);
        this.categoriaFacade.edit(cate);
        menuAdminBean.setCategoriaSeleccionada(cate.getMenuDTO());
        menuBean.setCategorias(this.categoriasService.findAllMenuDTO());
        return "listadoCategoriasAdmin";
    }
    
}