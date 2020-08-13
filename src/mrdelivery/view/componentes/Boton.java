package mrdelivery.view.componentes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class Boton {
    Button boton;
    public Boton(int alto, int ancho){
        boton = new Button();
        Tooltip tooltip = new Tooltip();
        tooltip.setWrapText(true);
        boton.setTooltip(tooltip);
        boton.setPrefSize(alto,ancho);
        boton.setAlignment(Pos.CENTER);
    }
    public Button getButton(){
        return boton;
    }
}
