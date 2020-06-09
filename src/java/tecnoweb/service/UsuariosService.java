/*
@Author: Ángel Baeza Expósito
         Francisco José García Rodríguez
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.dto.UsuarioDTO;
import tecnoweb.entity.Usuario;


@Stateless
public class UsuariosService {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    public List<UsuarioDTO> findAllDTO(){
        List<Usuario> lista = this.usuarioFacade.findAll();
        List<UsuarioDTO> res = new ArrayList<>();
        for(Usuario usr : lista){
            res.add(usr.getDTO());
        }
        return res;
    }
    
    public UsuarioDTO findByIdDTO(Integer idUsr){
        Usuario usr = this.usuarioFacade.find(idUsr);
        if(usr != null) return usr.getDTO();
        else return null;
    }
    
    public void createOrUpdate(Integer idUsr, String email, String nombre, String apellidos, Date fecha, String pwd, String photo, Boolean isAdmin){
        Usuario usr = this.usuarioFacade.find(idUsr);
        boolean esNuevo = false;
        if(usr == null) {
            usr = new Usuario(0);
            esNuevo = true;
        }
        usr.setEmailUsuario(email);
        usr.setNombre(nombre);
        usr.setApellidos(apellidos);
        usr.setFechaNac(fecha);
        usr.setPassword(pwd);
        usr.setFotoUsuario(photo);
        usr.setIsAdmin(isAdmin);
        
        if (esNuevo) {
            this.usuarioFacade.create(usr);
        } else {
            this.usuarioFacade.edit(usr);
        }
    }
    
    public void remove(Integer idUsr){
        Usuario usr = this.usuarioFacade.find(idUsr);
        if(usr != null) this.usuarioFacade.remove(usr);
    }
    
    public void remove2(UsuarioDTO usuario){
        int id = usuario.getIdUsuario();  
        Usuario p = this.usuarioFacade.find(id);
        this.usuarioFacade.remove(p);
    }
    
    public void edit(UsuarioDTO usuario){
        Usuario aux = this.usuarioFacade.find(usuario.getIdUsuario());
        aux.setNombre(usuario.getNombre());
        aux.setFotoUsuario(usuario.getFotoUsuario());
        aux.setApellidos(usuario.getApellidos());
        aux.setFechaNac(usuario.getFechaNac());
        aux.setEmailUsuario(usuario.getEmailUsuario());
        this.usuarioFacade.edit(aux);
    }
    
    public UsuarioDTO findByEmailUsuario(String email){
        Usuario usuario = this.usuarioFacade.findByEmailUsuario(email);
        if(usuario==null){
            return null;
        }else{
            return usuario.getDTO();
        }
    }
}
