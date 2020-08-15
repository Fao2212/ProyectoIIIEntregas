package mrdelivery.view.componentes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.css.PseudoClass;
import javafx.css.Style;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.util.Duration;

public abstract class Boton extends ToggleButton {
    public static final PseudoClass seleccionado = PseudoClass.getPseudoClass("selected");
    public static final PseudoClass resaltado = PseudoClass.getPseudoClass("resaltado");
    public static final PseudoClass activo = PseudoClass.getPseudoClass("activo");
    public Boton (int alto, int ancho){
        Tooltip tooltip = new Tooltip();
        tooltip.setWrapText(true);
        this.setTooltip(tooltip);
        this.setPrefSize(alto,ancho);
        this.setAlignment(Pos.CENTER);
        this.setFont(Font.font("Courier",10));
    }

    public void resaltar(){
        this.pseudoClassStateChanged(resaltado,true);
    }

    public void quitarResaltado(){
        this.pseudoClassStateChanged(resaltado,false);
    }

}
