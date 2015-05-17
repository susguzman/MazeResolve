/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Jesus
 */
public class PanelImg extends JPanel{
    
    public BufferedImage imagen;
    public int x1, x2, y1, y2, setPuntos;
    
    public PanelImg(){
        this(null);
    }
    
    public PanelImg(BufferedImage imagen){
        this.imagen = imagen;
        this.setPuntos = 0;
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClicked_panel(evt);
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        //Pintar imagen
        if(imagen != null){
            g.drawImage(imagen, 0, 0, this.getWidth(), this.getHeight(), this);           
        }
        
        //Pintar puntos
        if(setPuntos > 0){
            g.setColor(Color.red);
            g.fillOval(x1, y1, 10, 10);
        }
        
        if(setPuntos == 2){
            g.setColor(Color.blue);
            g.fillOval(x2, y2, 10, 10);
        }
        
        //g.setColor(Color.black);
        //g.drawRect(50, 50, 100, 100);
        super.paint(g);
    }
    
    private void mouseClicked_panel(java.awt.event.MouseEvent evt){
        if(setPuntos == 2){
            setPuntos = 0;
        }
        
        if(setPuntos == 0){
            x1 = evt.getX();
            y1 = evt.getY();
        }else{
            x2 = evt.getX();
            y2 = evt.getY();
        }
        
        System.out.println("clic. X: " + evt.getX() + " Y: " + evt.getY());
        setPuntos++;
        this.repaint();
    }
    
}
