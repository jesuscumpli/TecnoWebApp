/*
@Author: Jesús Cumplido Almenara
         Álvaro Nieto González
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
import tecnoweb.entity.Producto;

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
    
    public List<Producto> findByIdUsuario(Integer idUsuario) {
        Query q;
        List<Producto> producto;

        q = this.getEntityManager().createNamedQuery("Producto.findByIdUsuario");
        q.setParameter("idUsuario", idUsuario); 
        producto =  q.getResultList();
        return producto;
    }
    
    public List<Producto> filtrarBusqueda( String busqueda){
        Query q;
        List<Producto> productos;
        
        q = this.getEntityManager().createQuery("SELECT distinct p FROM Producto p JOIN p.palabraclaveList cv WHERE (p.titulo like CONCAT('%',:b,'%') OR p.descripcion like CONCAT('%',:b,'%') OR cv.valor=:b) ");
        q.setParameter("b",busqueda);
        productos = q.getResultList();
        
        return productos;
    }
    
    public List<Producto> filtrarSubcategoriaBusqueda(Integer idSubcat, String busqueda){
        Query q;
        List<Producto> productos;
        
        q = this.getEntityManager().createQuery("SELECT distinct p FROM Producto p JOIN p.palabraclaveList cv WHERE p.idSubcategoria.idSubcategoria=:idSubcat "
                + "AND (p.titulo like :b OR p.descripcion like :b OR cv.valor=:b) ");
        q.setParameter("idSubcat", idSubcat);
        q.setParameter("b","%"+busqueda+"%");
        productos = q.getResultList();
        
        return productos;
    }
    
    public List<Producto> filtrarCategoriaBusqueda(Integer idCat, String busqueda){
        Query q;
        List<Producto> productos;
        
        q = this.getEntityManager().createQuery("SELECT distinct p FROM Producto p JOIN p.palabraclaveList cv WHERE p.idSubcategoria.idCategoria.idCategoria=:idCat "
                + "AND (p.titulo like :b OR p.descripcion like :b OR cv.valor=:b) ");
        q.setParameter("idCat", idCat);
        q.setParameter("b","%"+busqueda+"%");
        productos = q.getResultList();
        
        return productos;
    }
   
}
