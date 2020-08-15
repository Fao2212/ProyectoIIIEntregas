package mrdelivery.model.structures;

import mrdelivery.model.Const;

import java.util.ArrayList;

public class CaminoAristas implements Comparable<CaminoAristas>{

    public ArrayList<Arista> camino;
    double distanciaTotal;
    int currentIndex;

    public CaminoAristas(){
        camino = new ArrayList<>();
        currentIndex = 0;
        distanciaTotal = 0.0;
    }

    public CaminoAristas(double _distanciaTotal){
        camino = new ArrayList<Arista>();
        currentIndex = 0;
        distanciaTotal = _distanciaTotal;
    }

    public CaminoAristas(CaminoAristas copiar){
        // TODO: Chequear si lo de sumaParcial esta bien
        camino = new ArrayList<Arista>();
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

    public void join(CaminoAristas aristas){
        this.camino.addAll(aristas.camino);
    }

    public void recorridoCompleto(){
        for (Arista arista : camino){
            arista.resaltarEnPantalla();
        }
    }

    public void verticeActual(){
        camino.get(currentIndex).resaltarEnPantalla();
    }

    public Paso avanzarCamino(){
        Arista anterior;
        Arista siguiente;
        Arista actual = camino.get(currentIndex);
        if(currentIndex == 0)
            anterior = null;
        else
            anterior = camino.get(currentIndex-1);
        if(currentIndex == camino.size())
            siguiente = null;
        else
            siguiente = camino.get(currentIndex+1);
        Paso paso = new Paso(actual,siguiente,anterior);
        if(currentIndex < camino.size())
            currentIndex++;
        return paso;
    }

    public void retrocederCamino(){
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

    private String getPonderacionConSimbolo(Arista arista, double total){
        switch (arista.ponderacionActual){
            case (Const.PRECIO):
                return "$" + total;
            case(Const.DISTANCIA):
                return total+" km";
            case(Const.TIEMPO):
                return total+" min";
            default:
                return "";
        }
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
        if (camino.size() > 0){
            s.append(" Total: " +getPonderacionConSimbolo(camino.get(0),distanciaTotal));
        }

        return s.toString();
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

    public void setPonderacion(int ponderacion){
        for (Arista arista:camino){
            arista.setPonderacionActual(ponderacion);
        }
    }
}
