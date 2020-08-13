package mrdelivery.view.componentes;

import mrdelivery.model.structures.Arista;

public class BotonArista extends Boton{
    Arista arista;
    public BotonArista(int alto, int ancho, Arista _arista) {
        super(alto, ancho);
        arista = _arista;
    }

    public Arista getArista(){
        return arista;
    }
}
