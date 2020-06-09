/*
@Author: Álvaro Nieto González
         Jesús Cumplido Almenara
*/

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
import tecnoweb.dto.PalabraclaveDTO;
import tecnoweb.dto.ProductoDTO;
import tecnoweb.dto.ProductoMenuDTO;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Producto;
import tecnoweb.entity.Subcategoria;
import tecnoweb.entity.Usuario;
import tecnoweb.entity.Valoracion;


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
        //List<Palabraclave> claves = this.palabraclaveFacade.findByProducto(p.getIdProducto());
        for(PalabraclaveDTO pad : p.getPalabraclaveList()){
            Palabraclave pad2 = this.palabraclaveFacade.findByValue(pad.getValor());
            lista.add(pad2);
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
            //Actualizamos subcategoria.getProductoList
            subcategoria.getProductoList().add(producto);
            this.subcategoriaFacade.edit(subcategoria);
        } else {
            this.productoFacade.edit(producto);
        }            
    }
    
    public List<ProductoMenuDTO> filtrarSubcategoriaBusqueda(Integer idSubcat, String busqueda){
        List<Producto> lista = this.productoFacade.filtrarSubcategoriaBusqueda(idSubcat, busqueda);
        List<ProductoMenuDTO> res = new ArrayList<>();
        for (Producto p: lista) {
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            res.add(nuevo);
        }
        return res;
    }
    
    public List<ProductoMenuDTO> filtrarCategoriaBusqueda(Integer idCat, String busqueda){
        List<Producto> lista = this.productoFacade.filtrarCategoriaBusqueda(idCat, busqueda);
        List<ProductoMenuDTO> res = new ArrayList<>();
        for (Producto p: lista) {
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            res.add(nuevo);
        }
        return res;
    }
    
    public List<ProductoMenuDTO> filtrarBusqueda( String busqueda){
        List<Producto> lista = this.productoFacade.filtrarBusqueda(busqueda);
        List<ProductoMenuDTO> res = new ArrayList<>();
        for (Producto p: lista) {
            ProductoMenuDTO nuevo = p.getMenuDTO();
            nuevo.setNotaMedia(this.getNotaMedia(p.getIdProducto()));
            res.add(nuevo);
        }
        return res;
    }
    
    public void createOrUpdate (ProductoMenuDTO p) {
        
        Producto producto;
        boolean esCrearNuevo = false;
        if (p.getIdProducto() == 0 || Integer.toString(p.getIdProducto()) == null || Integer.toString(p.getIdProducto()).isEmpty()) { // Creación de un nuevo producto
            producto = new Producto();
            esCrearNuevo = true;
        } else {
            producto = this.productoFacade.find(p.getIdProducto());
        }

        producto.setTitulo(p.getTitulo());
        producto.setDescripcion(p.getDescripcion());
        producto.setPrecio(p.getPrecio());
        producto.setFechaSubida(new Date(System.currentTimeMillis()));
        producto.setFotoProducto(p.getFotoProducto());
        Subcategoria subcategoria = this.subcategoriaFacade.find(p.getIdSubcategoria().getIdSubcategoria());
        producto.setIdSubcategoria(subcategoria);
        
        List<PalabraclaveDTO> palabrasClave = p.getPalabraclaveList();
        List<String> tags = new ArrayList<>();
        if(!palabrasClave.isEmpty()){
            for (PalabraclaveDTO pcd : palabrasClave) {
                tags.add(pcd.getValor());
            }  
        }

        List<Palabraclave> listaClaves = new ArrayList<>();

        for (String valor : tags) {
            Palabraclave pc = this.palabraclaveFacade.findByValue(valor.trim());
            if (pc == null) {        //Si no existe la creo
                pc = new Palabraclave();
                pc.setValor(valor.trim());
                this.palabraclaveFacade.create(pc);    
            }
            listaClaves.add(pc);
        }
        producto.setPalabraclaveList(listaClaves);
        Usuario u = this.usuarioFacade.find(p.getIdUsuario().getIdUsuario());
        producto.setIdUsuario(u);                    

        if (esCrearNuevo) {
            this.productoFacade.create(producto);
            subcategoria.getProductoList().add(producto);
            this.subcategoriaFacade.edit(subcategoria);
        } else {
            this.productoFacade.edit(producto);
        }            
    }
}
