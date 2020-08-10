package mrdelivery.model.structures;

import java.util.ArrayList;

public class Vertice {

    String nombre;
    ArrayList<Arista> aristas;

    Vertice(String nombre){
        this.nombre = nombre;
        this.aristas = new ArrayList<>();
    }

    Vertice(Vertice vertice){//Paso el vertice completo por si luego se le agregan mas atributos que asignar
        this.nombre = vertice.nombre;
        this.aristas = new ArrayList<>();
    }

    public void addArista(Arista arista){
        this.aristas.add(arista);
    }

    public boolean buscarCamino(Vertice vertice2) {
    }
}
