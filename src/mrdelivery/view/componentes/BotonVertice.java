package mrdelivery.view.componentes;
import javafx.beans.property.BooleanProperty;
import mrdelivery.model.structures.Vertice;

public class BotonVertice extends Boton {
    Vertice vertice;
    public BotonVertice(int alto, int ancho, Vertice _vertice) {
        super(alto, ancho);
        vertice = _vertice;
        this.getStyleClass().add("vertice");
        if(vertice != null && !vertice.isActivo())
            this.pseudoClassStateChanged(Boton.seleccionado,true);
        this.setOnMouseClicked((evento)->{
            if (vertice != null) {
                vertice.setActivo(!vertice.isActivo());
                this.pseudoClassStateChanged(Boton.seleccionado, !vertice.isActivo());
            }
        });
    }

    public Vertice getVertice(){
        return vertice;
    }
}
