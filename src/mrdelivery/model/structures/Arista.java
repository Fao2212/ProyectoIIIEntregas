package mrdelivery.model.structures;

import mrdelivery.model.Const;

public class Arista {

    Vertice origen;
    Vertice destino;
    double distancia;
    double tiempo;
    double precio;
    boolean activo;
    int ponderacionActual;

    public Arista(Vertice origen,Vertice destino,boolean activo,double precio,double distancia,double tiempo){
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.precio = precio;
        this.tiempo = tiempo;
        this.activo = activo;
        this.ponderacionActual = 0;
    }

    public Arista(Vertice origen,Vertice destino,String activo,String precio,String distancia,String tiempo){
        this.origen = origen;
        this.destino = destino;
        this.distancia = Double.parseDouble(distancia);
        this.precio = Double.parseDouble(precio);
        this.tiempo = Double.parseDouble(tiempo);
        this.activo = Boolean.parseBoolean(activo);
        this.ponderacionActual = 0;
    }

    public Arista(Vertice origen,Vertice destino,Arista arista){
        this.origen = origen;
        this.destino = destino;
        this.distancia = arista.distancia;
        this.precio = arista.precio;
        this.tiempo = arista.tiempo;
        this.activo = arista.activo;
        this.ponderacionActual = 0;
    }
    public Arista(Arista _arista){
        this.origen = _arista.origen;
        this.destino = _arista.destino;;
        this.distancia = _arista.distancia;
        this.precio = _arista.precio;
        this.tiempo = _arista.tiempo;
        this.activo = _arista.activo;
        this.ponderacionActual = _arista.ponderacionActual;
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
        //TODO:Usa la referencia a pantalla para resaltarla de un color mostrando asi el camino
    }
}
