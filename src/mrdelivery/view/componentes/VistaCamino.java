package mrdelivery.view.componentes;

import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import mrdelivery.model.structures.CaminoAristas;

public class VistaCamino extends ListCell<CaminoAristas> {
        public VistaCamino() {}

        @Override protected void updateItem(CaminoAristas camino, boolean empty) {
            super.updateItem(camino, empty);
            setText(camino == null ? "" : camino.toStringResumen());
            if (camino != null)
                setTooltip(new Tooltip(camino.toString()));
        }
}
