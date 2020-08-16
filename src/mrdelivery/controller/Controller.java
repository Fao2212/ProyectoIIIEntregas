package mrdelivery.controller;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mrdelivery.model.App;
import mrdelivery.model.Const;
import mrdelivery.model.structures.*;
import mrdelivery.view.Out;
import mrdelivery.view.componentes.BotonArista;
import mrdelivery.view.componentes.BotonVertice;
import mrdelivery.view.componentes.VistaCamino;
import org.json.JSONObject;
import tray.notification.NotificationType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements ViewController {

    // Objetos generados por fxml
    @FXML private VBox vbxGrafoOriginal, vbxGrafoActual;

    @FXML private HBox hbxVerticesGrafoOriginal, hbxVerticesGrafoActual;

    @FXML private GridPane grdGrafoOriginal, grdGrafoActual;

    @FXML private ToggleGroup PonderacionGrafoActual;

    @FXML private RadioButton rdbPrecioGrafoOriginal, rdbDistanciaGrafoOriginal, rdbTiempoGrafoOriginal,
                        rdbPrecioGrafoActual, rdbDistanciaGrafoActual, rdbTiempoGrafoActual;

    @FXML private ToggleGroup PonderacionGrafoOriginal;

    @FXML private Button btnAvanzarOriginal, btnAvanzarActual, btnSiguienteGrafo, btnConexo, btnObtenerCaminoMinimo,
                   btnTodosLosCaminos, btnCaminoOptimo, btnRecorridoProfundidad, btnRecorridoAnchura,
                   btnRecorrerPrimDesde, btnCalcularPrim, btnRegresarAlGrafoNormal;

    @FXML private TextField tfdDesdeCaminosMinimos, tfdOrigenTodosLosCaminos, tfdDestinoTodosLosCaminos,
                      tfdProfundidadDesde, tfdAnchuraDesde, tfdPrimDesde;

    @FXML private ListView<CaminoAristas> lstCaminos, lstCaminosMinimos, lstRecorridoProfundidad,
                                          lstRecorridoAnchura, lstPrim;
    @FXML private ListView<String> lstCaminoAvanzado;

    // Variables propias
    private App app;
    private Arista tmpPasoActual;
    private double totalRecorrido;

    private GridPane crearCuadricula(){
        GridPane cuadricula = new GridPane();
        cuadricula.setAlignment(Pos.CENTER);
        return cuadricula;
    }

    private HBox crearFila(){
        HBox fila = new HBox();
        fila.setAlignment(Pos.CENTER);
        return fila;
    }

    @Override
    public void prepararVentana(){
        btnCalcularPrim.setTooltip(new Tooltip("Calcular prim"));
        btnRegresarAlGrafoNormal.setTooltip(new Tooltip("Regresar al grafo original"));
        btnRecorrerPrimDesde.setTooltip(new Tooltip("Recorrer el árbol de expansión mínima"));
        totalRecorrido = 0;
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

    private void reloadGrid(Grafo grafo, GridPane gridPane, VBox vbox, HBox hbox){
        gridPane = crearCuadricula();
        hbox = crearFila();
        Arista [][] matriz = grafo.getMatriz();
        for (int fila = 0; fila < matriz.length; fila++) {
            BotonVertice btnVertice = new BotonVertice(Const.BTN_ALTO,Const.BTN_ANCHO, grafo.getVertices().get(fila));
            grafo.getVertices().get(fila).setBoton(btnVertice);
            btnVertice.getTooltip().setText(showVerticeToolTip(grafo.getVertices().get(fila)));
            btnVertice.setText(grafo.getVertices().get(fila).getNombre());
            hbox.getChildren().add(btnVertice);
            HBox.setMargin(btnVertice,new Insets(10,0,20,0));

            for (int columna = 0; columna < matriz.length; columna++) {
                BotonArista btnArista = new BotonArista(Const.BTN_ALTO,Const.BTN_ANCHO,matriz[fila][columna]);
                btnArista.getTooltip().setText(showAristaToolTip(grafo.getVertices().get(fila),grafo.getVertices().get(columna)));
                if(matriz[fila][columna] != null) {
                    matriz[fila][columna].setPonderacionActual(grafo.getPonderacionActiva());
                    matriz[fila][columna].setBoton(btnArista);
                    btnArista.getTooltip().setText(matriz[fila][columna].toStringToolTip());
                    btnArista.setText(matriz[fila][columna].getPonderacionString(grafo.getPonderacionActiva()));
                }
                gridPane.add(btnArista,fila,columna);
            }
        }
        vbox.getChildren().set(1,hbox);
        vbox.getChildren().set(2,gridPane);
    }

    // ACCIONES DESDE LA INTERFAZ

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

    private void avanzarAdelante(Arista paso){
        PauseTransition quitarResaltadoDestino = new PauseTransition(Duration.millis(500));
        quitarResaltadoDestino.setOnFinished((cambio) -> {
                    paso.getDestino().quitarResaltado();
                }
        );

        PauseTransition resaltadoDestino = new PauseTransition(Duration.millis(500));
        resaltadoDestino.setOnFinished((cambio) -> {
                    paso.quitarResaltado();
                    paso.getDestino().resaltarEnPantalla();
                    quitarResaltadoDestino.play();
                }
        );

        PauseTransition resaltadoArista = new PauseTransition(Duration.millis(500));
        resaltadoArista.setOnFinished((cambio) -> {
                    paso.getOrigen().quitarResaltado();
                    paso.resaltarEnPantalla();
                    resaltadoDestino.play();
                }
        );
        PauseTransition resaltadoOrigen = new PauseTransition(Duration.millis(500));
        resaltadoOrigen.setOnFinished((cambio) -> {
                    paso.getOrigen().resaltarEnPantalla();
                    resaltadoArista.play();
                }
        );
        resaltadoOrigen.play();
    }

    private void avanzarAtras(Arista paso){
        PauseTransition quitarResaltadoOrigen = new PauseTransition(Duration.millis(500));
        quitarResaltadoOrigen.setOnFinished((cambio)->{
            paso.getOrigen().quitarResaltado();
        });

        PauseTransition quitarResaltadoArista = new PauseTransition(Duration.millis(500));
        quitarResaltadoArista.setOnFinished((cambio)->{
            paso.quitarResaltado();
            paso.getOrigen().resaltarEnPantalla();
            quitarResaltadoOrigen.play();
        });

        PauseTransition quitarResaltadoDestino = new PauseTransition(Duration.millis(500));
        quitarResaltadoDestino.setOnFinished((cambio)->{
            paso.getDestino().quitarResaltado();
            paso.resaltarEnPantalla();
            quitarResaltadoArista.play();
        });

        PauseTransition resaltadoDestino = new PauseTransition(Duration.millis(500));
        resaltadoDestino.setOnFinished((cambio) ->{
                    paso.getDestino().resaltarEnPantalla();
                    quitarResaltadoDestino.play();
                }
        );
        resaltadoDestino.play();
    }

    private String toStringPasoCamino(Vertice origen, Vertice destino){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (app.getActualModificado().getPonderacionActiva() == Const.PRECIO)
            return "Desde: " + origen.getNombre() + " Hacia: " + destino.getNombre() + " Costo: $" + df.format(totalRecorrido);
        else if (app.getActualModificado().getPonderacionActiva() == Const.DISTANCIA)
            return "Desde: " + origen.getNombre() + " Hacia: " + destino.getNombre() + " Distancia: " + df.format(totalRecorrido) + " km";
        else
            return "Desde: " + origen.getNombre() + " Hacia: " + destino.getNombre() + " Tiempo: " + df.format(totalRecorrido)+ " min";
    }

    @FXML
    void avanzarGrafoActual(ActionEvent event) {
        CaminoAristas caminoAristas = app.getActualModificado().getRecorridoActual();
        if (caminoAristas != null){
            if(!caminoAristas.camino.isEmpty()) {
                if (!caminoAristas.isRetrocediendo()) {
                    tmpPasoActual = caminoAristas.avanzarCamino();
                    System.out.println("PASO ACTUAL " + tmpPasoActual.toStringConexion());
                    if (tmpPasoActual.isActivo()) {
                        if (!caminoAristas.camino.isEmpty()){   // Caso en el que el ultimo elemento que se saco era el ultimo
                            System.out.println("Este es el siguiente en la lista enlazada = " + caminoAristas.camino.peek().getOrigen().getNombre());
                            if (tmpPasoActual.getDestino().equals(caminoAristas.camino.peek().getOrigen())) {
                                avanzarAdelante(tmpPasoActual);
                                totalRecorrido += tmpPasoActual.getPonderacionActual();
                                lstCaminoAvanzado.getItems().add(toStringPasoCamino(tmpPasoActual.getOrigen(),tmpPasoActual.getDestino()));
                            }
                            else {
                                // Tiene que retroceder pues X-Y U-Z por ejemplo
                                avanzarAdelante(tmpPasoActual);
                                totalRecorrido += tmpPasoActual.getPonderacionActual();
                                lstCaminoAvanzado.getItems().add(toStringPasoCamino(tmpPasoActual.getOrigen(),tmpPasoActual.getDestino()));
                                Out.pushNotification("Llego al final del camino","Intentando regresar", NotificationType.INFORMATION);
                                caminoAristas.setRetrocediendo(true);
                            }
                        }
                        else {
                            avanzarAdelante(tmpPasoActual);
                            totalRecorrido += tmpPasoActual.getPonderacionActual();
                            lstCaminoAvanzado.getItems().add(toStringPasoCamino(tmpPasoActual.getOrigen(),tmpPasoActual.getDestino()));
                        }
                    }
                    else {
                        Out.msg("Algo anda mal", "No puede seguir avanzando por aqui, regresando");
                        caminoAristas.setCaminoInterrumpido(true);
                        caminoAristas.setRetrocediendo(true);
                        caminoAristas.retrocederCamino();   // Esto para sacar al actual inactivo que se agrego por defecto
                    }
                }
                else {
                    if (!caminoAristas.recorridoPrevio.isEmpty()) {
                        avanzarAtras(caminoAristas.retrocederCamino());
                        totalRecorrido += tmpPasoActual.getPonderacionActual();
                        lstCaminoAvanzado.getItems().add(toStringPasoCamino(tmpPasoActual.getDestino(),tmpPasoActual.getOrigen()));
                    }
                    else {
                        Out.pushNotification("Informacion","Fin del retroceso",NotificationType.INFORMATION);
                        caminoAristas.setRetrocediendo(false);
                        while (!caminoAristas.camino.isEmpty() && caminoAristas.isCaminoInterrumpido()){
                            if (caminoAristas.camino.peek() != null && caminoAristas.camino.peek().isActivo()){
                                // Si el origen del siguiente en la lista es DIFERENTE a el origen de la ultima arista sacada de
                                // la pila de aristas para el retroceso
                                if (caminoAristas.camino.peek().getOrigen().equals(caminoAristas.getUltimoSacadoRecorridoPrevio().getOrigen())){
                                    caminoAristas.avanzarCamino();
                                }
                                else {
                                    System.out.println("Salio porque si se encontro un camino por donde seguir");
                                    break;
                                }
                            }
                            Out.msg("Informacion","El camino de aristas esta vacio");
                        }
                        if (caminoAristas.camino.isEmpty()){
                            Out.msg("Informacion","Fin del recorrido, no pudo seguir avanzando");
                        }
                    }
                }
            }
            else {
                Out.pushNotification("Informacion", "Fin del camino, total recorrido " + totalRecorrido, NotificationType.SUCCESS);
                totalRecorrido = 0;
            }
        }
        else {
            Out.msg("No hay recorridos cargados en este momento");
        }

    }

    @FXML
    void avanzarGrafoOriginal(ActionEvent event) {

    }

    @FXML
    void calcularRecorrido(ActionEvent event) {

    }

    @FXML
    void obtenerCaminoOptimo(ActionEvent event) {
        String origen = tfdOrigenTodosLosCaminos.getText();
        String destino = tfdDestinoTodosLosCaminos.getText();
        if (!origen.equals("") && !destino.equals("") && app.getActualModificado() != null){
            // Se verifica que son vertices del grafo
            Vertice verticeOrigen = app.getActualModificado().buscarVertice(origen);
            Vertice verticeDestino = app.getActualModificado().buscarVertice(destino);
            if ((verticeOrigen != null) && (verticeDestino != null)) {
                CaminoAristas caminoAristas = app.getActualModificado().caminoOptimo(verticeOrigen, verticeDestino);
                ObservableList<CaminoAristas> caminoAristasObservable = FXCollections.observableArrayList();
                caminoAristasObservable.addAll(caminoAristas);
                lstCaminos.setItems(caminoAristasObservable);
                lstCaminoAvanzado.getItems().removeAll();   // Limpia el camino para mostrar la siguiente secuencia de pasos
                lstCaminos.setCellFactory(caminoVerticesListView -> new VistaCamino());
            }
            else
                Out.msg("Algo anda mal ...","Por favor ingrese un origen y destino validos");
        }
        else
            Out.msg("Algo anda mal ...","Por favor ingrese el origen y el destino para obtener todos los caminos");
    }

    @FXML
    void mostrarCaminoMinimoDetallado(MouseEvent event) {

    }

    @FXML
    void obtenerCaminosMinimos(ActionEvent event) {
        String origen = tfdDesdeCaminosMinimos.getText();
        CaminoAristas caminoTotal = new CaminoAristas();
        if (!origen.equals("") && app.getActualModificado() != null){
            // Se verifica que el vertice ingresado existe
            Vertice vertice = app.getActualModificado().buscarVertice(origen);
            if (vertice != null){
                HashMap<Vertice,CaminoAristas> caminos = app.getActualModificado().caminosMinimos(vertice,app.getActualModificado().getPonderacionActiva());
                if (caminos != null) {
                    ArrayList<Vertice> verticesDelGrafo = app.getActualModificado().getVertices();
                    ArrayList<CaminoAristas> caminosMinimos = new ArrayList<>();
                    for (Vertice vert : verticesDelGrafo) {
                        System.out.println("Camino hacia el vertice "+vert.getNombre());
                        caminosMinimos.add(caminos.get(vert));
                        caminoTotal.join(caminos.get(vert));
                        for (Arista arista : caminos.get(vert).camino){
                            System.out.println(arista.toStringConexion());
                        }
                    }
                    ObservableList<CaminoAristas> caminoAristasObservable = FXCollections.observableArrayList();
                    caminoAristasObservable.addAll(caminosMinimos);
                    lstCaminosMinimos.setItems(caminoAristasObservable);
                    lstCaminosMinimos.refresh();
                    lstCaminosMinimos.setCellFactory(caminoVerticesListView -> new VistaCamino());

                    tmpPasoActual = null;
                    lstCaminoAvanzado.getItems().removeAll();   // Limpia el camino para mostrar la siguiente secuencia de pasos
                    app.getActualModificado().setRecorridoActual(caminoTotal);
                }
            }
            else
                Out.msg("Algo anda mal ...","Por favor ingrese un vertice valido");
        }
        else
            Out.msg("Algo anda mal ...","Por favor ingrese el origen para obtener los caminos desde alguna parte");
    }

    @FXML
    void mostrarCaminoDetallado(MouseEvent event) {
        String camino = lstCaminos.getSelectionModel().getSelectedItem().toString();
        Out.msg("Camino detallado",camino, Alert.AlertType.INFORMATION);
    }

    @FXML
    void obtenerTodosLosCaminos(ActionEvent event) {
        String origen = tfdOrigenTodosLosCaminos.getText();
        String destino = tfdDestinoTodosLosCaminos.getText();
        if (!origen.equals("") && !destino.equals("") && app.getActualModificado() != null){
            // Se verifica que son vertices del grafo
            Vertice verticeOrigen = app.getActualModificado().buscarVertice(origen);
            Vertice verticeDestino = app.getActualModificado().buscarVertice(destino);
            if ((verticeOrigen != null) && (verticeDestino != null)) {
                ArrayList<CaminoAristas> caminoAristas = app.getActualModificado().todosLosCaminos(verticeOrigen, verticeDestino);
                caminoAristas.sort(CaminoAristas::compareTo);   // Se ordenan los elementos de menor a mayor
                ObservableList<CaminoAristas> caminoAristasObservable = FXCollections.observableArrayList();
                caminoAristasObservable.addAll(caminoAristas);
                lstCaminos.setItems(caminoAristasObservable);
                lstCaminoAvanzado.getItems().removeAll();   // Limpia el camino para mostrar la siguiente secuencia de pasos
                lstCaminos.setCellFactory(caminoVerticesListView -> new VistaCamino());
            }
            else
                Out.msg("Algo anda mal ...","Por favor ingrese un origen y destino validos");
        }
        else
            Out.msg("Algo anda mal ...","Por favor ingrese el origen y el destino para obtener todos los caminos");
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

    @FXML
    void btnEjecutarPrim(ActionEvent event) {
        if (app.getActualModificado() != null){
            app.getActualModificado().prim();
            for (int i = 0;i<app.getActualModificado().getVertices().size();i++){
                System.out.println(i);
                System.out.println( app.getActualModificado().recorridoEnProfundidad(app.getActualModificado().getVertices().get(i)));
            }
            cargarGrafoActual(app.getActualModificado());
            Out.msg("El grafo actual ha sido recreado");
        }
    }

    @FXML
    void recorrerPrimDesde(ActionEvent event) {
        cargarRecorrido(tfdPrimDesde,lstPrim,Const.RECORRIDO_PRIM);
    }

    @FXML
    void regresarAlGrafoNormal(ActionEvent event) {
        if (app.getActualModificado() != null){
            app.setActualModificado(app.getActualOriginal().clonarGrafo());
            cargarGrafoActual(app.getActualOriginal());
            Out.msg("El grafo se ha revertido a su forma original");
        }
    }

    @FXML
    void obtenerEsConexo(ActionEvent event) {
        if (app.getActualModificado() != null){
            if (app.getActualModificado().esConexo())
                Out.msg("Informacion","Sí, hay camino hacia todos los demás. El grafo actual es conexo");
            else
                Out.msg("Informacion","No, no hay camino hacia todos los demás. El grafo actual no conexo");
        }
    }

    private void cargarRecorrido(TextField tfdOrigen, ListView<CaminoAristas> lista, int tipoRecorrido) {
        String origen = tfdOrigen.getText();
        CaminoAristas camino = new CaminoAristas();
        if (app.getActualModificado() != null){
            if (!origen.equals("")){
                // Se verifica que el vertice seleccionado exista y esta activo
                Vertice verticeOrigen = app.getActualModificado().buscarVertice(origen);
                if (tipoRecorrido == Const.RECORRIDO_PROFUNDIDAD)
                    camino = app.getActualModificado().recorridoEnProfundidad(verticeOrigen);
                else if (tipoRecorrido == Const.RECORRIDO_ANCHURA)
                    camino = app.getActualModificado().recorridoEnAnchura(verticeOrigen);
                else if (tipoRecorrido == Const.RECORRIDO_PRIM) {
//                app.getActualModificado().prim();
                    for (int i = 0;i<app.getActualModificado().getVertices().size();i++){

                        System.out.println(i);
                        System.out.println( app.getActualModificado().recorridoEnProfundidad(app.getActualModificado().getVertices().get(i)));
                    }
                    camino = app.getActualModificado().recorridoEnProfundidad(verticeOrigen);
                    Out.msg("Ojo","Recuerde reestablecer el grafo despues de calcular el Arbol de Expansión Mínima (Esto es temporal)");
                }
                if (verticeOrigen != null) {
                    ObservableList<CaminoAristas> caminoObservable = FXCollections.observableArrayList();
                    caminoObservable.addAll(camino);
                    lista.setItems(caminoObservable);
                    lista.setCellFactory(caminoVerticesListView -> new VistaCamino());
                }
                else
                    Out.msg("Algo anda mal ...","Por favor ingrese un origen valido");
            }
            else
                Out.msg("Algo anda mal ...", "Por favor ingrese el origen para obtener el " + tipoRecorrido);
            tmpPasoActual = null;
            lstCaminoAvanzado.getItems().removeAll();   // Limpia el camino para mostrar la siguiente secuencia de pasos
            app.getActualModificado().setRecorridoActual(camino);
        }
    }
    @FXML
    void obtenerRecorridoAnchura(ActionEvent event) {
        cargarRecorrido(tfdAnchuraDesde, lstRecorridoAnchura,Const.RECORRIDO_ANCHURA);
    }

    @FXML
    void obtenerRecorridoProfundidad(ActionEvent event) {
        cargarRecorrido(tfdProfundidadDesde, lstRecorridoProfundidad,Const.RECORRIDO_PROFUNDIDAD);
    }

    public void mostrarCaminoProfundidadDetallado(MouseEvent mouseEvent) {
    }
}
