/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import tecnoweb.dao.CategoriaFacade;
import tecnoweb.dto.CategoriaDTO;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.entity.Categoria;

/**
 *
 * @author Jesús
 */
@Stateless
public class CategoriasService {

    @EJB
    private CategoriaFacade categoriaFacade;
    
    
    public List<CategoriaDTO> findAll(){
        List<Categoria> lista = this.categoriaFacade.findAll();
        List<CategoriaDTO> res = new ArrayList<>();
        for(Categoria c: lista){
            res.add(c.getDTO());
        }
        return res;
    }
    
    public List<CategoriaMenuDTO> findAllMenuDTO(){
        List<Categoria> lista = this.categoriaFacade.findAll();
        List<CategoriaMenuDTO> res = new ArrayList<>();
        for(Categoria c: lista){
            res.add(c.getMenuDTO());
        }
        return res;
    }
}
