package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class reporteMarcasController extends Controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXDatePicker dpINI;

    @FXML
    private JFXDatePicker dpFin;

    @FXML
    private JFXToggleButton tggIsTodosMarcas;

    @FXML
    private TextField txtBuscarMarcas;

    @FXML
    private JFXButton btnGenerarMarcas;

    @FXML
    private JFXToggleButton tggIsTodosEmpleados;

    @FXML
    private TextField txtBuscarEpleados;

    @FXML
    private JFXButton btnGenerarEmpleados;

    @FXML
    void onAction_btnGenerar(ActionEvent event) {

    }

    @FXML
    void onAction_tggIsTodosEmpleados(ActionEvent event) {

    }

    @FXML
    void onAction_tggIsTodosMarcas(ActionEvent event) {

    }

    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node getRoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
