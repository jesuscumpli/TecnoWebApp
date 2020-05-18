/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author DeuneB07
 */
@Named(value = "usuarioEditarBean")
@RequestScoped
public class UsuarioEditarBean {

    @EJB
    private UsuariosService usuariosService;

    @Inject
    private UsuarioBean usuarioBean;
    
    // Variables del Formulario
    private String name;
    private String apellidos;
    private String email;
    private Date fechaNac;
    private String oldPwd;
    private String newPwd;
    private String newPwdR;
    private String photoURL;
    
    //Variables Status del Formulario
    private String statusEmail;
    private String statusNombre;
    private String statusFechaNac;
    private String statusApellido;
    private String statusPwdOrig;
    private String statusPwdNew;
    
    
    // Usuario que Tenemos Elegido
    private UsuarioDTO usuarioSeleccionado;
    
    private static final Logger LOG = Logger.getLogger(MenuBean.class.getName());

    /**
     * Creates a new instance of UsuarioEditarBean
     */
    public UsuarioEditarBean() {
    }

    //getters + setters variables
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

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewPwdR() {
        return newPwdR;
    }

    public void setNewPwdR(String newPwdR) {
        this.newPwdR = newPwdR;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
    
    //getters + setters status
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

    public String getStatusPwdOrig() {
        return statusPwdOrig;
    }

    public void setStatusPwdOrig(String statusPwdOrig) {
        this.statusPwdOrig = statusPwdOrig;
    }

    public String getStatusPwdNew() {
        return statusPwdNew;
    }

    public void setStatusPwdNew(String statusPwdNew) {
        this.statusPwdNew = statusPwdNew;
    }
    
    //getter + setter usuarioBean + UsuarioDTO
    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    public UsuarioDTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    @PostConstruct
    public void init(){
        this.usuarioSeleccionado = this.usuarioBean.getUsuario();
        if (usuarioSeleccionado == null) { // Acceso no autorizado (sin autenticar)
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");           
            } catch (IOException ex) {
                LOG.severe(String.format("Se ha producido una excepcion: %s", ex.getMessage()));
            }
        } else {
            name = usuarioSeleccionado.getNombre();
            apellidos = usuarioSeleccionado.getApellidos();
            email = usuarioSeleccionado.getEmailUsuario();
            fechaNac = usuarioSeleccionado.getFechaNac();
            photoURL = "";
            oldPwd = "";
            newPwd = "";
            newPwdR = "";
            statusEmail = "";
            statusApellido = "";
            statusFechaNac = "";
            statusNombre = "";
            statusPwdNew = "";
            statusPwdOrig = "";
        }
    }
    
    public String convertirFecha(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        return sdf.format(date);
    }
    
    public String modificarPerfil(){
        
        boolean error = false;
        boolean changePass = !oldPwd.equals("") || !newPwd.equals("") || !newPwdR.equals("");
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
          
        if(changePass){
            if(oldPwd.trim().equals("") || !oldPwd.equals(usuarioSeleccionado.getPassword())) 
                statusPwdOrig = "Contrasena no Coincide con la Original.";
            if(!newPwd.trim().equals(newPwdR)) statusPwdNew = "Las contrasenas no coinciden.";
            if(newPwd.trim().equals("")) statusPwdNew = "No puede introducir una contrasena vacia.";
        }
        
        if(!statusEmail.equals("") || !statusNombre.equals("") || !statusApellido.equals("") 
            || !statusPwdOrig.equals("") || !statusPwdNew.equals("") || !statusFechaNac.equals("")) error = true;
        
        if(!error){
            
            usuarioSeleccionado.setEmailUsuario(email);
            usuarioSeleccionado.setNombre(name);
            usuarioSeleccionado.setApellidos(apellidos);
            usuarioSeleccionado.setFechaNac(fechaNac);
            if(changePass) usuarioSeleccionado.setPassword(newPwd);
            if(changePhoto) usuarioSeleccionado.setFotoUsuario(photoURL);

            this.usuariosService.createOrUpdate(new Integer(usuarioSeleccionado.getIdUsuario()), usuarioSeleccionado.getEmailUsuario(), usuarioSeleccionado.getNombre(), 
                    usuarioSeleccionado.getApellidos(), usuarioSeleccionado.getFechaNac(), usuarioSeleccionado.getPassword(), usuarioSeleccionado.getFotoUsuario(), usuarioSeleccionado.getIsAdmin());
            return "perfilUsuario";
        
        } else {
        
            oldPwd = "";
            newPwd = "";
            newPwdR = "";
            return null;
        
        }
    }
    
    public String darDeBaja(){
        this.usuariosService.remove(usuarioSeleccionado.getIdUsuario());
        return "login";
    }
}
