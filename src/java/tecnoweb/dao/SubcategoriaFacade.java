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
import tecnoweb.entity.Subcategoria;

/**
 *
 * @author alvar
 */
@Stateless
public class SubcategoriaFacade extends AbstractFacade<Subcategoria> {

    @PersistenceContext(unitName = "TecnoWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubcategoriaFacade() {
        super(Subcategoria.class);
    }

    public Subcategoria findByName(String name) {
        Query q;
        Subcategoria subcategoria = null;
        List<Subcategoria> lista;
        
    
        q = this.getEntityManager().createNamedQuery("Subcategoria.findByNombreSubcategoria");
        q.setParameter("nombreSubcategoria", name); // Los parámetros son aquellas cadenas de caracteres que van precedidas de los dos puntos.

        lista = q.getResultList(); 
        // Si la lista está vacía, quiere decir que no hay ningún administrador con ese email
        if (lista != null && !lista.isEmpty()) { 
            subcategoria = lista.get(0); // Como sé que solo hay uno, devuelvo directament el primero
        }
        return subcategoria;
    }
    
}
