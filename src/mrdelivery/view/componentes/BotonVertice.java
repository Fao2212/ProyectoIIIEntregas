package mrdelivery.view.componentes;
import mrdelivery.model.structures.Vertice;

public class BotonVertice extends Boton {

    Vertice vertice;
    public BotonVertice(int alto, int ancho, Vertice _vertice) {
        super(alto, ancho);
        vertice = _vertice;
        this.getStyleClass().add("vertice");
        this.setOnMouseClicked((evento)->{
            if (vertice != null)
                vertice.setActivo(!vertice.isActivo());
        });
    }

    public Vertice getVertice(){
        return vertice;
    }
}
