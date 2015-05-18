package logic;

/**
 *
 * @author Jesus
 */
public class Celda {

    public int x, y, f, h, g;
    public Celda padre;
    
    public Celda(Celda padre, int x, int y, int f, int g, int h){
        this.padre = padre;
        this.x = x;
        this.y = y;
        this.f = f;
        this.g = g;
        this.h = h;
    }
    
}
