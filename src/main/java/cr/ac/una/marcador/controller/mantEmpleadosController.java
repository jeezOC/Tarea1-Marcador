package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
import cr.ac.una.marcador.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class mantEmpleadosController extends Controller implements Initializable {

    boolean isNuevo;
    @FXML
    private VBox root;

    @FXML
    private TextField txtFolio;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedula;

    @FXML
    private JFXToggleButton tggEsAdministrador;

    @FXML
    private JFXDatePicker dpFechaNacimiento;

    @FXML
    private Label lblFolio;

    @FXML
    private ImageView imgFotoEmpleado;

    @FXML
    private Label lblNombre;

    @FXML
    private JFXButton btnCambiarFoto;

    @FXML
    private JFXButton btnBorrar;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnLimpiar;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    void onAction_btnBorrar(ActionEvent event) {

    }

    @FXML
    void onAction_btnCambiarFoto(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("camaraView");
    }

    @FXML
    void onAction_btnEditar(ActionEvent event) {

    }

    @FXML
    void onAction_btnLimpiar(ActionEvent event) {

    }

    @FXML
    void onAction_btnNuevo(ActionEvent event) {
        if(isNuevo){
            txtFolio.setText("");
            txtFolio.setDisable(true);
            txtCedula.setDisable(false);
            txtNombre.setDisable(false);
            txtApellido.setDisable(false);
            dpFechaNacimiento.setDisable(false);
            btnNuevo.setText("GUARDAR");
            isNuevo = false;
        }else{
            txtFolio.setDisable(false);
            txtCedula.setDisable(true);
            txtNombre.setDisable(true);
            txtApellido.setDisable(true);
            dpFechaNacimiento.setDisable(true);
            btnNuevo.setText("NUEVO");
            isNuevo = true;
        }
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
        isNuevo = true;
        txtFolio.setDisable(false);
        txtCedula.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        dpFechaNacimiento.setDisable(true);
    }
}