package mrdelivery.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import mrdelivery.model.structures.Grafo;

public class Controller implements ViewController, EventHandler {

    // Constantes
    public static final int ALTO = 10;
    public static final int ANCHO = 10;

    // Objetos generados por fxml
    @FXML private VBox vbxGrafoOriginal,  vbxGrafoActual;
    @FXML private Button btnAvanzarOriginal, btnAvanzarActual, btnSiguienteGrafo;
    private GridPane grdGrafoOriginal, grdGrafoActual;

    private Button crearBoton(int alto, int ancho, int y, int x){
        Button boton = new Button();
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Fila: " + x + " Columna: " + y);
        tooltip.setWrapText(true);
        boton.setTooltip(tooltip);
        boton.setPrefSize(alto,ancho);
        boton.setAlignment(Pos.CENTER);
        return boton;
    }


    private GridPane crearCuadricula(){
        GridPane cuadricula = new GridPane();
        cuadricula.setAlignment(Pos.CENTER);
        return cuadricula;
    }

    @Override
    public void prepararVentana(){
        grdGrafoOriginal = crearCuadricula();
        grdGrafoActual = crearCuadricula();
        for (int fila = 0; fila < ALTO; fila++) {
            for (int columna = 0; columna < ANCHO; columna++) {
                Button btnOriginal = crearBoton(40,40, fila, columna);
                Button btnActual = crearBoton(40,40, fila, columna);
                grdGrafoOriginal.add(btnOriginal,fila,columna);
                grdGrafoActual.add(btnActual,fila,columna);
            }
        }
        vbxGrafoOriginal.getChildren().add(1,grdGrafoOriginal); // Index 1, porque es el segundo elemento en el vbox
        vbxGrafoActual.getChildren().add(1,grdGrafoActual);
        btnSiguienteGrafo.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
    }

    public void cargarGrafo(Grafo grafo){

    }

    public void cargarGrafoOriginal(Grafo grafo){

    }

    public void cargarGrafoActual(Grafo grafo){

    }

    @Override
    public void handle(Event event) {
        System.out.println("HOLA");
    }
}
