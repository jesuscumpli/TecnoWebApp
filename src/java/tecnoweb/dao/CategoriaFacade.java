/*
@Author: Álvaro Nieto González
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tecnoweb.entity.Categoria;

@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "TecnoWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }
    
    public Categoria findByName(String name) {
        Query q;
        Categoria categoria = null;
        List<Categoria> lista;

        // Las "Named Query" son consultas predefinidas que se ubican antes de la declaración
        // de la clase entidad, en este caso, "Administrador":
        // @NamedQuery(name = "Administrador.findByEmail", query = "SELECT a FROM Administrador a WHERE a.email = :email")
        q = this.getEntityManager().createNamedQuery("Categoria.findByNombreCategoria");
        q.setParameter("nombreCategoria", name); // Los parámetros son aquellas cadenas de caracteres que van precedidas de los dos puntos.

        lista = q.getResultList(); 
        // Si la lista está vacía, quiere decir que no hay ningún administrador con ese email
        if (lista != null && !lista.isEmpty()) { 
            categoria = lista.get(0); // Como sé que solo hay uno, devuelvo directament el primero
        }
        return categoria;
    }
    
}
