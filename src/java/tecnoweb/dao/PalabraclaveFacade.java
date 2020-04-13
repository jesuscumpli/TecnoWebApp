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
    
    public List<Palabraclave> findByProducto(Integer idProd){
        Query q;
        
        q = this.getEntityManager().createQuery("SELECT p FROM Palabraclave p WHERE p.productoList.idProducto = :idProd ");
        q.setParameter("idProd", idProd);
        return q.getResultList();
    }
    
    public Palabraclave findByValue(String valor) {
        Query q;
        Palabraclave palabraClave = null;
        List<Palabraclave> lista;


        q = this.getEntityManager().createNamedQuery("Palabraclave.findByValor");
        q.setParameter("valor", valor); // Los parámetros son aquellas cadenas de caracteres que van precedidas de los dos puntos.

        lista = q.getResultList(); 
        // Si la lista está vacía, quiere decir que no hay ningún administrador con ese email
        if (lista != null && !lista.isEmpty()) { 
            palabraClave = lista.get(0); // Como sé que solo hay uno, devuelvo directament el primero
        }
        return palabraClave;
    }
   
}
