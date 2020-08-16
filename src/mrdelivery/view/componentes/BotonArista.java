package mrdelivery.view.componentes;

import javafx.beans.property.BooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import mrdelivery.model.structures.Arista;
import mrdelivery.view.Out;

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
            alternar();
        });
    }

    public void alternar(){
        if (arista != null) {
            if (arista.getOrigen().isActivo() && arista.getDestino().isActivo()){
                arista.setActivo(!arista.isActivo());
                this.pseudoClassStateChanged(Boton.seleccionado, !arista.isActivo());
                System.out.println("Se cambio el estado de la arista, ahora esta: "+arista.isActivo());
            }
            else
                Out.msg("Algo anda mal ...","El vertice esta inactivo, no puede activar la arista");
        }
    }

    public void desactivar(){
        arista.setActivo(false);
        this.pseudoClassStateChanged(Boton.seleccionado, true);
        System.out.println("Se desactivo el boton");
    }

    public void activar(){
        arista.setActivo(true);
        this.pseudoClassStateChanged(Boton.seleccionado, false);
        System.out.println("Se activo el boton");
    }

    public Arista getArista(){
        return arista;
    }



}
