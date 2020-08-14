package mrdelivery.view.componentes;

import javafx.css.PseudoClass;
import javafx.css.Style;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

public abstract class Boton extends ToggleButton {
    public static final PseudoClass seleccionado = PseudoClass.getPseudoClass("selected");
    public Boton (int alto, int ancho){
        Tooltip tooltip = new Tooltip();
        tooltip.setWrapText(true);
        this.setTooltip(tooltip);
        this.setPrefSize(alto,ancho);
        this.setAlignment(Pos.CENTER);
        this.setFont(Font.font("Courier",10));
    }

    public void resaltar(){
        String previous = this.getStyle();
        this.setStyle("-fx-background-color:#000000");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setStyle(previous);
        System.out.println("Pinte el boton");
    }


}
