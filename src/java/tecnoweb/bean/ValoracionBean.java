/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.service.ProductosService;
import tecnoweb.service.ValoracionesService;

/**
 *
 * @author luisr
 */
@Named(value = "ValoracionBean")
@RequestScoped
public class ValoracionBean {

    @EJB
    private ProductosService productosService;

    @Inject
    private MenuBean menuBean;
    
    @Inject
    private UsuarioBean usuarioBean;

    @EJB
    private ValoracionesService valoracionesService;

    /**
     * Creates a new instance of Valoraciones
     */
    
    protected String comentario;
    protected Double nota;
    protected boolean esNueva;
    protected ProductoMenuDTO producto;
    protected ValoracionDTO valoracion;
    protected List<ValoracionDTO> listaValoraciones;
    protected List<Integer> numeros;
    
    public ValoracionBean() {
    }
    
    @PostConstruct
    public void init(){
        this.numeros = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10));
        this.producto = this.menuBean.getProductoSeleccionado();
        this.listaValoraciones = this.valoracionesService.findListaValoraciones(this.producto.getIdProducto());
        this.valoracion = this.valoracionesService.findValoracion(this.usuarioBean.getUsuario().getIdUsuario(), this.producto.getIdProducto());
        
        esNueva = valoracion==null;
        
        if(esNueva){
            valoracion = new ValoracionDTO();
            valoracion.setUsuario(this.usuarioBean.getUsuario());
            valoracion.setProducto(producto);
            valoracion.setNota(0.0);
        }else{
            comentario= this.valoracion.getComentario();
            nota = this.valoracion.getNota();
        }
        
    }

    public ProductoMenuDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMenuDTO producto) {
        this.producto = producto;
    }

    public ValoracionDTO getValoracion() {
        return valoracion;
    }

    public ProductosService getProductosService() {
        return productosService;
    }

    public void setProductosService(ProductosService productosService) {
        this.productosService = productosService;
    }

    public void setValoracion(ValoracionDTO valoracion) {
        this.valoracion = valoracion;
    }

    public List<ValoracionDTO> getListaValoraciones() {
        return listaValoraciones;
    }

    public void setListaValoraciones(List<ValoracionDTO> listaValoraciones) {
        this.listaValoraciones = listaValoraciones;
    }

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public ValoracionesService getValoracionesService() {
        return valoracionesService;
    }

    public void setValoracionesService(ValoracionesService valoracionesService) {
        this.valoracionesService = valoracionesService;
    }
    

    public List<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<Integer> numeros) {
        this.numeros = numeros;
    }

    public boolean isEsNueva() {
        return esNueva;
    }

    public void setEsNueva(boolean esNueva) {
        this.esNueva = esNueva;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
    
    public String doGuardarValoracion(){
        
        if(!comentario.equals(valoracion.getComentario())){
            valoracion.setComentario(comentario);
        }
            
        if(!nota.equals(valoracion.getNota())){
            valoracion.setNota(nota);
        }
        
        valoracion.setFechaPublicacion(new Date());
        
        if(esNueva){
            this.valoracionesService.create(valoracion);
            producto.setNumValoraciones(producto.getNumValoraciones()+1);
        }else{
            this.valoracionesService.edit(valoracion);
        }
        
        Double nuevaNotaMedia = this.productosService.getNotaMedia(producto.getIdProducto());
        producto.setNotaMedia(nuevaNotaMedia);
        
        //this.valoracion = this.valoracionesService.findValoracion(this.usuarioBean.getUsuario().getIdUsuario(), this.producto.getIdProducto() );
        //listaValoraciones = this.valoracionesService.findListaValoraciones(this.producto.getIdProducto());
        
        return "valoracion?faces-redirect=true";
    }
    
    public String doLimpiar(){
        
        this.setComentario("");
        this.setNota(0.0);
        return "valoracion";
    }
    
    public String doVolver(){
        menuBean.productoSeleccionado = null;
        return "menu";
    }
}
