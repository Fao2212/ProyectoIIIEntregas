package mrdelivery.model.structures;

import java.util.ArrayList;

public class Vertice {

    String nombre;
    ArrayList<Arista> aristas;
    boolean visitado;

    public Vertice(String nombre){
        this.nombre = nombre;
        this.aristas = new ArrayList<>();
        this.visitado = false;
    }

    public Vertice(Vertice vertice){//Paso el vertice completo por si luego se le agregan mas atributos que asignar
        this.nombre = vertice.nombre;
        this.aristas = new ArrayList<>();
        this.visitado = false;
    }

    public void addArista(Arista arista){
        this.aristas.add(arista);
    }

    public boolean buscarCamino(Vertice vertice2) {
        return false;
    }

    public void resaltarEnPantalla() {
        //Usa la referencia a pantalla para resaltarla de un color mostrando asi el camino

    public void setVisitado(boolean value){
        this.visitado = value;
    }

    public Object getNombre() {
        return nombre;
    }
}
