/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import tecnoweb.dao.PalabraclaveFacade;
import tecnoweb.dao.ProductoFacade;
import tecnoweb.dao.SubcategoriaFacade;
import tecnoweb.dao.UsuarioFacade;
import tecnoweb.dao.ValoracionFacade;
import tecnoweb.dto.ProductoDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Subcategoria;
import tecnoweb.entity.Usuario;
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
    
    @EJB
    private SubcategoriaFacade subcategoriaFacade;
    
    @EJB
    private PalabraclaveFacade palabraclaveFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    public ProductoMenuDTO find(Integer id) {
        Producto p = this.productoFacade.find(id);
        return p.getMenuDTO();
    }
    
    public List<ProductoMenuDTO> findAllMenuDTO(){
        List<Producto> lista = this.productoFacade.findAll();
        List<ProductoMenuDTO> res = new ArrayList<>();
        for(Producto p: lista){
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            //nuevo.setNotaMedia(p.getNotaMedia());
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
    
    public List<ProductoMenuDTO> findByIdUsuario(int idUsuario) {
        List<Producto> lista = this.productoFacade.findByIdUsuario(idUsuario);
        List<ProductoMenuDTO> res = new ArrayList<>();
        for (Producto p: lista) {
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            res.add(nuevo);
        }
        return res;
    }
    
    public ProductoMenuDTO findByIdProducto(Integer cod){
        Producto p= this.productoFacade.find(cod);

        return p.getMenuDTO();
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
    
    public void remove(ProductoMenuDTO producto){
        int id = producto.getIdProducto();  
        Producto p = this.productoFacade.find(id);
        this.productoFacade.remove(p);
    }

    

    
    public void edit(ProductoMenuDTO p){
        Producto aux = this.productoFacade.find(p.getIdProducto());
        aux.setDescripcion(p.getDescripcion());
        aux.setFotoProducto(p.getFotoProducto());
        aux.setPrecio(p.getPrecio());
        aux.setTitulo(p.getTitulo());
        List<Palabraclave> lista = new ArrayList<>();
        List<Palabraclave> claves = this.palabraclaveFacade.findByProducto(p.getIdProducto());
        for(Palabraclave pad : claves){
            lista.add(pad);
        }
        aux.setPalabraclaveList(lista);
        this.productoFacade.edit(aux);
    }
    
    public void createOrUpdate (String productoId, String nombre, String descripcion, String precio, 
                                String url, Integer idSubcat, String palabrasClave, Integer usuario) {
        
        Producto producto;
        boolean esCrearNuevo = false;
        if (productoId == null || productoId.isEmpty()) { // Creación de un nuevo producto
            //producto = new Producto(0); ANTES SIN DTO
            producto = new Producto();
            esCrearNuevo = true;
        } else {
            //producto = this.productoFacade.find(new Integer(str));
            producto = this.productoFacade.find(new Integer(productoId));
        }

        producto.setTitulo(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(new Double(precio));
        producto.setFechaSubida(new Date(System.currentTimeMillis()));
        producto.setFotoProducto(url);
        Subcategoria subcategoria = this.subcategoriaFacade.find(idSubcat);
        producto.setIdSubcategoria(subcategoria);

        String[] tags = null;
        if( palabrasClave.isEmpty()){
            tags = new String[0];
        }else{
            tags = palabrasClave.split(",");
        }

        List<Palabraclave> listaClaves = new ArrayList<>();

        for (String valor : tags) {
            Palabraclave p = this.palabraclaveFacade.findByValue(valor.trim());
            if (p == null) {        //Si no existe la creo
                p = new Palabraclave();
                p.setValor(valor.trim());
                this.palabraclaveFacade.create(p);    
            }
            listaClaves.add(p);
        }
        producto.setPalabraclaveList(listaClaves);
        Usuario u = this.usuarioFacade.find(usuario);
        producto.setIdUsuario(u);                    

        if (esCrearNuevo) {
            this.productoFacade.create(producto); 
        } else {
            this.productoFacade.edit(producto);                    
        }            
    }
}
