package mrdelivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mrdelivery.controller.ViewController;
import mrdelivery.view.Out;

import java.io.IOException;

public class Main extends Application {

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane ventana = cargarPanel("view/gui.fxml");
        primaryStage.setTitle("MrDelivery");
        primaryStage.setScene(new Scene(ventana));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
