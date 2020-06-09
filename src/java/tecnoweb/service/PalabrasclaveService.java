/*
@Author: Álvaro Nieto González
*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.service;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import tecnoweb.dao.PalabraclaveFacade;
import tecnoweb.dto.PalabraclaveDTO;
import tecnoweb.entity.Palabraclave;

@Stateless
public class PalabrasclaveService {
    
    @EJB
    private PalabraclaveFacade palabraClaveFacade;

    public PalabraclaveDTO findByValue(String valor) {
        Palabraclave aux = this.palabraClaveFacade.findByValue(valor);
        if(aux==null){
            return null;
        } else{
            return aux.getDTO();
        }
    }

    public void create(PalabraclaveDTO p) {
        Palabraclave aux = new Palabraclave();
        aux.setValor(p.getValor());
        this.palabraClaveFacade.create(aux);
    }
    
    
}
