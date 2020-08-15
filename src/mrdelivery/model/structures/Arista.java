package mrdelivery.model.structures;

import javafx.beans.property.BooleanProperty;
import mrdelivery.model.Const;
import mrdelivery.view.componentes.Boton;
import mrdelivery.view.componentes.BotonArista;

public class Arista implements  Comparable<Arista>{

    Vertice origen;
    Vertice destino;
    double distancia;
    double tiempo;
    double precio;
    boolean activo;
    int ponderacionActual;
    Peso peso;
    BotonArista boton;
    Grafo grafoAsociado;

    public Arista(Vertice origen,Vertice destino,boolean activo,double precio,double distancia,double tiempo){
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.precio = precio;
        this.tiempo = tiempo;
        this.activo = activo;
        this.ponderacionActual = 0;
        this.grafoAsociado = null;
    }

    public Arista(Vertice origen,Vertice destino,String activo,String precio,String distancia,String tiempo){
        this.origen = origen;
        this.destino = destino;
        this.distancia = Double.parseDouble(distancia);
        this.precio = Double.parseDouble(precio);
        this.tiempo = Double.parseDouble(tiempo);
        this.activo = Boolean.parseBoolean(activo);
        this.ponderacionActual = 0;
        this.grafoAsociado = null;
    }

    public Arista(Vertice origen,Vertice destino,Arista arista){
        this.origen = origen;
        this.destino = destino;
        this.distancia = arista.distancia;
        this.precio = arista.precio;
        this.tiempo = arista.tiempo;
        this.activo = arista.activo;
        this.ponderacionActual = 0;
        this.grafoAsociado = null;
    }
    public Arista(Arista _arista){
        this.origen = _arista.origen;
        this.destino = _arista.destino;;
        this.distancia = _arista.distancia;
        this.precio = _arista.precio;
        this.tiempo = _arista.tiempo;
        this.activo = _arista.activo;
        this.ponderacionActual = _arista.ponderacionActual;
        this.grafoAsociado = null;
    }

    public Grafo getGrafoAsociado() {
        return grafoAsociado;
    }

    public void setGrafoAsociado(Grafo grafoAsociado) {
        this.grafoAsociado = grafoAsociado;
    }

    public Vertice getOrigen() {
        return origen;
    }

    public void setOrigen(Vertice origen) {
        this.origen = origen;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public String toStringConexion() {
        return "Conexion:" + origen.getNombre() + "-" + destino.getNombre();
    }
    public String toStringToolTip() {
        return toStringConexion()+"\n"+toString();
    }
    public String toString(){
        return "Estado: " + toStringActivo() + " Km: "+ distancia + " Tiempo: " + tiempo + " Costo: $" + precio;
    }

    private String toStringActivo() {
        if (activo == true)
            return "Activo";
        return "Inactivo";
    }

    public double getPonderacion(int tipo){
        switch (tipo){
            case Const.PRECIO: return precio;
            case Const.DISTANCIA: return distancia;
            case Const.TIEMPO: return tiempo;
            default: return 0;
        }
    }

    public void setPonderacionActual(int ponderacionActual) {
        this.ponderacionActual = ponderacionActual;
    }

    public double getPonderacionActual(){
        switch (ponderacionActual){
            case Const.PRECIO: return precio;
            case Const.DISTANCIA: return distancia;
            case Const.TIEMPO: return tiempo;
            default: return 0;
        }
    }
    public String getPonderacionString(int tipo){
        switch (tipo){
            case Const.PRECIO: return "$"+precio;
            case Const.DISTANCIA: return distancia+" km";
            case Const.TIEMPO: return tiempo+" min";
            default: return "";
        }
    }

    public void resaltarEnPantalla(){
        boton.resaltar();
    }

    @Override
    public int compareTo(Arista o) {
        if(ponderacionActual == o.ponderacionActual)
            return 0;
        else if(ponderacionActual > o.ponderacionActual)
            return 1;
        else
            return -1;
    }

    public void setBoton(BotonArista btnArista) {
        this.boton = btnArista;
    }

    public void quitarResaltado() {
        boton.quitarResaltado();
    }
}
