package mrdelivery.model.structures;

import mrdelivery.model.Const;

public class Arista implements  Comparable<Arista>{

    Vertice origen;
    Vertice destino;
    double distancia;
    double tiempo;
    double precio;
    boolean activo;
    Peso peso;

    public Arista(Vertice origen,Vertice destino,boolean activo,double distancia,double tiempo,double precio){
        this.origen = origen;
        this.destino =destino;
        this.distancia = distancia;
        this.precio = precio;
        this.tiempo = tiempo;
        this.activo = activo;
    }

    public Arista(Vertice origen,Vertice destino,String activo,String distancia,String tiempo,String precio){
        this.origen = origen;
        this.destino =destino;
        this.distancia = Double.parseDouble(distancia);
        this.precio = Double.parseDouble(precio);
        this.tiempo = Double.parseDouble(tiempo);
        this.activo = Boolean.parseBoolean(activo);
    }

    public Arista(Vertice origen,Vertice destino,Arista arista){
        this.origen = origen;
        this.destino = destino;
        this.distancia = arista.distancia;
        this.precio = arista.precio;
        this.tiempo = arista.tiempo;
        this.activo = arista.activo;
    }

    public void setPeso(Peso peso) {
        this.peso = peso;
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

    public void resaltarEnPantalla(){
        //TODO:Usa la referencia a pantalla para resaltarla de un color mostrando asi el camino
    }


    @Override
    public int compareTo(Arista o) {
        double comp1 = 0;
        double comp2 = 0;
        if(peso == Peso.DISTANCIA){
            comp1 = distancia;
            comp2 = o.getDistancia();
        }
        else if(peso == Peso.PRECIO){
            comp1 = precio;
            comp2 = o.getPrecio();
        }
        else if(peso == Peso.TIEMPO){
            comp1 = tiempo;
            comp2 = o.getTiempo();
        }
        if(comp1 == comp2)
            return 0;
        else if(comp1 > comp2)
            return 1;
        else
            return -1;
    }
}
