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
            alternar();
        });
    }

    public void alternar(){
        if (vertice != null) {
            vertice.modificarActividad(!vertice.isActivo());
            this.pseudoClassStateChanged(Boton.seleccionado, !vertice.isActivo());
            System.out.println("Se cambio el estado del vertice, ahora esta: "+vertice.isActivo());
        }
    }

    public Vertice getVertice(){
        return vertice;
    }
}
