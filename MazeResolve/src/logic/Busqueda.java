package logic;

import gui.PanelImg;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author jesgu_000
 */
public class Busqueda {
    
    private PanelImg panelImg;
    private final BufferedImage imagen;
    private int mObstaculos[][], x1, x2, y1, y2;
    
    public Busqueda(PanelImg panelImg) throws Exception{
        if(panelImg == null){
            throw new Exception("Problema con origen de datos");
        }
        
        if(panelImg.imagen == null){
            throw new Exception("Debe primero cargar una imagen");
        }
        
        if(panelImg.setPuntos != 2){
            throw new Exception("Debe colocar los puntos de inicio y final");
        }
        
        this.panelImg = panelImg;
        this.imagen = panelImg.imagen;
        
        double xMul = (double)panelImg.imagen.getWidth() / (double)panelImg.getWidth();
        double yMul = (double)panelImg.imagen.getHeight() / (double)panelImg.getHeight();
        System.out.println("Ancho: " + panelImg.imagen.getWidth() + "/" + panelImg.getWidth() + " = " + xMul);
        System.out.println("Alto: " + panelImg.imagen.getHeight() + "/" + panelImg.getHeight() + " = " + yMul);
        
        this.x1 = (int)(panelImg.x1 * xMul);
        this.y1 = (int)(panelImg.y1 * yMul);
        this.x2 = (int)(panelImg.x2 * xMul);
        this.y2 = (int)(panelImg.y2 * yMul);
        System.out.println("x1: " + x1 + " y1: " + y1);
        System.out.println("x2: " + x2 + " y2: " + y2);
    }
    
    public void solucionar() throws Exception{
        //Crear matriz de obstaculos
        crearMatriz();
        
        //Verficar que puntos no sean obstaculos
        if(!puntosOk()){
            throw new Exception("Puntos mal colocados");
        }
        
        System.out.println("Todo bien :D");
    }
    
    private void crearMatriz(){
        //System.out.println("Width: " + imagen.getWidth() + " Height: " + imagen.getHeight() + " Total: " + (imagen.getWidth() * imagen.getHeight()));
        mObstaculos = new int[imagen.getWidth()][imagen.getHeight()];
        
        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                Color c = new Color(imagen.getRGB(i, j));
                if(c.getRed() >= 240 && c.getGreen() >= 240 && c.getBlue() >= 240){
                    mObstaculos[i][j] = 0;
                }else{
                    mObstaculos[i][j] = 1;
                }
            }
        }
        
        System.out.println("terminado");
    }
    
    private boolean puntosOk(){
        return mObstaculos[x1][y1] == 0 && mObstaculos[x2][y2] == 0;
    }
    
    private boolean esObstaculo(int i, int j){
        return mObstaculos[i][j] == 1;
    }
}
