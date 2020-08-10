package mrdelivery;

import javafx.application.Application;
import javafx.stage.Stage;
import mrdelivery.model.App;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        App app = new App(primaryStage);
        app.iniciar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
