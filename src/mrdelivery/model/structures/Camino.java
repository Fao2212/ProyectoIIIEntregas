package mrdelivery.model.structures;

import java.util.ArrayList;

public class Camino {

    ArrayList<Vertice> camino;
    int currentIndex;

    Camino(){
        camino = new ArrayList<>();
        currentIndex = 0;
    }

    Camino(Camino copiar){
        camino = new ArrayList<>();
        camino.addAll(copiar.camino);
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

    public boolean compararCamino(Camino camino){
        return true;
    }

    public boolean caminoValido(Vertice start,Vertice end){
        return false;
    }

    public void imprimirCamino(){
        for (Vertice vertice:camino){
            System.out.print(vertice.nombre+"-");
        }

    }

}
