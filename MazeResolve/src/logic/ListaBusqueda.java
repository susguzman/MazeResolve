package logic;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Jesus
 */
public class ListaBusqueda {

    private ListaAscendente lista;
    private HashMap<String, Celda> hash;

    public ListaBusqueda() {
        lista = new ListaAscendente();
        hash = new HashMap<>();
    }

    public boolean add(Celda nuevo) {
        //Verificar que no existe la celda
        String llave = nuevo.x + "," + nuevo.y;
        if(hash.containsKey(llave)){
            return false;
        }
        
        lista.addOrdenado(nuevo);
        hash.put(llave, nuevo);
        
        return true;
    }
    
    public Celda getMenor(){
        if(!lista.isEmpty()){
            Celda c = lista.getFirst();
            
            lista.removeFirst();
            hash.remove(c.x + "," + c.y);
            
            return c;
        }
        
        return null;
    }
    
    public Celda getRemove(String llave){
        if(!lista.isEmpty()){
            Celda c = hash.get(llave);
            
            //System.out.println("Lista AN: " + lista.size());
            lista.remove(c);
            hash.remove(llave);
            //System.out.println("Lista DE: " + lista.size());
            
            return c;
        }
        
        return null;
    }
    
    public Celda get(String llave){
        if(!lista.isEmpty()){
            return hash.get(llave);
        }
        
        return null;
    }
    
    public boolean contieneElemento(Celda c){
        return hash.containsKey(c.x + "," + c.y);
    }
    
    public boolean esVacia(){
        return lista.isEmpty();
    }

    private class ListaAscendente extends LinkedList<Celda> {

        public void addOrdenado(Celda nuevo) {
            boolean agregado = false;
            for (int i = 0; i < this.size() && agregado == false; i++) {
                Celda c = this.get(i);

                if (c.f >= nuevo.f) {
                    this.add(i, nuevo);
                    agregado = true;
                }
            }

            if (agregado == false) {
                this.addLast(nuevo);
            }
        }

    }
}
