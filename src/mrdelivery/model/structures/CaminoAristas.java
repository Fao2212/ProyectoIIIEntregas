package mrdelivery.model.structures;

import mrdelivery.model.Const;

import java.util.ArrayList;
import java.util.Stack;

public class CaminoAristas implements Comparable<CaminoAristas>{

    public ArrayList<Arista> camino;
    double distanciaTotal;
    public int indexOfNextStep;
    Paso pasoActual;
    boolean llegoAlFinal;
    boolean llegoAlInicio;
    boolean retrocediendo;
    public Stack<Paso> recorridoPrevio;
    public Stack<Paso> recorridoSiguiente;

    public CaminoAristas(){
        camino = new ArrayList<>();
        indexOfNextStep = 0;
        distanciaTotal = 0.0;
        pasoActual = null;
        llegoAlFinal = false;
        llegoAlInicio = false;
        retrocediendo = false;
        recorridoPrevio = new Stack<>();
        recorridoSiguiente = new Stack<>();
    }

    public CaminoAristas(double _distanciaTotal){
        camino = new ArrayList<Arista>();
        indexOfNextStep = 0;
        distanciaTotal = _distanciaTotal;
        pasoActual = null;
        llegoAlFinal = false;
        llegoAlInicio = false;
        retrocediendo = false;
        recorridoPrevio = new Stack<>();
        recorridoSiguiente = new Stack<>();
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
        indexOfNextStep = 0;
        pasoActual = null;
        llegoAlFinal = false;
        llegoAlInicio = false;
        retrocediendo = false;
        recorridoPrevio = new Stack<>();
        recorridoSiguiente = new Stack<>();
    }

    public void addCamino(Arista arista){
        camino.add(arista);
    }

    public void join(CaminoAristas aristas){
        this.camino.addAll(aristas.camino);
    }

    public void recorridoCompleto(){
        // TODO: Ver si se elimina por no ocuparse
        for (Arista arista : camino){
            arista.resaltarEnPantalla();
        }
    }

    public void verticeActual(){
        camino.get(indexOfNextStep).resaltarEnPantalla();
    }

    public Paso getPasoActual() {
        return pasoActual;
    }

    public void setPasoActual(Paso pasoActual) {
        this.pasoActual = pasoActual;
    }

    public void setLlegoAlFinal(boolean llegoAlFinal) {
        this.llegoAlFinal = llegoAlFinal;
    }

    public boolean isLlegoAlInicio() {
        return llegoAlInicio;
    }

    public void setLlegoAlInicio(boolean llegoAlInicio) {
        this.llegoAlInicio = llegoAlInicio;
    }

    public boolean isRetrocediendo() {
        return retrocediendo;
    }

    public void setRetrocediendo(boolean retrocediendo) {
        this.retrocediendo = retrocediendo;
    }

    public Paso avanzarCamino(){
        if (!llegoAlFinal){
            Arista anterior;
            Arista siguiente;
            Arista actual = camino.get(indexOfNextStep);
            if(indexOfNextStep == 0)
                anterior = null;
            else
                anterior = camino.get(indexOfNextStep -1);
            if(indexOfNextStep == (camino.size()-1))
                siguiente = null;
            else
                siguiente = camino.get(indexOfNextStep +1);
            Paso paso = new Paso(actual,siguiente,anterior);
            recorridoPrevio.push(paso);
            this.pasoActual = paso;
            if(indexOfNextStep < (camino.size()-1))
                indexOfNextStep++;
            return paso;
        }
        return null;
    }

    public Paso retrocederCamino(){
        return recorridoPrevio.pop();
//        if (!llegoAlInicio){
//            if(indexOfNext > -1) {   // El minimo es el cero
//                Arista anterior;
//                Arista siguiente;
//                Arista actual = camino.get(indexOfNext);
//                if (indexOfNext == 0)
//                    anterior = null;
//                else
//                    anterior = camino.get(indexOfNext - 1);
//                if (indexOfNext == (camino.size() - 1))
//                    siguiente = null;
//                else
//                    siguiente = camino.get(indexOfNext + 1);
//                Paso paso = new Paso(actual, siguiente, anterior);
//                recorridoPrevio.pop();
//                this.pasoActual = paso;
//                return paso;
//            }
//        }
//        return null;
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
}
