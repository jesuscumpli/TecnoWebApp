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
public class PalabraclaveDTO {
    
    private int idPalabraclave;
    private String valor;
    //No hay lista de productos: productoList
    
    public PalabraclaveDTO() {
    }

    public int getIdPalabraclave() {
        return idPalabraclave;
    }

    public void setIdPalabraclave(int idPalabraclave) {
        this.idPalabraclave = idPalabraclave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
