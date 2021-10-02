package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.util.CodigoRespuesta;
import cr.ac.una.marcador.util.FlowController;
import cr.ac.una.marcador.util.Formato;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.Respuesta;

import cr.ac.una.marcador.util.wsConsumer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class mantEmpleadosController extends Controller implements Initializable {

    boolean isNuevo;
    EmpleadoDto empleado;
    List<Node> requeridos = new ArrayList<>();
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
    private TextField txtContra;

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
        try {
            cargarEmpleado(txtFolio.getText());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void onAction_btnLimpiar(ActionEvent event) {
        clearAll();
        disableAll(true);
    }

    @FXML
    void onAction_btnNuevo(ActionEvent event) {

        if (isNuevo) {
            txtFolio.setText("");
            txtFolio.setDisable(true);
            txtCedula.setDisable(false);
            txtNombre.setDisable(false);
            txtApellido.setDisable(false);
            dpFechaNacimiento.setDisable(false);
            btnNuevo.setText("GUARDAR");
            isNuevo = false;
        } else {
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
 isNuevo = true;

        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtApellido.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtFolio.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
//        txtPassword.setTextFormatter(Formato.getInstance().maxLengthFormat(8));
        empleado = new EmpleadoDto();
        nuevoEmpleado();
        indicarRequeridos();

        txtFolio.setDisable(false);
        txtCedula.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        dpFechaNacimiento.setDisable(false);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableAll(true);
    }



    void disableAll(boolean disable){
        txtFolio.setText("");
        txtFolio.setDisable(!disable);
        txtCedula.setDisable(disable);
        txtNombre.setDisable(disable);
        txtApellido.setDisable(disable);
        dpFechaNacimiento.setDisable(disable);
        txtContra.setDisable(disable);
    }
    void clearAll(){
        txtFolio.setText("");
        txtFolio.setText("");
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        dpFechaNacimiento.setPromptText("");
        txtContra.setText("");
    }

    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre, txtCedula));
    }

    private void nuevoEmpleado() {
        unbindEmpleado();
        empleado = new EmpleadoDto();
        bindEmpleado(true);
        validarAdministrador();
        txtFolio.clear();
        txtFolio.requestFocus();
    }
    

    private void cargarEmpleado(String folio) throws IOException, ClassNotFoundException {
        
            EmpleadoDto emp = (EmpleadoDto) wsConsumer.getInstance().buscarEmpleadoFolio(folio);
            Respuesta respuesta = new Respuesta(true,CodigoRespuesta.CORRECTO,"","","Empleado",emp);
        if (respuesta.getEstado()) {
            unbindEmpleado();
            empleado = (EmpleadoDto) respuesta.getResultado("Empleado");
            bindEmpleado(false);
            validarAdministrador();
            validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar empleado", getStage(), respuesta.getMensaje());
        }
    }

    private void unbindEmpleado() {
        txtFolio.textProperty().unbind();
        txtCedula.textProperty().unbindBidirectional(empleado.cedula);
        txtNombre.textProperty().unbindBidirectional(empleado.name);
        txtApellido.textProperty().unbindBidirectional(empleado.lastname);
        
        dpFechaNacimiento.valueProperty().unbindBidirectional(empleado.nacimiento);
        
        dpFechaNacimiento.valueProperty().unbindBidirectional(empleado.nacimiento);
        tggEsAdministrador.selectedProperty().unbindBidirectional(empleado.admin);
    }

    private void bindEmpleado(boolean nuevo) {
        if (!nuevo) {
            txtFolio.textProperty().bind(empleado.folio);
        }
        txtCedula.textProperty().bindBidirectional(empleado.cedula);
        txtNombre.textProperty().bindBidirectional(empleado.name);
        txtApellido.textProperty().bindBidirectional(empleado.lastname);
        dpFechaNacimiento.valueProperty().bindBidirectional(empleado.nacimiento);
        tggEsAdministrador.selectedProperty().bindBidirectional(empleado.admin);
    }

    void validarAdministrador() {
        if (tggEsAdministrador.isSelected()) {
            requeridos.addAll(Arrays.asList(/*txtPassword*/));
//            /*txtPassword*/.setDisable(false);
        } else {
            requeridos.removeAll(Arrays.asList(/*txtPassword*/));

//            /*txtPassword*/.validate();
//            /*txtPassword*/.clear();
//            /*txtPassword*/.setDisable(true);
        }
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && !((JFXTextField) node).validate()) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXPasswordField && !((JFXPasswordField) node).validate()) {
                if (validos) {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXDatePicker && ((JFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((JFXDatePicker) node).getAccessibleText();
                } else {
                    invalidos += "," + ((JFXDatePicker) node).getAccessibleText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && ((JFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

}
