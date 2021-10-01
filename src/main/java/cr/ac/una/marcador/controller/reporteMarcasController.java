package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class reporteMarcasController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXDatePicker dpINI;

    @FXML
    private JFXDatePicker dpFin;

    @FXML
    private TextField txtBuscar;

    @FXML
    private JFXToggleButton tggIsTodos;

    @FXML
    private JFXButton btnGenerar;

    @FXML
    void onAction_btnGenerar(ActionEvent event) {

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
