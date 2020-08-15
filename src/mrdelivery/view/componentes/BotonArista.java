package mrdelivery.view.componentes;

import javafx.beans.property.BooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import mrdelivery.model.structures.Arista;

public class BotonArista extends Boton{
    Arista arista;
    public BotonArista(int alto, int ancho, Arista _arista) {
        super(alto, ancho);
        arista = _arista;
        this.setWrapText(true);
        this.setFont(Font.font("Courier", FontWeight.NORMAL, FontPosture.REGULAR,10));
        if (arista != null)
            this.getStyleClass().add("arista");
        if(arista != null && !arista.isActivo())
            this.pseudoClassStateChanged(Boton.seleccionado,true);
        this.setOnMouseClicked((evento)->{
            if (arista != null) {
                arista.setActivo(!arista.isActivo());
                this.pseudoClassStateChanged(Boton.seleccionado, !arista.isActivo());
                System.out.println("Se cambio el estado de la arista, ahora esta: "+arista.isActivo());
            }
        });
    }

    public Arista getArista(){
        return arista;
    }



}
