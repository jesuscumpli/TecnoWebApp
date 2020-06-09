/*
@Author: Luis Ramos Matas
*/

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
        this.valoracion = this.menuBean.getValoracionSeleccionada();
        this.listaValoraciones = this.valoracionesService.findListaValoraciones(this.producto.getIdProducto());
        
        if(valoracion==null){
           this.menuBean.setValoracionSeleccionada(new ValoracionDTO());
           this.menuBean.getValoracionSeleccionada().setIdValoracion(-1);   //NUEVA
           this.menuBean.getValoracionSeleccionada().setUsuario(this.usuarioBean.getUsuario());
           this.menuBean.getValoracionSeleccionada().setProducto(producto);
           this.menuBean.getValoracionSeleccionada().setComentario("");
           this.menuBean.getValoracionSeleccionada().setNota(0.0);
        }
            
    }

    public ProductoMenuDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMenuDTO producto) {
        this.producto = producto;
    }

    public ProductosService getProductosService() {
        return productosService;
    }

    public void setProductosService(ProductosService productosService) {
        this.productosService = productosService;
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
    
    public String doGuardarValoracion(){
        
        valoracion.setFechaPublicacion(new Date());
        
        if(valoracion.getIdValoracion()==-1){
            this.valoracionesService.create(valoracion);
            producto.setNumValoraciones(producto.getNumValoraciones()+1);
        }else{
            this.valoracionesService.edit(valoracion);
        }
        
        Double nuevaNotaMedia = this.productosService.getNotaMedia(producto.getIdProducto());
        producto.setNotaMedia(nuevaNotaMedia);
        
        
        listaValoraciones = this.valoracionesService.findListaValoraciones(this.producto.getIdProducto());
        valoracion = this.valoracionesService.findValoracion(valoracion.getUsuario().getIdUsuario(), valoracion.getProducto().getIdProducto());
        this.menuBean.setValoracionSeleccionada(valoracion);
        
        return "valoracion";
    }
    
    public String doLimpiar(){
        
        this.menuBean.getValoracionSeleccionada().setComentario("");
        this.menuBean.getValoracionSeleccionada().setNota(0.0);
        return "valoracion";
    }
    
    public String doVolver(){
        menuBean.setProductoSeleccionado(null);
        menuBean.setValoracionSeleccionada(null);
        return "menu";
    }
}
