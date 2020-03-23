/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tecnoweb.entity.Usuario;

/**
 *
 * @author Jesús
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "TecnoWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    // Esta consulta la he creado yo. A partir del email se busca al usuario.
    public Usuario findByEmailUsuario (String email) {
        try{
            Query q;
            Usuario usuario;

            // Las "Named Query" son consultas predefinidas que se ubican antes de la declaración
            // de la clase entidad, en este caso, "Usuario":
            // @NamedQuery(name = "Usuario.findByEmailUsuario", query = "SELECT a FROM Usuario a WHERE a.emailUsuario = :emailUsuario")
            q = this.getEntityManager().createNamedQuery("Usuario.findByEmailUsuario");
            q.setParameter("emailUsuario", email); // Los parámetros son aquellas cadenas de caracteres que van precedidas de los dos puntos.
            usuario = (Usuario) q.getSingleResult();
            return usuario;
        }catch(NoResultException e) {
            return null;
        }
    }
}
