package mrdelivery.model.structures;

import java.util.ArrayList;

public class Camino {

    ArrayList<Vertice> camino;
    int currentIndex;

    Camino(){
        camino = new ArrayList<>();
        currentIndex = 0;
    }

    public void addCamino(Vertice vertice){
        camino.add(vertice);
    }

    public void recorridoCompleto(){
        for (Vertice vertice:camino){
            vertice.resaltarEnPantalla();
        }
    }

    public void verticeActual(){
        camino.get(currentIndex).resaltarEnPantalla();
    }

    public void avanzarCamino(){
        if(currentIndex < camino.size())
            currentIndex++;
    }


    public  void retrocederCamino(){
        if(currentIndex > 0)
            currentIndex--;
    }


}
