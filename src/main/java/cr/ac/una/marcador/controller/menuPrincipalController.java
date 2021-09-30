package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.marcador.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class menuPrincipalController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnMarcas;

    @FXML
    private JFXButton btnMantenimiento;

    @FXML
    private JFXButton btnSalir;

    @FXML
    void onAction_btnMantenimiento(ActionEvent event) {
        FlowController.getInstance().hide();
        FlowController.getInstance().goViewInWindow("login");
    }

    @FXML
    void onAction_btnMarcas(ActionEvent event) {
        FlowController.getInstance().hide();
        FlowController.getInstance().goMarcas();
    }

    @FXML
    void onAction_btnSalir(ActionEvent event) {

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
