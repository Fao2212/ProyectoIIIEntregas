package mrdelivery.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import mrdelivery.Main;
import mrdelivery.controller.ViewController;
import mrdelivery.view.Out;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Configuracion {

    public JSONObject leerConfiguracion(){
        JSONObject config;
        LectorJSON lector = new LectorJSON("config.json");
        lector.leer();
        ArrayList<JSONObject> archivoConfig = lector.getArchivosJson();
        if (archivoConfig.size() > 0)
            return archivoConfig.get(0);
        else
            return null;
    }
    public static Pane cargarPanel(String path) {
        Pane mainRoot = null;
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
            mainRoot = loader.load();

            ViewController controller = loader.<ViewController> getController();
            controller.prepararVentana();

        } catch (IOException e) {
            Out.msg("No se pudo cargar " + path,e);
            e.printStackTrace();
        }

        return  mainRoot;
    }

}
