/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jesús
 */
public class ProductoMenuDTO {

    
    private int idProducto;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Date fechaSubida;
    private String fotoProducto;
    private SubcategoriaDTO idSubcategoria;
    private UsuarioDTO idUsuario;
    //No hay lista valoracion: valoracionList
    private List<PalabraclaveDTO> palabraclaveList;
    private Double notaMedia;   //Adicional
    private int numValoraciones;
    
    public ProductoMenuDTO(){
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    public int getNumValoraciones() {
        return numValoraciones;
    }

    public void setNumValoraciones(int numValoraciones) {
        this.numValoraciones = numValoraciones;
    }

    public void setFotoProducto(String fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public SubcategoriaDTO getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(SubcategoriaDTO idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public UsuarioDTO getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UsuarioDTO idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public List<PalabraclaveDTO> getPalabraclaveList() {
        return palabraclaveList;
    }

    public void setPalabraclaveList(List<PalabraclaveDTO> palabraclaveList) {
        this.palabraclaveList = palabraclaveList;
    } 
    
    public String getDescripcionShort(){
        if(this.getDescripcion().length()>200){
            return this.getDescripcion().substring(0,200) + "...";
        }else{
            return this.getDescripcion();
        }
    }
    
    public int getNotaEstrella(){
        return this.notaMedia.intValue() / 2;
    }
    
}
