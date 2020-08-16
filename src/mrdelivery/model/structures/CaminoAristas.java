package mrdelivery.model.structures;

import mrdelivery.model.Const;

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
        // TODO: Chequear si lo de sumaParcial esta bien
        camino = new ArrayList<Arista>();
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
//            if(currentIndex == 0) {
//                anterior = null;
//                System.out.println("Anterior = null ");
//            }
//            else {
//                anterior = camino.get(currentIndex - 1);
//                System.out.println("Anterior = " + anterior.toStringConexion());
//            }
//            if(currentIndex == (camino.size()-1)) {
//                siguiente = null;
//                System.out.println("Siguiente = null ");
//            }
//            else {
//                siguiente = camino.get(currentIndex + 1);
//                System.out.println("Siguiente = " + siguiente.toStringConexion());
//            }
//            Paso paso = new Paso(actual,siguiente,anterior);
            this.recorridoPrevio.push(paso);
            this.ultimoSacadoCamino = paso;
            //currentIndex++; // Se acomoda para la siguiente iteracion
//            if(currentIndex < (camino.size()-1);
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
