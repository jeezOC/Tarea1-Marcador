package cr.ac.una.marcador;

import cr.ac.una.marcador.util.FlowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //Elimina advertencia, al realizar consultas al soap. https://github.com/javaee/metro-jax-ws/issues/1237
        System.setProperty("javax.xml.soap.SAAJMetaFactory", "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
 //       
//        scene = new Scene(loadFXML("primary"), 640, 480);
//        stage.setScene(scene);
//        stage.show();
        FlowController.getInstance().InitializeFlow(stage, null);

        FlowController.getInstance().goMain();

//        FlowController.getInstance().goView("baseView");
    }

    public static void main(String[] args) {
        launch();
    }

}