package mrdelivery.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mrdelivery.Main;
import mrdelivery.controller.ViewController;
import mrdelivery.view.Out;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {

    // Atributos
    public static JSONObject config;
    public static Pane ventanaPrincipal;
    public static Stage stage;
    public static ThreadLector threadLector;

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

            ViewController controller = loader.<ViewController> getController();
            controller.prepararVentana();
        } catch (IOException e) {
            Out.msg("No se pudo cargar " + rutaRelativa,e);
            e.printStackTrace();
        }
    }

    public void iniciarThreadLector(){
        LectorJSON lectorGrafos = new LectorJSON(config.getString("carpeta_rutas"),
                                                 config.getString("carpeta_leidos"));
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


}
