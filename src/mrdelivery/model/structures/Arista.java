package mrdelivery.model.structures;

public class Arista {

    Vertice origen;
    Vertice destino;
    double distancia;
    double tiempo;
    double precio;
    boolean activo;

    public Arista(Vertice origen,Vertice destino,boolean activo,double distancia,double tiempo,double precio){

    }

    public Arista(Vertice origen,Vertice destino,String activo,String distancia,String tiempo,String precio){

    }

    public Arista(Vertice origen,Vertice destino,Arista arista){
        this.origen = origen;
        this.destino =destino;
        this.distancia = arista.distancia;
        this.precio = arista.precio;
        this.tiempo = arista.tiempo;
        this.activo = arista.activo;
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
}
