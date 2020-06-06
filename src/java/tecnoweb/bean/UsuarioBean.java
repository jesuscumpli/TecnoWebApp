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
import javax.inject.Inject;
import javax.inject.Named;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.service.ProductosService;
import tecnoweb.service.UsuariosService;

/**
 *
 * @author Jesús
 */
@Named (value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable{
    @Inject
    private MenuBean menuBean;

    @EJB
    private UsuariosService usuariosService;
    
    @EJB
    private ProductosService productosService;
    

    protected String email;
    protected String password; 
    protected String status = "";
    
    protected UsuarioDTO usuario;
    protected ProductoMenuDTO productoSeleccionado;

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

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
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
                if(usuario.getIsAdmin()) return "menuAdmin?faces-redirect=true";
                else return "menu?faces-redirect=true";
            }        
        }
    }
    
    public String doLogout(){
        menuBean.setProductoSeleccionado(null);
        menuBean.setIdCatSelected(-1);
        menuBean.setSubcatSelected(null);
        menuBean.setBusqueda("");
        menuBean.setOrden("Fecha");
        menuBean.setProductos(this.productosService.findAllMenuDTO());
        this.usuario = null;
        this.status = "";
        this.email = "";
        this.password = "";
        return "login?faces-redirect=true";
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
    
    public String doEditar(ProductoMenuDTO producto) {
        this.productoSeleccionado = producto;
        return "nuevoProducto";
    }
    
    public String doVerValoraciones (ProductoMenuDTO producto) {
         this.productoSeleccionado = producto;
         return "listaValoracionesMiProducto";
    }

    public ProductoMenuDTO getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoMenuDTO productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }
    
    public String resetProducto() {
        this.productoSeleccionado=null;
        return "listadoProducto";
    }
    
}
