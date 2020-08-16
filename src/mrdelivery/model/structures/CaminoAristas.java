package mrdelivery.model.structures;

import mrdelivery.model.Const;

import java.text.DecimalFormat;
import java.util.*;

public class CaminoAristas implements Comparable<CaminoAristas>{

    public LinkedList<Arista> camino;
    double distanciaTotal;
    public int currentIndex;
    Arista ultimoSacadoRecorridoPrevio;
    Arista ultimoSacadoCamino;
    boolean caminoInterrumpido;
    boolean retrocediendo;
    public Stack<Arista> recorridoPrevio;

    public CaminoAristas(){
        camino = new LinkedList<>();
        currentIndex = 0;
        distanciaTotal = 0.0;
        retrocediendo = false;
        caminoInterrumpido = false;
        recorridoPrevio = new Stack<>();
        ultimoSacadoCamino = null;
        ultimoSacadoRecorridoPrevio = null;
    }

    public CaminoAristas(double _distanciaTotal){
        camino = new LinkedList<>();
        currentIndex = 0;
        distanciaTotal = _distanciaTotal;
        retrocediendo = false;
        caminoInterrumpido = false;
        recorridoPrevio = new Stack<>();
        ultimoSacadoCamino = null;
        ultimoSacadoRecorridoPrevio = null;
    }

    public CaminoAristas(CaminoAristas copiar){
        camino = new LinkedList<>();
        int sumaParcial = 0;
        for (Arista arista : copiar.camino){
            sumaParcial += arista.getPonderacionActual();
        }
        distanciaTotal = sumaParcial;
        camino.addAll(copiar.camino);
        currentIndex = 0;
        retrocediendo = false;
        caminoInterrumpido = false;
        recorridoPrevio = new Stack<>();
        ultimoSacadoCamino = null;
        ultimoSacadoRecorridoPrevio = null;
    }

    public void addCamino(Arista arista){
        distanciaTotal += arista.getPonderacionActual();
        camino.addLast(arista);
    }

    public void join(CaminoAristas aristas){
        this.camino.addAll(aristas.camino);
    }

    public void verticeActual(){
        camino.get(currentIndex).resaltarEnPantalla();
    }

    public boolean isRetrocediendo() {
        return retrocediendo;
    }

    public void setRetrocediendo(boolean retrocediendo) {
        this.retrocediendo = retrocediendo;
    }

    public boolean isCaminoInterrumpido() {
        return caminoInterrumpido;
    }

    public void setCaminoInterrumpido(boolean caminoInterrumpido) {
        this.caminoInterrumpido = caminoInterrumpido;
    }

    public Arista avanzarCamino(){
        if (!camino.isEmpty()){
            Arista paso = camino.removeFirst();
            System.out.println("Paso actual = " + paso.toStringConexion());
            this.recorridoPrevio.push(paso);
            this.ultimoSacadoCamino = paso;
            return paso;
        }
        return null;
    }

    public Arista getUltimoSacadoRecorridoPrevio() {
        return ultimoSacadoRecorridoPrevio;
    }

    public Arista getUltimoSacadoCamino() {
        return ultimoSacadoCamino;
    }

    public Arista retrocederCamino(){
        ultimoSacadoRecorridoPrevio = recorridoPrevio.pop();
        return ultimoSacadoRecorridoPrevio;
    }

    public Arista fijarmeParaAtras(Vertice origenDelSiguiente){
        int largo = recorridoPrevio.size();
        System.out.println("Largo de recorrido previo " + largo);
        for (int i = largo-1; i > -1; i--){
            if (recorridoPrevio.get(i).getDestino().equals(origenDelSiguiente)) {
                this.ultimoSacadoRecorridoPrevio = recorridoPrevio.get(i);
                return recorridoPrevio.get(i);
            }
        }
        return null;
    }

    public boolean compararCamino(CaminoAristas caminoAristas){
        if(camino.size() == caminoAristas.camino.size()) {
            for (int i = 0;i < camino.size();i++) {
                if(camino.get(i).destino != caminoAristas.camino.get(i).destino || camino.get(i).origen != caminoAristas.camino.get(i).origen)
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean caminoValido(Vertice start,Vertice end){
        //TODO:Pendiente caminoValidoAristas
        return false;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void sumarEnDistanciaTotal(double cantidad){
        this.distanciaTotal += cantidad;
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
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        switch (arista.ponderacionActual){
            case (Const.PRECIO):
                return "$" + df.format(total);
            case(Const.DISTANCIA):
                return df.format(total)+" km";
            case(Const.TIEMPO):
                return df.format(total)+" min";
            default:
                return "";
        }
    }
    public String toStringResumen(){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
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
