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
    
    public Valoracion findValoracion(int idUsuario, int idProducto){
        try{
            Query q;
            Valoracion v;

            q = this.getEntityManager().createNamedQuery("Valoracion.findValoracion");
            q.setParameter("idUsuario", idUsuario); 
            q.setParameter("idProducto",idProducto);
            v = (Valoracion) q.getSingleResult();
            return v;
        }catch(NoResultException e) {
            return null;
        }
    }
    
}
