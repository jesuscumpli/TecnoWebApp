/*
@Author: Álvaro Nieto González
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.service.ValoracionesService;


@Named(value = "productoValoracionesBean")
@RequestScoped
public class ProductoValoracionesBean implements Serializable{

    @EJB
    private ValoracionesService valoracionesService;
    
    protected List<ValoracionDTO> listaValoraciones;
    
    @Inject
    private UsuarioBean usuarioBean;
    
    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());
    
    /**
     * Creates a new instance of ProductoBean
     */
    public ProductoValoracionesBean() {
    }
    
    @PostConstruct
    public void init(){
        UsuarioDTO usuario = usuarioBean.getUsuario();
        ProductoMenuDTO producto = usuarioBean.getProductoSeleccionado();
        if (usuario == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {
            this.listaValoraciones = this.valoracionesService.findByProducto(producto.getIdProducto());
          
        }
    }

    public ValoracionesService getValoracionesService() {
        return valoracionesService;
    }

    public void setValoracionesService(ValoracionesService valoracionesService) {
        this.valoracionesService = valoracionesService;
    }

    public List<ValoracionDTO> getListaValoraciones() {
        return listaValoraciones;
    }

    public void setListaValoraciones(List<ValoracionDTO> listaValoraciones) {
        this.listaValoraciones = listaValoraciones;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    public boolean sinValoraciones () {
        return listaValoraciones==null || listaValoraciones.isEmpty();
    }
    
}
