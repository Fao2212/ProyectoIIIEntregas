package mrdelivery.model.structures;

public class Paso {

    Arista aristaActual;
    Arista aristaSiguiente;
    Arista aristaAnterior;


    public Paso(Arista actual, Arista siguiente, Arista anterior) {
        this.aristaActual = actual;
        this.aristaAnterior = anterior;
        this.aristaSiguiente = siguiente;
    }

    public Arista getAristaActual() {
        return aristaActual;
    }

    public void setAristaActual(Arista aristaActual) {
        this.aristaActual = aristaActual;
    }

    public Arista getAristaSiguiente() {
        return aristaSiguiente;
    }

    public void setAristaSiguiente(Arista aristaSiguiente) {
        this.aristaSiguiente = aristaSiguiente;
    }

    public Arista getAristaAnterior() {
        return aristaAnterior;
    }

    public void setAristaAnterior(Arista aristaAnterior) {
        this.aristaAnterior = aristaAnterior;
    }

    public Vertice getOrigen(){
        return this.aristaActual.origen;
    }

    public Vertice getDestino(){
        return this.aristaActual.destino;
    }

}
