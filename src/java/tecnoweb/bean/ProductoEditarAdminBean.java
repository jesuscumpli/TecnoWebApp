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
import tecnoweb.dto.PalabraclaveDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.service.PalabrasclaveService;
import tecnoweb.service.ProductosService;

/**
 *
 * @author haylo
 */
@Named(value = "productoEditarAdminBean")
@RequestScoped
public class ProductoEditarAdminBean {
    
    @Inject
    private MenuAdminBean menuAdminBean;
    
    @Inject
    private MenuBean menuBean;
    
    @EJB
    private ProductosService productosService;
    
    @EJB
    private PalabrasclaveService palabrasclaveService;
    
    private ProductoMenuDTO productoSeleccionado;


    /**
     * Creates a new instance of ProductoEditarAdminBean
     */
    public ProductoEditarAdminBean() {
    }
    
    @PostConstruct
    public void init () {
        this.productoSeleccionado = this.menuAdminBean.getProductoSeleccionado();
        String claves = productoSeleccionado.getPalabraclaveList().toString().replaceAll("\\[|\\]","");
        System.out.println(claves);
        this.menuAdminBean.setClavesSeleccionada(claves);
    }

    public MenuAdminBean getMenuAdminBean() {
        return menuAdminBean;
    }

    public void setMenuAdminBean(MenuAdminBean menuAdminBean) {
        this.menuAdminBean = menuAdminBean;
    }

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
    }

    public ProductosService getProductosService() {
        return productosService;
    }

    public void setProductosService(ProductosService productosService) {
        this.productosService = productosService;
    }

    public ProductoMenuDTO getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoMenuDTO productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public String modificar(){
            
        String[] tags = null;
        if( menuAdminBean.getClavesSeleccionada().isEmpty()){
            tags = new String[0];
        }else{
            tags = menuAdminBean.getClavesSeleccionada().split(",");                                         
        }

        List<PalabraclaveDTO> listaClaves = new ArrayList<>();

        for (String valor : tags) {
            PalabraclaveDTO p = palabrasclaveService.findByValue(valor.trim());
            //Palabraclave p = palabraClaveFacade.findByValue(valor.trim());
            if (p == null) {        //Si no existe la creo
                p = new PalabraclaveDTO();
                p.setValor(valor.trim());
                this.palabrasclaveService.create(p);
            }
            listaClaves.add(p);
        }
        productoSeleccionado.setPalabraclaveList(listaClaves);

        System.out.println("holaaaaa");
        this.productosService.edit(productoSeleccionado);
        this.menuAdminBean.setProductoSeleccionado(null);
        this.menuAdminBean.setClavesSeleccionada(null);
        this.menuBean.filtrar();
        return "listadoProductosAdmin";
    }
    
    
    
}
