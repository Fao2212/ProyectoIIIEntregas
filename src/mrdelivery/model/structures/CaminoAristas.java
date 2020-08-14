package mrdelivery.model.structures;

import java.util.ArrayList;

public class CaminoAristas implements Comparable<CaminoAristas>{

    public ArrayList<Arista> camino;
    double distanciaTotal;
    int currentIndex;

    CaminoAristas(){
        camino = new ArrayList<>();
        currentIndex = 0;
        distanciaTotal = 0.0;
    }

    CaminoAristas(double _distanciaTotal){
        camino = new ArrayList<>();
        currentIndex = 0;
        distanciaTotal = _distanciaTotal;
    }

    CaminoAristas(CaminoAristas copiar){
        camino = new ArrayList<>();
        camino.addAll(copiar.camino);
        currentIndex = 0;
        distanciaTotal = 0.0;
    }

    public void addCamino(Arista arista){
        camino.add(arista);
    }

    public void recorridoCompleto(){
        for (Arista arista : camino){
            arista.resaltarEnPantalla();
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
        //TODO:Pendiente compararCaminoAristas
        return true;
    }

    public boolean caminoValido(Vertice start,Vertice end){
        //TODO:Pendiente caminoValidoAristas
        return false;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public void imprimirCamino(){
        for (Arista arista : camino){
            System.out.print("("+arista.getOrigen().getNombre()+")-[ $"+arista.getPrecio()+", "+
                             arista.getDistancia()+" km, "+arista.getTiempo()+" min ]-("+
                             arista.getDestino().getNombre()+") ");
        }
    }

    @Override
    public int compareTo(CaminoAristas o) {
        if(distanciaTotal == o.distanciaTotal)
            return 0;
        else if(distanciaTotal > o.distanciaTotal)
            return 1;
        else
            return -1;
    }
}
