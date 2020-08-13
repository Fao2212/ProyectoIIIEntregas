package mrdelivery.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mrdelivery.model.App;
import mrdelivery.model.Const;
import mrdelivery.view.componentes.Boton;
import mrdelivery.model.structures.Arista;
import mrdelivery.model.structures.Grafo;
import mrdelivery.view.Out;
import org.json.JSONObject;

public class Controller implements ViewController, EventHandler {

    // Constantes
    boolean ponderacionActiva[] = {false,false,false};

    // Objetos generados por fxml
    @FXML private VBox vbxGrafoOriginal, vbxGrafoActual;

    @FXML private Button btnAvanzarOriginal, btnAvanzarActual, btnSiguienteGrafo,
                         btnActivarVertice, btnDesactivarVertice, btnObtenerCaminoMinimo,
                         btnTodosLosCaminos, btnCaminoOptimo, btnCalcularRecorrido,
                         btnCalcularAEM;

    @FXML private TextField tfdVerticeAlterado, tfdDesdeCaminosMinimos, tfdOrigenTodosLosCaminos,
                      tfdDestinoTodosLosCaminos, tfdRecorridosDesde;


    @FXML private RadioButton rdbPrecio, rdbDistancia, rdbTiempo;

    @FXML private ToggleGroup CostoDesdeOpciones;

    @FXML private TextArea tarCaminosMinimo, tarRecorridoProfundidad, tarRecorridoAnchura,
                     tarArbolExpansionMinima;

    @FXML private ListView<?> lstCaminos;
    @FXML
    private ListView<?> lstCaminoAvanzado;

    private GridPane grdGrafoOriginal, grdGrafoActual;
    private HBox hbxVerticesOriginal, hbxVerticesActual;
    private App app;

    private GridPane crearCuadricula(){
        GridPane cuadricula = new GridPane();
        cuadricula.setAlignment(Pos.CENTER);
        return cuadricula;
    }

    private HBox crearFila(){
        HBox fila = new HBox();
        fila.setAlignment(Pos.CENTER);
        fila.setMargin(vbxGrafoActual,new Insets(5,5,5,10));
        return fila;
    }

    private int getPonderacionActiva(){
        for (int i = 0; i < ponderacionActiva.length; i++){
            if(ponderacionActiva[i])
                return i;
        }
        return -1;  // Ninguno esta activo
    }

    @Override
    public void prepararVentana(){
        grdGrafoOriginal = crearCuadricula();
        grdGrafoActual = crearCuadricula();
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button btnAristaOriginal = new Boton(40,40).getButton();
                Button btnAristaActual = new Boton(40,40).getButton();
                grdGrafoOriginal.add(btnAristaOriginal,fila,columna);
                grdGrafoActual.add(btnAristaActual,fila,columna);
            }
        }
        vbxGrafoOriginal.getChildren().add(1,grdGrafoOriginal); // Index 1, porque es el segundo elemento en el vbox
        vbxGrafoActual.getChildren().add(1,grdGrafoActual);
    }

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    public void cargarGrafos(Grafo grafoOriginal,Grafo grafoActual){
        cargarGrafoOriginal(grafoOriginal);
        cargarGrafoActual(grafoActual);
    }

    public void cargarGrafoOriginal(Grafo grafo){
        reloadGrid(grafo,grdGrafoOriginal,vbxGrafoOriginal);
    }

    public void cargarGrafoActual(Grafo grafo){
        reloadGrid(grafo,grdGrafoActual,vbxGrafoActual);
    }

    private void reloadGrid(Grafo grafo,GridPane gridPane,VBox vbox){
        gridPane = crearCuadricula();
        Arista [][] matriz = grafo.getMatriz();
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz.length; columna++) {
                Button btn= crearBoton(40,40, fila, columna);
                btn.getTooltip().setText(showVerticeToolTip(grafo.getVertices().get(fila),grafo.getVertices().get(columna)));
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

    // ACCIONES DESDE LA INTERFAZ
    @FXML
    void activarVertice(ActionEvent event) {

    }

    @FXML
    void actualizarPesaje(ActionEvent event) {
        if (rdbPrecio.isSelected()){
            ponderacionActiva[Const.PRECIO] = true;
            System.out.println("Se activo el prceio");
        }
        else if (rdbDistancia.isSelected()){
            ponderacionActiva[Const.DISTANCIA] = true;
            System.out.println("Se activo la distancia");
        }
        else if (rdbTiempo.isSelected()){
            ponderacionActiva[Const.TIEMPO] = true;
            System.out.println("Se activo el tiempo");
        }
    }

    @FXML
    void avanzarGrafoActual(ActionEvent event) {

    }

    @FXML
    void avanzarGrafoOriginal(ActionEvent event) {

    }

    @FXML
    void calcularRecorrido(ActionEvent event) {

    }

    @FXML
    void desactivarVertice(ActionEvent event) {

    }

    @FXML
    void obtenerCaminoOptimo(ActionEvent event) {

    }

    @FXML
    void obtenerCaminosMinimos(ActionEvent event) {

    }

    @FXML
    void obtenerTodosLosCaminos(ActionEvent event) {

    }

    @FXML
    void siguienteGrafo(ActionEvent event) {
        JSONObject json = app.getNextJson();
        if (json != null){
            app.crearGrafo(json);
            if (app.getActualModificado() != null){
                cargarGrafos(app.getActualOriginal(), app.getActualModificado());
            }
            else
                System.out.println("El grafo actual esta nulo");
        }
        else{
            Out.msg("Error en carga de rutas","Por favor verifique que la carpeta de rutas"+
                    " no esta vacia", Alert.AlertType.ERROR);
        }
    }

    public String showVerticeToolTip(Vertice v1, Vertice v2){
        return "Fila: " + v1.getNombre() + " Columna: " + v2.getNombre();
    }

}
