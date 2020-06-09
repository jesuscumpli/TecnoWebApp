/*
@Author: Jesús Cumplido Almenara
         Francisco José García Rodríguez
*/

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
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.dto.SubcategoriaDTO;
import tecnoweb.entity.Categoria;
import tecnoweb.entity.Subcategoria;

@Stateless
public class SubcategoriasService {

    @EJB
    private SubcategoriaFacade subcategoriaFacade;
    
    @EJB
    private CategoriaFacade categoriaFacade;
    
    public SubcategoriaDTO find(Integer codSubcat){
        Subcategoria subCat = this.subcategoriaFacade.find(codSubcat);
        return subCat.getDTO();
    }
    
    public SubcategoriaDTO findByName(String subcategoriaString) {
        Subcategoria aux = this.subcategoriaFacade.findByName(subcategoriaString);
        return aux.getDTO();
    }
    
    public List<SubcategoriaDTO> findByCategoria(Integer cod){
        List<Subcategoria> lista = this.subcategoriaFacade.findByCategoria(cod);
        List<SubcategoriaDTO> res = new ArrayList<>();
        
        for(Subcategoria sub : lista){
            res.add(sub.getDTO());
        }
        
        return res;
    }
    
    public void remove(SubcategoriaDTO subcat){
        int id = subcat.getIdSubcategoria();  
        Subcategoria p = this.subcategoriaFacade.find(id);
        this.subcategoriaFacade.remove(p);
    }
    
    public void edit(SubcategoriaDTO subcat){
        Subcategoria aux = this.subcategoriaFacade.find(subcat.getIdSubcategoria());
        aux.setNombreSubcategoria(subcat.getNombreSubcategoria());
        this.subcategoriaFacade.edit(aux);
    }
    
    public void create(SubcategoriaDTO subcat){
        Subcategoria aux;
        aux = new Subcategoria(0);
        Categoria cat = this.categoriaFacade.find(subcat.getIdCategoria().getIdCategoria());
        aux.setIdCategoria(cat);
        aux.setNombreSubcategoria(subcat.getNombreSubcategoria());
        this.subcategoriaFacade.create(aux);
    }
    
}
