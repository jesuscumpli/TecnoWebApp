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
import tecnoweb.dto.CategoriaDTO;
import tecnoweb.dto.CategoriaMenuDTO;
import tecnoweb.entity.Categoria;


@Stateless
public class CategoriasService {

    @EJB
    private CategoriaFacade categoriaFacade;
    
    public CategoriaDTO find(Integer codCat){
        Categoria cat = this.categoriaFacade.find(codCat);
        return cat.getDTO();
    }
    
    public CategoriaMenuDTO findMenuDTO(Integer codCat){
        Categoria cat = this.categoriaFacade.find(codCat);
        return cat.getMenuDTO();
    }
    
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
    
    public CategoriaMenuDTO findByName(String cat) {
        Categoria categoria = this.categoriaFacade.findByName(cat);
        return categoria.getMenuDTO();
    }

    public CategoriaMenuDTO findById(int idCategoria) {
        Categoria categoria = this.categoriaFacade.find(idCategoria);
        return categoria.getMenuDTO();
    }
    
    public void remove(CategoriaDTO cat){
        int id = cat.getIdCategoria();  
        Categoria p = this.categoriaFacade.find(id);
        this.categoriaFacade.remove(p);
    }
    
    public void edit(CategoriaDTO cat){
        Categoria aux = this.categoriaFacade.find(cat.getIdCategoria());
        aux.setNombreCategoria(cat.getNombreCategoria());
        this.categoriaFacade.edit(aux);
    }
    
    public void create(CategoriaDTO cat){
        Categoria aux;
        aux = new Categoria(0);
        aux.setNombreCategoria(cat.getNombreCategoria());
        this.categoriaFacade.create(aux);
    }
    
    public void editMenuDTO(CategoriaMenuDTO cat){
        Categoria aux = this.categoriaFacade.find(cat.getIdCategoria());
        aux.setNombreCategoria(cat.getNombreCategoria());
        this.categoriaFacade.edit(aux);
    }
    
    public void createMenuDTO(CategoriaMenuDTO cat){
        Categoria aux;
        aux = new Categoria(0);
        aux.setNombreCategoria(cat.getNombreCategoria());
        this.categoriaFacade.create(aux);
    }
}
