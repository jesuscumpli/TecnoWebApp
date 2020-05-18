/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author Jesús
 */
@Named (value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable{

    @EJB
    private UsuariosService usuariosService;

    protected String email;
    protected String password; 
    protected String status = "";
    
    protected UsuarioDTO usuario;

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    
    public String doLogin () {        
        if(this.email==null || this.email.trim().isEmpty() || this.password==null || this.password.trim().isEmpty()){
            this.status = "Faltan campos por rellenar, vuelva a intentarlo";
            return null;
            
        }else{
            UsuarioDTO user = this.usuariosService.findByEmailUsuario(this.email);
            if (user == null) {
                this.status = "El Email no está registrado en nuestra aplicación";
                this.email = "";
                this.password = "";
                return null;
            } else if (!this.password.equals(user.getPassword())) {
               this.status = "La contraseña es incorrecta, vuelva a intentarlo";       
                this.password = "";           
                return null;           
            } else {
                this.usuario = user; //Usuario en SESSION
                if(usuario.getIsAdmin()) return "menuAdmin";
                else return "menu";
            }        
        }
    }
    
    public String doLogout(){
        this.usuario = null;
        this.status = "";
        this.email = "";
        this.password = "";
        return "login";
    }
    
    public boolean nombreValido(){
        return usuario.getNombre() != null && !usuario.getNombre().trim().equals("");
    }
    
    public boolean apellidosValido(){
        return usuario.getApellidos() != null && !usuario.getApellidos().trim().equals("");
    }
    
    public String convertirFecha(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        return sdf.format(usuario.getFechaNac());
    }
    
}
