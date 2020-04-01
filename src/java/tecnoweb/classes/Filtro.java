/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tecnoweb.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import tecnoweb.entity.Palabraclave;
import tecnoweb.entity.Producto;

/**
 *
 * @author Jesús
 */
public class Filtro {
    
    public static List<Producto> filtrarTitulo(List<Producto> productos, String filtro){
        List<Producto> result = new ArrayList<>();
        for(Producto p: productos){
            if(p.getTitulo().toLowerCase().contains(filtro.toLowerCase())){
                result.add(p);
            }
        }
        return result;
    }
    
    public static List<Producto> filtrarDescripcion(List<Producto> productos, String filtro){
        List<Producto> result = new ArrayList<>();
        for(Producto p: productos){
            if(p.getDescripcion().toLowerCase().contains(filtro.toLowerCase())){
                result.add(p);
            }
        }
        return result;
    }
    
    public static List<Producto> filtrarPalabrasClave(List<Producto> productos, String filtro){
        List<Producto> result = new ArrayList<>();
        for(Producto p: productos){
            Iterator<Palabraclave> it = p.getPalabraclaveList().iterator();
            boolean found = false;
            while(found && it.hasNext()){
                Palabraclave clave = it.next();
                if(clave.getValor().equalsIgnoreCase(filtro)){
                    found = true;
                    result.add(p);
                }
            }
        }
        return result;
    }
    
    public static void ordenarProductos(String orden, List<Producto> productos){
            switch (orden) {
                case "Fecha":
                    Collections.sort(productos, (p1,p2) -> {return p2.getFechaSubida().compareTo(p1.getFechaSubida()); } );
                    break;
                case "Precio":
                    Collections.sort(productos, (p1,p2) -> {return ((Double)p1.getPrecio()).compareTo((Double)p2.getPrecio()); } );
                    break;
                case "Valoracion":
                    Collections.sort(productos, (p1,p2) -> {return (p2.getNotaMedia().compareTo(p1.getNotaMedia()));});
                    break;
                default:
                    Collections.sort(productos, (p1,p2) -> {return p1.getTitulo().compareTo(p2.getTitulo()); } );
            }
    }
}
