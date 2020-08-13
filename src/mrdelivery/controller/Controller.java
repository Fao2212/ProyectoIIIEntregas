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
import mrdelivery.model.App;
import mrdelivery.model.structures.Arista;
import mrdelivery.model.structures.Grafo;
import mrdelivery.model.structures.Vertice;


public class Controller implements ViewController, EventHandler {

    // Constantes
    public static final int ALTO = 10;
    public static final int ANCHO = 10;

    // Objetos generados por fxml
    @FXML private VBox vbxGrafoOriginal,  vbxGrafoActual;
    @FXML private Button btnAvanzarOriginal, btnAvanzarActual, btnSiguienteGrafo;
    private GridPane grdGrafoOriginal, grdGrafoActual;
    private App app;

    private Button crearBoton(int alto, int ancho, int y, int x){
        Button boton = new Button();
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Fila: " + x + " Columna: " + y);
        tooltip.setWrapText(true);
        boton.setTooltip(tooltip);
        boton.setText("0");
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

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    public void cargarGrafos(Grafo grafoOriginal,Grafo grafoActual){
        Arista[][] matrizOriginal = grafoOriginal.getMatriz();
        Arista[][] matrizActual = grafoActual.getMatriz();
        cargarGrafoOriginal(matrizOriginal);
        cargarGrafoActual(matrizActual);
    }

    public void cargarGrafoOriginal(Arista[][] matriz){
        reloadGrid(matriz,grdGrafoOriginal,vbxGrafoOriginal);
    }

    public void cargarGrafoActual(Arista[][] matriz){
        reloadGrid(matriz,grdGrafoActual,vbxGrafoActual);
    }

    private void reloadGrid(Arista[][] matriz,GridPane gridPane,VBox vbox){
        gridPane = crearCuadricula();
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz.length; columna++) {
                Button btn= crearBoton(40,40, fila, columna);
                if(matriz[fila][columna] != null) {
                    btn.getTooltip().setText(matriz[fila][columna].toStringToolTip());
                    btn.setText("1");
                }
                gridPane.add(btn,fila,columna);
            }
        }
        vbox.getChildren().remove(1);
        vbox.getChildren().add(1,gridPane); // Index 1, porque es el segundo elemento en el vbox
    }

    @Override
    public void handle(Event event) {
        app.crearGrafo(app.getNextJson());
        cargarGrafos(app.getActualOriginal(),app.getActualModificado());
    }

}
