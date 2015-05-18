package logic;

import gui.PanelImg;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author jesgu_000
 */
public class Busqueda {

    private final BufferedImage imagen;
    private int mObstaculos[][], x1, x2, y1, y2;
    private ListaBusqueda ltsOpen;
    private ListaBusqueda ltsClose;

    public Busqueda(PanelImg panelImg) throws Exception {
        if (panelImg == null) {
            throw new Exception("Problema con origen de datos");
        }

        if (panelImg.imagen == null) {
            throw new Exception("Debe primero cargar una imagen");
        }

        if (panelImg.setPuntos != 2) {
            throw new Exception("Debe colocar los puntos de inicio y final");
        }

        this.imagen = panelImg.imagen;

        double xMul = (double) panelImg.imagen.getWidth() / (double) panelImg.getWidth();
        double yMul = (double) panelImg.imagen.getHeight() / (double) panelImg.getHeight();
        System.out.println("Ancho: " + panelImg.imagen.getWidth() + "/" + panelImg.getWidth() + " = " + xMul);
        System.out.println("Alto: " + panelImg.imagen.getHeight() + "/" + panelImg.getHeight() + " = " + yMul);

        this.x1 = (int) (panelImg.x1 * xMul);
        this.y1 = (int) (panelImg.y1 * yMul);
        this.x2 = (int) (panelImg.x2 * xMul);
        this.y2 = (int) (panelImg.y2 * yMul);
        System.out.println("x1: " + x1 + " y1: " + y1);
        System.out.println("x2: " + x2 + " y2: " + y2);
    }

    private void crearMatriz() {
        //System.out.println("Width: " + imagen.getWidth() + " Height: " + imagen.getHeight() + " Total: " + (imagen.getWidth() * imagen.getHeight()));
        mObstaculos = new int[imagen.getWidth()][imagen.getHeight()];

        for (int i = 0; i < imagen.getWidth(); i++) {
            for (int j = 0; j < imagen.getHeight(); j++) {
                Color c = new Color(imagen.getRGB(i, j));
                if (c.getRed() >= 250 && c.getGreen() >= 250 && c.getBlue() >= 250) {
                    mObstaculos[i][j] = 0;
                } else {
                    mObstaculos[i][j] = 1;
                }
            }
        }
    }

    private boolean puntosOk() {
        return mObstaculos[x1][y1] == 0 && mObstaculos[x2][y2] == 0;
    }

    private boolean celdaValida(Celda c) {
        if (c.x >= 0 && c.x < mObstaculos.length && c.y >= 0 && c.y < mObstaculos[0].length) {
            if(mObstaculos[c.x][c.y] == 0){
                return true;
            }else{
                return false;
            }
        }
        
        return false;
    }
    
    private int calcularH(int x, int y){
        return 10 * (Math.abs(y2 - y) + Math.abs(x2 - x));
    }
    
    private void pintarRuta(Celda c){
        while(c.padre != null){
            imagen.setRGB(c.x, c.y, Color.BLUE.getRGB());
            c = c.padre;
        }
    }

    private boolean aEstrella() {        
        //Agregar origen a lista abierta
        Celda origen = new Celda(null, x1, y1, 0, 0, calcularH(x1, y1));
        ltsOpen.add(origen);

        //Si la lista esta vacia no hay solucion
        while (!ltsOpen.esVacia()) {
            //Get primer elemento de lista abierta y pasar a cerrada
            Celda c = ltsOpen.getMenor();
            ltsClose.add(c);

            //Crear listas de adyacentes con factores
            LinkedList<Celda> adyacentes = new LinkedList<>();
            adyacentes.add(new Celda(c, c.x - 1, c.y - 1, 0, c.g + 14, 0));
            adyacentes.add(new Celda(c, c.x, c.y - 1, 0, c.g + 10, 0));
            adyacentes.add(new Celda(c, c.x + 1, c.y - 1, 0, c.g + 14, 0));
            adyacentes.add(new Celda(c, c.x - 1, c.y, 0, c.g + 10, 0));
            adyacentes.add(new Celda(c, c.x + 1, c.y, 0, c.g + 10, 0));
            adyacentes.add(new Celda(c, c.x - 1, c.y + 1, 0, c.g + 14, 0));
            adyacentes.add(new Celda(c, c.x, c.y + 1, 0, c.g + 10, 0));
            adyacentes.add(new Celda(c, c.x + 1, c.y + 1, 0, c.g + 14, 0));

            //Verificar adyacientes
            for (Celda a : adyacentes) {
                //Si es una celda validad
                if (celdaValida(a)) {

                    //Si la celda es el destino
                    if (c.x == x2 && c.y == y2) {
                        pintarRuta(c);
                        return true;
                    }

                    //Si no esta en la lista cerrada
                    if (!ltsClose.contieneElemento(a)) {
                        //Si esta en la lista abierta
                        if (ltsOpen.contieneElemento(a)) {
                            //Verificamos si la g es mejor para ser remplazada
                            Celda c2 = ltsOpen.get(a.x + "," + a.y);
                            if (c2 != null && a.g < c2.g) {
                                //Remover y recalcular
                                ltsOpen.getRemove(a.x + "," + a.y);
                                a.h = calcularH(a.x, a.y);
                                a.f = a.g + a.h;
                                ltsOpen.add(a);
                            }
                        } else {
                            //Agregar nueva celda adyaciente
                            a.h = calcularH(a.x, a.y);
                            a.f = a.g + a.h;
                            ltsOpen.add(a);
                        }
                    }
                }
            }
        }
        
        return false;
    }

    public void solucionar() throws Exception {
        //Crear matriz de obstaculos
        crearMatriz();

        //Verficar que puntos no sean obstaculos
        if (!puntosOk()) {
            throw new Exception("Puntos mal colocados");
        }

        //Creando listas
        ltsOpen = new ListaBusqueda();
        ltsClose = new ListaBusqueda();

        //Aplicar algoritmo estrella
        if(aEstrella())
            System.out.println("Solucion encontrada");
        else
            System.out.println("Nada :(");

        System.out.println("Terminado xD");
    }

}
