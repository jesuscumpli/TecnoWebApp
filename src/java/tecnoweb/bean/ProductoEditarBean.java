/*
@Author: Álvaro Nieto González
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.dto.PalabraclaveDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.service.CategoriasService;
import tecnoweb.service.PalabrasclaveService;
import tecnoweb.service.ProductosService;
import tecnoweb.service.SubcategoriasService;


@Named(value = "productoEditarBean")
@RequestScoped
public class ProductoEditarBean {
    
    @Inject 
    UsuarioBean usuarioBean;
    
    @Inject
    MenuBean menuBean;
    
    @EJB
    private CategoriasService categoriasService;
    
    @EJB
    private PalabrasclaveService palabrasClaveService;
    
    @EJB 
    private SubcategoriasService subcategoriasService;
    
    @EJB 
    private ProductosService productosService;
    
    protected ProductoMenuDTO productoSeleccionado;
    
    protected List<CategoriaMenuDTO> listaCategorias;
    protected List<SubcategoriaDTO> listaSubcategorias;
    
    
    protected int idSubcategoriaSeleccionada;
    
    protected String palabrasClaveString;
    
    protected boolean esNuevo;
    
    
    /**
     * Creates a new instance of ProductoEditar
     */
    public ProductoEditarBean() {
    }
    
    @PostConstruct
    public void init () {
            this.productoSeleccionado = this.usuarioBean.getProductoSeleccionado();
            this.listaCategorias = this.categoriasService.findAllMenuDTO();
            
            if (this.productoSeleccionado == null) { // Es crear nuevo producto
                esNuevo = true;
                this.productoSeleccionado = new ProductoMenuDTO();
                this.productoSeleccionado.setIdSubcategoria(this.subcategoriasService.find(1));
                this.productoSeleccionado.setPalabraclaveList(new ArrayList<PalabraclaveDTO>());
                this.usuarioBean.setProductoSeleccionado(productoSeleccionado);
                this.idSubcategoriaSeleccionada = 1;
            } else { // Es editar producto
                esNuevo = false;
                this.idSubcategoriaSeleccionada = this.productoSeleccionado.getIdSubcategoria().getIdSubcategoria();
                this.crearPalabrasClaveString(productoSeleccionado.getPalabraclaveList());
             }          
            this.listaSubcategorias = this.subcategoriasService.findByCategoria(this.usuarioBean.getIdCategoriaSeleccionada());            
    
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

    public CategoriasService getCategoriasService() {
        return categoriasService;
    }

    public void setCategoriasService(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
    }

    public PalabrasclaveService getPalabrasClaveService() {
        return palabrasClaveService;
    }

    public void setPalabrasClaveService(PalabrasclaveService palabrasClaveService) {
        this.palabrasClaveService = palabrasClaveService;
    }

    public SubcategoriasService getSubcategoriasService() {
        return subcategoriasService;
    }

    public void setSubcategoriasService(SubcategoriasService subcategoriasService) {
        this.subcategoriasService = subcategoriasService;
    }

    public ProductoMenuDTO getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoMenuDTO productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }


    public List<CategoriaMenuDTO> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<CategoriaMenuDTO> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<SubcategoriaDTO> getListaSubcategorias() {
        return listaSubcategorias;
    }

    public void setListaSubcategorias(List<SubcategoriaDTO> listaSubcategorias) {
        this.listaSubcategorias = listaSubcategorias;
    }

    private void crearPalabrasClaveString(List<PalabraclaveDTO> listaPalabras) {
        String palabrasclave = "";
       
        for (PalabraclaveDTO p : listaPalabras) {
                    palabrasclave += p.getValor() + ",";
               }
        if(palabrasclave.length()>0)
                {palabrasclave = palabrasclave.substring(0,palabrasclave.length()-1);}
        this.palabrasClaveString = palabrasclave;
    }
    
    private List<PalabraclaveDTO> crearPalabrasClaveList (String palabrasClave) {
        String[] tags;
        if( palabrasClave.isEmpty()){
            tags = new String[0];
        }else{
            tags = palabrasClave.split(",");
        }

        List<PalabraclaveDTO> listaClaves = new ArrayList<>();

        for (String valor : tags) {
            PalabraclaveDTO p = this.palabrasClaveService.findByValue(valor.trim());
            if (p == null) {        //Si no existe la creo
                p = new PalabraclaveDTO();
                p.setValor(valor.trim());
                this.palabrasClaveService.create(p);    
            }
            listaClaves.add(p);
        }
        return listaClaves;
    }
    
    public String getPalabrasClaveString() {
        return palabrasClaveString;
    }

    public void setPalabrasClaveString(String palabrasClaveString) {
        this.palabrasClaveString = palabrasClaveString;
    }

    public int getIdSubcategoriaSeleccionada() {
        return idSubcategoriaSeleccionada;
    }

    public void setIdSubcategoriaSeleccionada(int idSubcategoriaSeleccionada) {
        this.idSubcategoriaSeleccionada = idSubcategoriaSeleccionada;
    }
    
    public String doGuardar() {
        SubcategoriaDTO subcategoriaSeleccionada = this.subcategoriasService.find(this.idSubcategoriaSeleccionada);
        this.productoSeleccionado.setIdSubcategoria(subcategoriaSeleccionada);
        this.productoSeleccionado.setPalabraclaveList(this.crearPalabrasClaveList(palabrasClaveString));
        this.productoSeleccionado.setIdUsuario(usuarioBean.getUsuario());
        
        this.productosService.createOrUpdate(this.productoSeleccionado);
        this.usuarioBean.setProductoSeleccionado(null);
        this.menuBean.filtrar();
        return "listadoProducto";   
    }
    
    public String recargar() {
        this.listaSubcategorias = this.subcategoriasService.findByCategoria(this.usuarioBean.getIdCategoriaSeleccionada());
        this.productoSeleccionado.setIdSubcategoria(this.listaSubcategorias.get(0));
        return "nuevoProducto";
    }
    
}
