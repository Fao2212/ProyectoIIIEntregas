package mrdelivery.view.componentes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import mrdelivery.model.structures.Arista;

public abstract class Boton extends Button{
    public Boton (int alto, int ancho){
        Tooltip tooltip = new Tooltip();
        tooltip.setWrapText(true);
        this.setTooltip(tooltip);
        this.setPrefSize(alto,ancho);
        this.setAlignment(Pos.CENTER);
        this.setFont(Font.font("Courier",10));
    }

}
