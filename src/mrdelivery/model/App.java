package mrdelivery.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mrdelivery.Main;
import mrdelivery.controller.ViewController;
import mrdelivery.model.structures.Arista;
import mrdelivery.model.structures.Grafo;
import mrdelivery.model.structures.Vertice;
import mrdelivery.view.Out;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.html.CSS;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class App {

    // Atributos
    public static JSONObject config;
    public static Pane ventanaPrincipal;
    public static Stage stage;
    public static ThreadLector threadLector;
    ArrayList<Grafo> ejecutados;
    Grafo actualOriginal;
    Grafo actualModificado;

    // Constructor
    public App (Stage _stage){
        stage = _stage;
        stage.setOnCloseRequest((e)->{
            if (threadLector.isAlive()){
                try {
                    threadLector.detener();
                    threadLector.join();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
    }
    // Metodos


    public Grafo getActualOriginal() {
        return actualOriginal;
    }

    public Grafo getActualModificado() {
        return actualModificado;
    }

    public void leerConfiguracion(){
        LectorJSON lector = new LectorJSON("config.json");
        lector.leer();
        ArrayList<JSONObject> archivoConfig = lector.getArchivosJson();
        if (archivoConfig.size() > 0)
            config = archivoConfig.get(0);
        else
            System.out.println("Hubo un problema al leer las configuraciones de la aplicacion");
    }

    public void cargarPanel(String rutaRelativa) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(Paths.get(".",rutaRelativa).toString()));
            ventanaPrincipal = loader.load();
//            ventanaPrincipal.getStylesheets().add(Main.class.getResource("estilos.css").toExternalForm());
            ViewController controller = loader.<ViewController> getController();
            controller.prepararVentana();
            controller.setApp(this);
        } catch (IOException e) {
            Out.msg("No se pudo cargar " + rutaRelativa,e);
            e.printStackTrace();
        }
    }

    public void iniciarThreadLector(){
        LectorJSON lectorGrafos = new LectorJSON(config.getString("carpeta_rutas"),
                                                 config.getString("carpeta_leidos"),
                                                 config.getString("carpeta_errores"));
        threadLector = new ThreadLector(lectorGrafos);
        threadLector.start();
    }

    public void iniciar(){
        leerConfiguracion();
        cargarPanel(config.getString("archivo_fxml"));
        iniciarThreadLector();

        stage.setTitle(config.getString("nombre_app"));
        stage.setScene(new Scene(ventanaPrincipal));
        stage.show();
    }

    public void crearGrafo(JSONObject object){
        if(object != null) {
            JSONArray vertices = object.getJSONArray("vertices");
            JSONArray aristas = object.getJSONArray("aristas");
            ArrayList<Vertice> listaVertices = new ArrayList<Vertice>();
            ArrayList<Arista> listaAristas = new ArrayList<Arista>();
            for (int i = 0; i < vertices.length(); i++) {
                listaVertices.add(new Vertice(vertices.getString(i)));
            }
            for (int i = 0; i < aristas.length(); i++) {
                JSONObject arista = aristas.getJSONObject(i);
                Vertice origen = buscarVertice(arista.getString("origen"), listaVertices);
                Vertice destino = buscarVertice(arista.getString("destino"), listaVertices);
                    Arista nuevaArista = new Arista(origen, destino, arista.getBoolean("activo"),
                                                    arista.getDouble("costo"), arista.getDouble("km"),
                                                    arista.getDouble("minutos"));
                    nuevaArista.setGrafoAsociado(actualOriginal);
                    origen.addArista(nuevaArista);
                    listaAristas.add(nuevaArista);
            }
            actualOriginal = new Grafo(listaVertices, listaAristas);
            actualModificado = actualOriginal.clonarGrafo();
            actualModificado.prim();
            System.out.println(actualModificado.recorridoEnProfundidad(actualModificado.getVertices().get(3)));
        }
    }


    public Vertice buscarVertice(String nombre,ArrayList<Vertice> vertices){
        for (Vertice vertice:vertices){
            if(vertice.getNombre().equals(nombre))
                return vertice;
        }
        return null;
    }

    public JSONObject getNextJson(){
        ArrayList<JSONObject> archivos = threadLector.lectorJSON.getArchivosJson();
        if(!archivos.isEmpty())
            return archivos.remove(0);
        return null;
    }

    public void setActualModificado(Grafo actualOriginal) {
        this.actualModificado = actualOriginal;
    }


}
