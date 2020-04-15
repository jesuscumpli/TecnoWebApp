/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dto;

import java.util.Date;

/**
 *
 * @author Jesús
 */
public class ValoracionDTO {
    
    private int idValoracion;
    private String comentario;
    private Double nota;
    private Date fechaPublicacion;
    private ProductoMenuDTO producto;
    private UsuarioDTO usuario;
    
    public ValoracionDTO(){
        
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public ProductoMenuDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoMenuDTO producto) {
        this.producto = producto;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public int getIdValoracion() {
        return idValoracion;
    }

    public void setIdValoracion(int idValoracion) {
        this.idValoracion = idValoracion;
    }
    
}
