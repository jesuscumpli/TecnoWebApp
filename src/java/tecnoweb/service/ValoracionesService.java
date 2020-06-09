/*
@Author: Luis Ramos Matas 
         Jesús Cumplido Almenara
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
import tecnoweb.dao.ValoracionFacade;
import tecnoweb.dto.ValoracionDTO;
import tecnoweb.entity.Valoracion;

/**
 *
 * @author Jesús
 */
@Stateless
public class ValoracionesService {

    @EJB
    private ProductosService productosService;

    @EJB
    private ValoracionFacade valoracionFacade;
    
    public List<ValoracionDTO> findByIdUsuario(Integer idUsuario){
        List<Valoracion> lista = this.valoracionFacade.findByIdUsuario(idUsuario);
        List<ValoracionDTO> res = new ArrayList<>();
        for(Valoracion v: lista){
            ValoracionDTO nueva = v.getDTO();
            Double notaMedia = this.productosService.getNotaMedia(nueva.getProducto().getIdProducto());
            nueva.getProducto().setNotaMedia(notaMedia);
            res.add(nueva);
        }
        return res;
    }
    
    public ValoracionDTO findValoracion(Integer idUsuario, Integer idProducto){
        Valoracion v = this.valoracionFacade.findValoracion(idUsuario, idProducto);
        if(v==null){
            return null;
        }else{
            return v.getDTO();
        }
    }
    
    public List<ValoracionDTO> findListaValoraciones(int idProducto){
        
        List<ValoracionDTO> listaValoracionesDTO=new ArrayList<>();
        List<Valoracion> listaValoraciones = this.valoracionFacade.findListaValoraciones(idProducto);

        for(Valoracion v : listaValoraciones){
            listaValoracionesDTO.add(v.getDTO());
        }
        return listaValoracionesDTO;

        
    }
    
    public List<ValoracionDTO> findByProducto(Integer idProd){
        
        List<ValoracionDTO> listaValoracionDTO=new ArrayList<>();
        List<Valoracion> listaValoracion = this.valoracionFacade.findByProducto(idProd);
        
        for(Valoracion v : listaValoracion){
            listaValoracionDTO.add(v.getDTO());
        }
        
        return listaValoracionDTO;
    }
 
    public void create(ValoracionDTO nueva) {
        Valoracion v = new Valoracion(0, nueva.getProducto().getIdProducto(), nueva.getUsuario().getIdUsuario());
        v.setComentario(nueva.getComentario());
        v.setFechaPublicacion(nueva.getFechaPublicacion());
        v.setNota(nueva.getNota());
        this.valoracionFacade.create(v);
    }

    public void edit(ValoracionDTO nueva) {
        int idUsuarioAux = nueva.getUsuario().getIdUsuario();
        int idProductoAux = nueva.getProducto().getIdProducto();
        Valoracion valoracionAux = this.valoracionFacade.findValoracion(idUsuarioAux, idProductoAux);
        valoracionAux.setComentario(nueva.getComentario());
        valoracionAux.setNota(nueva.getNota());
        valoracionAux.setFechaPublicacion(nueva.getFechaPublicacion());
        this.valoracionFacade.edit(valoracionAux);
    }
}
