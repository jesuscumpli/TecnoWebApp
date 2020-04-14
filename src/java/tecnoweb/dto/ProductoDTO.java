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
public class ProductoDTO {
    
    private int idProducto;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Date fechaSubida;
    private String fotoProducto;
    private SubcategoriaDTO idSubcategoria;
    //private UsuarioDTO idUsuario;
    //No hay lista valoracion: valoracionList
    //No hay lista palabras claves: palabraclaveList
    
    public ProductoDTO(){
        
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(String fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public SubcategoriaDTO getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(SubcategoriaDTO idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }
    
}
