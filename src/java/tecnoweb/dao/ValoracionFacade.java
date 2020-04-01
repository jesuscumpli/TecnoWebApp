/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    
}
