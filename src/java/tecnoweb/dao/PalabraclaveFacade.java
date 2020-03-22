/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tecnoweb.entity.Palabraclave;

/**
 *
 * @author Jesús
 */
@Stateless
public class PalabraclaveFacade extends AbstractFacade<Palabraclave> {

    @PersistenceContext(unitName = "TecnoWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PalabraclaveFacade() {
        super(Palabraclave.class);
    }
    
}
