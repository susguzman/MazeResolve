package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author jesgu_000
 */
public class Busqueda {
    
    private final BufferedImage imagen;
    private int mObstaculos[][];
    
    public Busqueda(BufferedImage imagen) throws Exception{
        if(imagen == null){
            throw new Exception("La imagen no puede ser null");
        }
        
        this.imagen = imagen;
    }
    
    public void solucionar(){
        crearMatriz();
    }
    
    private void crearMatriz(){
        System.out.println("Width: " + imagen.getWidth() + " Height: " + imagen.getHeight() + " Total: " + (imagen.getWidth() * imagen.getHeight()));
        mObstaculos = new int[imagen.getWidth()][imagen.getHeight()];
        
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                Color c = new Color(imagen.getRGB(i, j));
                if(c.getRed() >= 240 && c.getGreen() >= 240 && c.getBlue() >= 240){
                    mObstaculos[i][j] = 1;
                }else{
                    mObstaculos[i][j] = 0;
                }
            }
        }
    }
    
    private boolean esObstaculo(int i, int j){
        return mObstaculos[i][j] == 1;
    }
}
