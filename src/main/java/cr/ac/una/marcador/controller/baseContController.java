package cr.ac.una.marcador.controller;
import com.jfoenix.controls.JFXButton;
import cr.ac.una.marcador.util.FlowController;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class baseContController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private VBox centerVBox;

    @FXML
    private JFXButton btnMantEmp;

    @FXML
    private JFXButton btnManMarc;

    @FXML
    private JFXButton btnReportes;

    @FXML
    private JFXButton btnSalir;


    @FXML
    void onAction_btnManMarc(ActionEvent event) {
        final double alturaInicio = centerVBox.getHeight();
        final Animation hideCenter = new Transition() {
            { setCycleDuration(Duration.millis(200)); }
            protected void interpolate(double frac) {
                final double curWidth = alturaInicio * (1.0 - frac);
                centerVBox.setTranslateY(-alturaInicio + curWidth);
            }
        };
        final Animation showCenter = new Transition() {
            { setCycleDuration(Duration.millis(200)); }
            protected void interpolate(double frac) {
                final double curWidth = alturaInicio * (2.0 - frac);
                centerVBox.setTranslateY((alturaInicio-(alturaInicio*2)) + curWidth);
            }
        };
        hideCenter.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                centerVBox.setVisible(false);
                showCenter.play();
                FlowController.getInstance().goView("mantenimientoMarcas");
                centerVBox.setVisible(true);
                showCenter.play();
            }
        });
        hideCenter.play();


    }

    @FXML
    void onAction_btnMantEmp(ActionEvent event) {
        final double alturaInicio = centerVBox.getHeight();
        final double alturaInicioNeg = -centerVBox.getHeight();
        final Animation hideCenter = new Transition() {
            { setCycleDuration(Duration.millis(200)); }
            protected void interpolate(double frac) {
                final double curWidth = alturaInicio * (1.0 - frac);
                centerVBox.setTranslateY(-alturaInicio + curWidth);
            }
        };
        final Animation showCenter = new Transition() {
            { setCycleDuration(Duration.millis(200)); }
            protected void interpolate(double frac) {
                final double curWidth = alturaInicio * (2.0 - frac);
                centerVBox.setTranslateY((alturaInicio-(alturaInicio*2)) + curWidth);
            }
        };
        hideCenter.onFinishedProperty().set(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                centerVBox.setVisible(false);
                showCenter.play();
                FlowController.getInstance().goView("mantenimientoEmpleados");
                centerVBox.setVisible(true);
                showCenter.play();

            }
        });
        hideCenter.play();

    }

    @FXML
    void onAction_btnSalir(ActionEvent event) {

    }

    @FXML
    void onAtion_btnReportes(ActionEvent event) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
