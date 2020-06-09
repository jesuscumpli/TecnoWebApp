/*
@Author: Jesús Cumplido Almenara 
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import tecnoweb.classes.Filtro;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.service.ValoracionesService;


@Named(value = "misValoracionesBean")
@RequestScoped
public class MisValoracionesBean {

    @EJB
    private ValoracionesService valoracionesService;

    protected List<ValoracionDTO> misValoraciones;
    
    @Inject
    private UsuarioBean usuarioBean;
    
    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());
    
    /**
     * Creates a new instance of MisValoracionesBean
     */
    public MisValoracionesBean() {
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
            this.misValoraciones = this.valoracionesService.findByIdUsuario(usuario.getIdUsuario());
        }
    }

    public ValoracionesService getValoracionesService() {
        return valoracionesService;
    }

    public void setValoracionesService(ValoracionesService valoracionesService) {
        this.valoracionesService = valoracionesService;
    }

    public List<ValoracionDTO> getMisValoraciones() {
        return misValoraciones;
    }

    public void setMisValoraciones(List<ValoracionDTO> misValoraciones) {
        this.misValoraciones = misValoraciones;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    
    
}
