package mrdelivery.view.componentes;

import mrdelivery.model.structures.Vertice;

public class BotonVertice extends Boton {

    Vertice vertice;
    public BotonVertice(int alto, int ancho, Vertice _vertice) {
        super(alto, ancho);
        vertice = _vertice;
    }

    public Vertice getVertice(){
        return vertice;
    }
}
