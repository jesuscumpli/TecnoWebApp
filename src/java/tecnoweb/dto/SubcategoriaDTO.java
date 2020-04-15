/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.dto;

/**
 *
 * @author Jesús
 */
public class SubcategoriaDTO {
    
    private int idSubcategoria;
    private String nombreSubcategoria;
    private CategoriaDTO idCategoria;
    //No hay lista de productos
    private int numProds;
    
    public SubcategoriaDTO(){
        
    }

    public int getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(int idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getNombreSubcategoria() {
        return nombreSubcategoria;
    }

    public void setNombreSubcategoria(String nombreSubcategoria) {
        this.nombreSubcategoria = nombreSubcategoria;
    }

    public CategoriaDTO getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(CategoriaDTO idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getNumProds() {
        return numProds;
    }

    public void setNumProds(int numProds) {
        this.numProds = numProds;
    }

}
