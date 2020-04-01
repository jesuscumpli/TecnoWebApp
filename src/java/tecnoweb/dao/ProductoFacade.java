/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dao;

import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tecnoweb.entity.Producto;

/**
 *
 * @author Jesús
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "TecnoWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    public List<Producto> findBySubcategoria(Integer codSubcat){
        Query q;
        
        q = this.getEntityManager().createQuery("SELECT p FROM Producto p WHERE p.idSubcategoria.idSubcategoria = :codSubcat");
        q.setParameter("codSubcat", codSubcat);
        return q.getResultList();
    }
    
    public List<Producto> findByCategoria(Integer codCat){
        Query q;
        
        q = this.getEntityManager().createQuery("SELECT p FROM Producto p WHERE p.idSubcategoria.idCategoria.idCategoria = :codCat");
        q.setParameter("codCat", codCat);
        return q.getResultList();
    }
   
}
