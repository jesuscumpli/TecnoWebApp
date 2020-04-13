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
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.ValoracionFacade;
import tecnoweb.dto.ProductoDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Valoracion;

/**
 *
 * @author Jesús
 */

@Stateless
public class ProductosService {

    @EJB
    private ValoracionFacade valoracionFacade;

    @EJB
    private ProductoFacade productoFacade;
    
    public List<ProductoMenuDTO> findAllMenuDTO(){
        List<Producto> lista = this.productoFacade.findAll();
        List<ProductoMenuDTO> res = new ArrayList<>();
        for(Producto p: lista){
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            res.add(nuevo);
        }
        return res;
    }
    
    public List<ProductoDTO> findAllDTO(){
        List<Producto> lista = this.productoFacade.findAll();
        List<ProductoDTO> res = new ArrayList<>();
        for(Producto p: lista){
            res.add(p.getDTO());
        }
        return res;
    }
    
    public List<ProductoDTO> findBySubcategoria(Integer codSubcat){
        List<Producto> lista = this.productoFacade.findBySubcategoria(codSubcat);
        List<ProductoDTO> res = new ArrayList<>();
        for(Producto p: lista){
            res.add(p.getDTO());
        }
        return res;
    }
    
    public List<ProductoMenuDTO> findBySubcategoriaMenuDTO(Integer codSubcat){
        List<Producto> lista = this.productoFacade.findBySubcategoria(codSubcat);
        List<ProductoMenuDTO> res = new ArrayList<>();
        for(Producto p: lista){
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            res.add(nuevo);
        }
        return res;
    }
    
    public List<ProductoDTO> findByCategoria(Integer codCat){
        List<Producto> lista = this.productoFacade.findByCategoria(codCat);
        List<ProductoDTO> res = new ArrayList<>();
        for(Producto p: lista){
            res.add(p.getDTO());
        }
        return res;
    }
    
    public List<ProductoMenuDTO> findByCategoriaMenuDTO(Integer codCat){
        List<Producto> lista = this.productoFacade.findByCategoria(codCat);
        List<ProductoMenuDTO> res = new ArrayList<>();
        for(Producto p: lista){
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            res.add(nuevo);
        }
        return res;
    }
    
    
    //MÉTODO ADICIONAL
    public Double getNotaMedia(Integer codProd){
        Double result = 0.0;
        List<Valoracion> lista = this.valoracionFacade.findByProducto(codProd);
        for(Valoracion v: lista){
            result += v.getNota();
        }
        if(!lista.isEmpty()){
            result = result / lista.size();
        }
        
        return result;
    }
}
