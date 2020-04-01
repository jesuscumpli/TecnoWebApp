/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferFiles;

/**
 *
 * @author Jesús
 */
public class pruebaCopiarImagen {
    
    public static void main(String[] args){
        String urlOrigen = "C:/Users/Jesús/Desktop/Jesús/mis cosas/imagenes/IMG_20170920.jpg";
        String urlDestino = "C:/Users/Jesús/Downloads/nuevo1.jpg";
        CopiarImagen.copiarImagen(urlOrigen, urlDestino);
        System.out.println("Se ha ejecutado correctamente");
    }
    
}
