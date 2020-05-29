/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author haylo
 */
@Named(value = "usuarioEditarListadoBean")
@RequestScoped
public class UsuarioEditarListadoBean {
    
    @Inject
    private UsuariosAdminBean usuariosAdminBean;
    
    @EJB
    private UsuariosService usuariosService;
    
    @Inject
    private MenuAdminBean menuAdminBean;
    
    private UsuarioDTO usuarioSeleccionado;
    
    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());

    // Variables del Formulario
    private String name;
    private String apellidos;
    private String email;
    private Date fechaNac;
    private String photoURL;
    
    //Variables Status del Formulario
    private String statusEmail;
    private String statusNombre;
    private String statusFechaNac;
    private String statusApellido;

    
    
    /**
     * Creates a new instance of UsuarioEditarListadoBean
     */
    public UsuarioEditarListadoBean() {
    }

    public UsuariosAdminBean getUsuariosAdminBean() {
        return usuariosAdminBean;
    }

    public void setUsuariosAdminBean(UsuariosAdminBean usuariosAdminBean) {
        this.usuariosAdminBean = usuariosAdminBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public String getStatusEmail() {
        return statusEmail;
    }

    public void setStatusEmail(String statusEmail) {
        this.statusEmail = statusEmail;
    }

    public String getStatusNombre() {
        return statusNombre;
    }

    public void setStatusNombre(String statusNombre) {
        this.statusNombre = statusNombre;
    }

    public String getStatusFechaNac() {
        return statusFechaNac;
    }

    public void setStatusFechaNac(String statusFechaNac) {
        this.statusFechaNac = statusFechaNac;
    }

    public String getStatusApellido() {
        return statusApellido;
    }

    public void setStatusApellido(String statusApellido) {
        this.statusApellido = statusApellido;
    }


    @PostConstruct
    public void init(){        
        this.usuarioSeleccionado = this.menuAdminBean.getUsuarioSeleccionado();
        if (usuarioSeleccionado == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("listadoUsuariosAdmin.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {
            name = usuarioSeleccionado.getNombre();
            apellidos = usuarioSeleccionado.getApellidos();
            email = usuarioSeleccionado.getEmailUsuario();
            fechaNac = usuarioSeleccionado.getFechaNac();
            photoURL = "";
            statusEmail = "";
            statusApellido = "";
            statusFechaNac = "";
            statusNombre = "";
        }
    }
    
    public String modificar(){
        
        boolean error = false;
        boolean changePhoto = (photoURL != null) && !photoURL.trim().equals("");
            
        if(!email.contains("@") || email.trim().equals("")) statusEmail = "Correo no valido.";
        if(name.trim().equals("")) statusNombre = "Nombre no valido.";
        if(apellidos.trim().equals("")) statusApellido = "Campo Apellidos no valido.";
        
        Date now = new Date();
        long diffInMillies = Math.abs(now.getTime() - fechaNac.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long years = diff/365;
        if(!now.after(fechaNac)) statusFechaNac = "No puedes nacer despues de hoy";
        if(years<18) statusFechaNac = "No cumple con la mayoria de edad";
        
        if(!statusEmail.equals("") || !statusNombre.equals("") || !statusApellido.equals("") 
            || !statusFechaNac.equals("")) error = true;
        
        if(!error){
            
            usuarioSeleccionado.setEmailUsuario(email);
            usuarioSeleccionado.setNombre(name);
            usuarioSeleccionado.setApellidos(apellidos);
            usuarioSeleccionado.setFechaNac(fechaNac);
            if(changePhoto) usuarioSeleccionado.setFotoUsuario(photoURL);

            this.usuariosService.createOrUpdate(new Integer(usuarioSeleccionado.getIdUsuario()), usuarioSeleccionado.getEmailUsuario(), usuarioSeleccionado.getNombre(), 
                    usuarioSeleccionado.getApellidos(), usuarioSeleccionado.getFechaNac(), usuarioSeleccionado.getPassword(), usuarioSeleccionado.getFotoUsuario(), usuarioSeleccionado.getIsAdmin());
            return "listadoUsuariosAdmin";
        
        } 
        else {
   
            return null;
        
        }
    }
    
    
    
}
