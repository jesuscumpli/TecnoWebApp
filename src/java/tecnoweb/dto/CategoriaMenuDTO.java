/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dto;

import java.util.List;

/**
 *
 * @author Jesús
 */
public class CategoriaMenuDTO {
    
    private int idCategoria;
    private String nombreCategoria;
    private List<SubcategoriaDTO> subcategoriaList;
    
    public CategoriaMenuDTO(){
        
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public List<SubcategoriaDTO> getSubcategoriaList() {
        return subcategoriaList;
    }

    public void setSubcategoriaList(List<SubcategoriaDTO> subcategoriaList) {
        this.subcategoriaList = subcategoriaList;
    }
    
}
