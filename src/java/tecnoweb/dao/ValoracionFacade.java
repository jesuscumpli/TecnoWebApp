/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Valoracion;

/**
 *
 * @author luisr
 */
@Stateless
public class ValoracionFacade extends AbstractFacade<Valoracion> {

    @PersistenceContext(unitName = "TecnoWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValoracionFacade() {
        super(Valoracion.class);
    }
    
    public Valoracion findValoracion(Integer idUsuario, Integer idProducto){

        Query q;
        Valoracion v = null;

        q = this.getEntityManager().createQuery("SELECT v FROM Valoracion v WHERE v.valoracionPK.idUsuario = :idUsuario AND v.valoracionPK.idProducto = :idProducto ");
        q.setParameter("idUsuario", idUsuario); 
        q.setParameter("idProducto",idProducto);
        List<Valoracion> lista = q.getResultList();
        if(lista!=null && !lista.isEmpty()){
            v = lista.get(0);
        }
        return v;
    }
    
    public List<Valoracion> findListaValoraciones(int idProducto){
        Query q;
        List<Valoracion> listaValoraciones = new ArrayList<>();
        
        q = this.getEntityManager().createNamedQuery("Valoracion.findListaValoraciones");
        q.setParameter("idProducto",idProducto);
        listaValoraciones = q.getResultList();
        
        return listaValoraciones;
    }
    
    public List<Valoracion> findByProducto(Integer idProd){
        Query q;

        q = this.getEntityManager().createQuery("SELECT v FROM Valoracion v WHERE v.producto.idProducto = :idProd");
        q.setParameter("idProd", idProd);
        return q.getResultList();
    }
    
    public List<Valoracion> findByIdUsuario(Integer idUsuario){
        Query q;
        List<Valoracion> valoraciones;

        q = this.getEntityManager().createNamedQuery("Valoracion.findByIdUsuario");
        q.setParameter("idUsuario", idUsuario); 
        valoraciones =  q.getResultList();
        return valoraciones;
    }
}
