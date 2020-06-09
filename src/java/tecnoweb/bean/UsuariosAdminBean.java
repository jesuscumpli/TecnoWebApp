/*
@Author: Francisco José García Rodríguez
*/

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
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.UsuariosService;

@Named(value = "usuariosAdminBean")
@RequestScoped
public class UsuariosAdminBean {

    @EJB
    private UsuariosService usuariosService;
    
    private static final Logger LOG = Logger.getLogger(UsuariosAdminBean.class.getName());
    
    protected List<UsuarioDTO> listaUsuarios;
    protected UsuarioDTO usuarioSeleccionado;
    
    /**
     * Creates a new instance of UsuariosAdminBean
     */
    public UsuariosAdminBean() {
    }
    
    @PostConstruct
    public void init () {
        this.listaUsuarios = this.usuariosService.findAllDTO();
        usuarioSeleccionado = null;
    }
    
    public List<UsuarioDTO> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaClientes(List<UsuarioDTO> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setClienteSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    public String doBorrar (UsuarioDTO usuario) {
        this.usuariosService.remove2(usuario);
        listaUsuarios.remove(usuario);
        LOG.info("doBorrar(): " + this.hashCode());        
        return "listadoUsuariosAdmin";
    } 
    
    public String doEditar (UsuarioDTO usuario) {      
        this.usuarioSeleccionado = usuario;
        return "editarUsuarioListadoAdmin";
    }
   
    
}
