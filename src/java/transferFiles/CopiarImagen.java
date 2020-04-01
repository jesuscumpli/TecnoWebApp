/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferFiles;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author Jesús
 */
public class CopiarImagen {
    
    public static void copiarImagen(String urlOrigen, String urlDestino){
        try{
            FileInputStream fis = new FileInputStream(urlOrigen); //inFile -> Archivo a copiar
            System.out.print("paso1");
            FileOutputStream fos = new FileOutputStream(urlDestino); //outFile -> Copia del archivo
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            fis.close();
            fos.close();
        }catch (IOException e) {
            System.err.println("Error al Generar Copia: "+e.getMessage());
        }
    }
    
}
