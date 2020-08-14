package mrdelivery.view.componentes;

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
        this.setOnMouseClicked((evento)->{
            if (arista != null) {
                arista.setActivo(!arista.isActivo());
                System.out.println("Se cambio el estado de la arista");;
            }
        });
    }

    public Arista getArista(){
        return arista;
    }
}
