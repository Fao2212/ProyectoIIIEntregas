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

import javax.swing.plaf.IconUIResource;

public class Controller implements ViewController {

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

    public void cargarGrafo(Grafo grafo){
        Arista[][] matriz = grafo.getMatriz();
        for (int i = 0; i < matriz.length; i++){
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < matriz.length; j++)
                if (matriz[i][j] != null)
                    s.append("["+matriz[i][j].isActivo()+"]");
                else
                    s.append("[ ]");
            System.out.println(s);
        }
        reloadGrids(matriz);
    }

    public void cargarGrafoOriginal(Grafo grafo){

    }

    public void cargarGrafoActual(Grafo grafo){

    }

    public void reloadGrids(Arista[][] matriz){
        grdGrafoOriginal = crearCuadricula();
        grdGrafoActual = crearCuadricula();
        hbxVerticesOriginal = crearFila();
        hbxVerticesActual = crearFila();
        int ponderacionActiva = getPonderacionActiva();
        for (int fila = 0; fila < matriz.length; fila++) {
            Button btnVerticeOriginal = new Boton(Const.BTN_ALTO,Const.BTN_ANCHO).getButton();
            Button btnVerticeActual = new Boton(Const.BTN_ALTO,Const.BTN_ANCHO).getButton();
            hbxVerticesOriginal.getChildren().add(btnVerticeOriginal);
            hbxVerticesActual.getChildren().add(btnVerticeActual);
            for (int columna = 0; columna < matriz.length; columna++) {
                Button btnAristaOriginal = new Boton(Const.BTN_ALTO,Const.BTN_ANCHO).getButton();
                Button btnAristaActual = new Boton(Const.BTN_ALTO,Const.BTN_ANCHO).getButton();

                String ponderacionArista = matriz[fila][columna].getPonderacionString(ponderacionActiva);
                btnAristaOriginal.setText(ponderacionArista);
                btnAristaActual.setText(ponderacionArista);

                grdGrafoOriginal.add(btnAristaOriginal,fila,columna);
                grdGrafoActual.add(btnAristaActual,fila,columna);
            }
        }
        vbxGrafoOriginal.getChildren().set(1,hbxVerticesOriginal);
        vbxGrafoActual.getChildren().set(1,hbxVerticesActual);
        vbxGrafoOriginal.getChildren().set(2,grdGrafoOriginal);
        vbxGrafoActual.getChildren().set(2,grdGrafoActual);
//        vbxGrafoOriginal.getChildren().remove(2);
//        vbxGrafoActual.getChildren().remove(2);
//        vbxGrafoOriginal.getChildren().add(1,hbxVerticesOriginal); // Index 1, porque es el segundo elemento en el vbox
//        vbxGrafoActual.getChildren().add(1,hbxVerticesActual);
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
                cargarGrafo(app.getActualOriginal());
            }
            else
                System.out.println("El grafo actual esta nulo");
        }
        else{
            Out.msg("Error en carga de rutas","Por favor verifique que la carpeta de rutas"+
                    " no esta vacia", Alert.AlertType.ERROR);
        }
    }
}
