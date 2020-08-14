package mrdelivery.model.structures;

import java.util.ArrayList;

public class CaminoAristas {

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
        int sumaParcial = 0;
        for (Arista arista : copiar.camino){
            sumaParcial += arista.getPonderacionActual();
        }
        distanciaTotal = sumaParcial;
        camino.addAll(copiar.camino);
        currentIndex = 0;
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

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < camino.size(); i++){
            Arista arista = camino.get(i);
            s.append("("+arista.getOrigen().getNombre()+")-[ $"+arista.getPrecio()+", "+
                    arista.getDistancia()+" km, "+arista.getTiempo()+" min ]-("+
                    arista.getDestino().getNombre()+") ");
            if (i == camino.size()-1){
                break;
            }
            s.append(" - ");
        }
        return s.toString();
    }

    public String toStringResumen(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < camino.size(); i++){
            Arista arista = camino.get(i);
            if (i == 0)
                s.append("("+arista.getOrigen().getNombre()+")-("+arista.getDestino().getNombre()+")");
            else
                s.append("-("+arista.getDestino().getNombre()+")");
        }
        if (camino.get(0) != null)
            s.append("Total: " +distanciaTotal + camino.get(0).getPonderacionActualString());
        return s.toString();
    }

}
