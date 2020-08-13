package mrdelivery.model.structures;

import java.util.ArrayList;

public class CaminoVertices {

    ArrayList<Vertice> camino;
    int currentIndex;

    CaminoVertices(){
        camino = new ArrayList<>();
        currentIndex = 0;
    }

    CaminoVertices(CaminoVertices copiar){
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

    public boolean compararCamino(CaminoVertices caminoVertices){
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

