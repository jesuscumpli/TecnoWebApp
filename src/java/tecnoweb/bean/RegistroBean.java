/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.bean;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author Jesús
 */
@Named(value = "registroBean")
@RequestScoped
public class RegistroBean {
    @Inject
    private UsuarioBean usuarioBean;
    
    @EJB
    private UsuariosService usuariosService;
    
    protected String email;
    protected String password1;
    protected String password2;
    protected String nombre;
    protected String apellidos;
    protected Date fechaNacimiento;
    protected String url;
    
    protected String status="";

    /**
     * Creates a new instance of RegistroBean
     */
    public RegistroBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    public String doRegistrar(){
        
        //Excepciones
        if(email==null || email.isEmpty()){ status="Faltan parámetros: email"; return null;}
        if(password1==null || password1.isEmpty()){ status="Faltan parámetros: contraseña"; return null;}
        if(nombre==null || nombre.isEmpty()){ status="Faltan parámetros: nombre"; return null;}  //Obligamos a que pongan nombre, aunque en la bd es nulleable
        if(apellidos==null || apellidos.isEmpty()){status="Faltan parámetros: apellidos"; return null;}
        if(fechaNacimiento==null){ status="Faltan parámetros: fecha nacimiento"; return null;}
        
            // comprobamos si el usuario está en la BD
        //nuevo = this.usuarioFacade.findByEmailUsuario(email);
        UsuarioDTO nuevo = this.usuariosService.findByEmailUsuario(email);
        if (nuevo == null) { // el usuario no está, es correcto
            if(!password1.equals(password2)){
                status="Las contraseñas no coinciden";
                password1="";
                password2="";
                return null;
            }else{
                //Creamos usuario nuevo en la BD
                this.usuariosService.createOrUpdate(0, email, nombre, apellidos, fechaNacimiento, password1, url, false);
                
                //Realizamos login a través de UsuarioBean
                nuevo = this.usuariosService.findByEmailUsuario(email);
                usuarioBean.setUsuario(nuevo);
                return "menu";
            }
        }else{
            status= "El email ya está registrado en la aplicación";
            password1 = "";
            password2 = "";
            return null;
        }
    }
    
    public boolean checkFecha(){
        Date now = new Date();
        long diffInMillies = Math.abs(now.getTime() - fechaNacimiento.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long years = diff/365;

        if(fechaNacimiento.after(now)){
            return false;
        }
        if(years<18){
            return false;
        }
        return true;
    }
    
}
