package mrdelivery.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import mrdelivery.model.App;
import mrdelivery.model.Const;
import mrdelivery.model.structures.Vertice;
import mrdelivery.model.structures.Arista;
import mrdelivery.model.structures.Grafo;
import mrdelivery.view.Out;
import mrdelivery.view.componentes.Boton;
import mrdelivery.view.componentes.BotonArista;
import mrdelivery.view.componentes.BotonVertice;
import org.json.JSONObject;

public class Controller implements ViewController {

    // Constantes
//    boolean ponderacionGrafoOriginalActiva[] = {false,false,false};
//    boolean ponderacionGrafoActualActiva[] = {false,false,false};

    // Objetos generados por fxml
    @FXML private VBox vbxGrafoOriginal, vbxGrafoActual, vbxAristasGrafoOriginal,vbxAristasGrafoActual;

    @FXML private HBox hbxVerticesGrafoOriginal,hbxVerticesGrafoActual;

    @FXML private GridPane grdGrafoOriginal, grdGrafoActual;

    @FXML private Button btnAvanzarOriginal, btnAvanzarActual, btnSiguienteGrafo,
                         btnActivarVertice, btnDesactivarVertice, btnObtenerCaminoMinimo,
                         btnTodosLosCaminos, btnCaminoOptimo, btnCalcularRecorrido,
                         btnCalcularAEM;

    @FXML private TextField tfdVerticeAlterado, tfdDesdeCaminosMinimos, tfdOrigenTodosLosCaminos,
                            tfdDestinoTodosLosCaminos, tfdRecorridosDesde;


    @FXML private RadioButton rdbPrecio, rdbDistancia, rdbTiempo, rdbPrecioGrafoOriginal,
                              rdbDistanciaGrafoOriginal, rdbTiempoGrafoOriginal, rdbPrecioGrafoActual,
                              rdbDistanciaGrafoActual, rdbTiempoGrafoActual;

    @FXML private ToggleGroup CostoDesdeOpciones, PonderacionGrafoOriginal, PonderacionGrafoActual;

    @FXML private TextArea tarCaminosMinimo, tarRecorridoProfundidad, tarRecorridoAnchura,
                     tarArbolExpansionMinima;

    @FXML private ListView<?> lstCaminos;
    @FXML
    private ListView<?> lstCaminoAvanzado;

    private App app;

    private GridPane crearCuadricula(){
        GridPane cuadricula = new GridPane();
        cuadricula.setAlignment(Pos.CENTER);
        return cuadricula;
    }

    private HBox crearFila(){
        HBox fila = new HBox();
        fila.setAlignment(Pos.CENTER);
//        HBox.setMargin(vbxGrafoActual,new Insets(10,10,10,10));
        return fila;
    }

    @Override
    public void prepararVentana(){
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
        reloadGrid(grafo,grdGrafoOriginal,vbxGrafoOriginal,hbxVerticesGrafoOriginal);
    }

    public void cargarGrafoActual(Grafo grafo){
        reloadGrid(grafo,grdGrafoActual,vbxGrafoActual,hbxVerticesGrafoActual);
    }

    private void decorarBotonArista(BotonArista btnArista){
        btnArista.getStyleClass().add("arista");
    }

    private void decorarBotonVertice(BotonVertice btnVertice){
        btnVertice.getStyleClass().add("vertice");
    }
    private void reloadGrid(Grafo grafo, GridPane gridPane, VBox vbox, HBox hbox){
        gridPane = crearCuadricula();
        hbox = crearFila();
        Arista [][] matriz = grafo.getMatriz();
        for (int fila = 0; fila < matriz.length; fila++) {
            BotonVertice btnVertice = new BotonVertice(Const.BTN_ALTO,Const.BTN_ANCHO, grafo.getVertices().get(fila));
            btnVertice.getTooltip().setText(showVerticeToolTip(grafo.getVertices().get(fila)));
            btnVertice.setText(grafo.getVertices().get(fila).getNombre());
            decorarBotonVertice(btnVertice);
            hbox.getChildren().add(btnVertice);
            HBox.setMargin(btnVertice,new Insets(10,0,20,0));
            for (int columna = 0; columna < matriz.length; columna++) {
                BotonArista btnArista = new BotonArista(Const.BTN_ALTO,Const.BTN_ANCHO,matriz[fila][columna]);
                btnArista.getTooltip().setText(showAristaToolTip(grafo.getVertices().get(fila),grafo.getVertices().get(columna)));
                if(matriz[fila][columna] != null) {
                    btnArista.getTooltip().setText(matriz[fila][columna].toStringToolTip());
                    btnArista.setFont(Font.font("Courier", FontWeight.NORMAL, FontPosture.REGULAR,10.5));
                    btnArista.setText(matriz[fila][columna].getPonderacionString(grafo.getPonderacionActiva()));
                    btnArista.setWrapText(true);
                    decorarBotonArista(btnArista);
                }
                gridPane.add(btnArista,fila,columna);
            }
        }
//        vbox.getChildren().remove(2);
        vbox.getChildren().set(1,hbox);
//        vbox.getChildren().remove(3);
        vbox.getChildren().set(2,gridPane);
    }

    // ACCIONES DESDE LA INTERFAZ
    @FXML
    void activarVertice(ActionEvent event) {

    }

    @FXML
    void actualizarPesaje(ActionEvent event) {
        if (rdbPrecio.isSelected()){
            app.getActualModificado().setPonderacionActiva(Const.PRECIO);
            app.getActualOriginal().setPonderacionActiva(Const.PRECIO);
            System.out.println("Se activo el precio");
        }
        else if (rdbDistancia.isSelected()){
            app.getActualModificado().setPonderacionActiva(Const.DISTANCIA);
            app.getActualOriginal().setPonderacionActiva(Const.DISTANCIA);
            System.out.println("Se activo la distancia");
        }
        else if (rdbTiempo.isSelected()){
            app.getActualModificado().setPonderacionActiva(Const.DISTANCIA);
            app.getActualOriginal().setPonderacionActiva(Const.DISTANCIA);
            System.out.println("Se activo el tiempo");
        }
    }

    @FXML
    void setPonderacionGrafoActual(ActionEvent event) {
        if (rdbPrecioGrafoActual.isSelected())
            app.getActualModificado().setPonderacionActiva(Const.PRECIO);
        else if (rdbDistanciaGrafoActual.isSelected())
            app.getActualModificado().setPonderacionActiva(Const.DISTANCIA);
        else if (rdbTiempoGrafoActual.isSelected())
            app.getActualModificado().setPonderacionActiva(Const.TIEMPO);
        cargarGrafoActual(app.getActualModificado());
    }

    @FXML
    void setPonderacionGrafoOriginal(ActionEvent event) {
        if (rdbPrecioGrafoOriginal.isSelected())
            app.getActualOriginal().setPonderacionActiva(Const.PRECIO);
        else if (rdbDistanciaGrafoOriginal.isSelected())
            app.getActualOriginal().setPonderacionActiva(Const.DISTANCIA);
        else if (rdbTiempoGrafoOriginal.isSelected())
            app.getActualOriginal().setPonderacionActiva(Const.TIEMPO);
        cargarGrafoOriginal(app.getActualOriginal());
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
            Out.msg("No se encontraron grafos","Por favor verifique que la carpeta de rutas"+
                    " no esta vacia", Alert.AlertType.INFORMATION);
        }
    }

    public String showAristaToolTip(Vertice v1, Vertice v2){
        return "Fila: " + v1.getNombre() + " Columna: " + v2.getNombre();
    }
    public String showVerticeToolTip(Vertice v1){
        return "Vertice: " + v1.getNombre();
    }

}
