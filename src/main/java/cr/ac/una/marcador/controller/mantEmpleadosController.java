package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.util.AppContext;
import cr.ac.una.marcador.util.CodigoRespuesta;
import cr.ac.una.marcador.util.FlowController;
import cr.ac.una.marcador.util.Formato;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.Respuesta;

import cr.ac.una.marcador.util.wsConsumer;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javafx.fxml.Initializable;
import java.net.URL;
import java.nio.file.Files;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
    private JFXButton btnNuevo;
    
    @FXML
    private JFXPasswordField txtContra;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnGuardar;

    @FXML
    void onAction_btnBorrar(ActionEvent event) throws IOException {
//        imageToByte();

        try {
            if (empleado.getFolio()== null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Debe cargar el empleado que desea eliminar.");
            } else {
                Respuesta respuesta = wsConsumer.getInstance().eliminarEmpleadoFolio(empleado.getFolio());
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), respuesta.getMensaje());
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar empleado", getStage(), "Empleado eliminado correctamente.");
                    nuevoEmpleado();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Ocurrio un error eliminando el empleado.");
        }

    }

    @FXML
    void onAction_btnCambiarFoto(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("camaraView",this.getStage(),false);
    }

     
    @FXML
    void onAction_btnNuevo(ActionEvent event) {
clearAll();
        disableAll(true);
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
        txtContra.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
        empleado = new EmpleadoDto();
            nuevoEmpleado();
        indicarRequeridos();

        txtFolio.setDisable(false);
        txtCedula.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtContra.setDisable(true);
        dpFechaNacimiento.setDisable(false);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppContext.getInstance().set("ViewImagePrincipal",imgFotoEmpleado);
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
        requeridos.addAll(Arrays.asList(txtNombre, txtCedula, txtApellido,dpFechaNacimiento /*,foto*/));
    }

    private void nuevoEmpleado(){
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
        txtContra.textProperty().unbindBidirectional(empleado.psswr);        
        dpFechaNacimiento.valueProperty().unbindBidirectional(empleado.nacimiento);
        tggEsAdministrador.selectedProperty().unbindBidirectional(empleado.admin);
        imgFotoEmpleado.setImage(null);
    }

    private void bindEmpleado(boolean nuevo) {
        if (!nuevo) {
            txtFolio.textProperty().bind(empleado.folio);
            try {
                byteToFile();
            } catch (IOException ex) {
                Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        txtCedula.textProperty().bindBidirectional(empleado.cedula);
        txtNombre.textProperty().bindBidirectional(empleado.name);
        txtApellido.textProperty().bindBidirectional(empleado.lastname);
        txtContra.textProperty().bindBidirectional(empleado.psswr);
        dpFechaNacimiento.valueProperty().bindBidirectional(empleado.nacimiento);
        tggEsAdministrador.selectedProperty().bindBidirectional(empleado.admin);
       
    }

    void validarAdministrador() {
        if (tggEsAdministrador.isSelected()) {
            requeridos.addAll(Arrays.asList(txtContra));
            txtContra.setDisable(false);
        } else {
           requeridos.removeAll(Arrays.asList(txtContra));
           txtContra.validate();
           txtContra.clear();
           txtContra.setDisable(true);
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

    @FXML
    private void seleccionAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    @FXML
    private void onAction_btnBuscar(ActionEvent event) {
        try {
            cargarEmpleado(txtFolio.getText());
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onAction_btnGuardar(ActionEvent event) {
         try {
            String invalidos = validarRequeridos();
           
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), invalidos);
            } else { 
                imageToByte();
                Respuesta respuesta = wsConsumer.getInstance().guardarEmpleado(empleado);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                } else {
                    unbindEmpleado();
                    empleado = (EmpleadoDto) respuesta.getResultado("Emp");
                    bindEmpleado(false);
                    
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar empleado", getStage(), "Empleado actualizado correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), "Ocurrio un error guardando el empleado.");
        }
    }

    
    
    private void imageToByte() throws IOException{
       
    File file = (File) AppContext.getInstance().get("fileImage");
    foto = Files.readAllBytes(file.toPath());
//    foto = Base64.getEncoder().encodeToString(bytes);
    empleado.setFoto(foto);
    System.out.println(foto);
    }
    
    private byte[] foto;
    
    private void byteToFile() throws IOException{
//    ByteArrayInputStream bais = new ByteArrayInputStream(empleado.getFoto());
//    BufferedImage bi = ImageIO.read(bais);
//    File archivoGenerado = new File("tempImage.jpg");
//    ImageIO.write(bi, "jpg", archivoGenerado);
//    
//    
//    AppContext.getInstance().set("fileImage", archivoGenerado);
//    Image img = new Image(archivoGenerado.toURI().toString());
//    AppContext.getInstance().set("imagen", img);
//        AppContext.getInstance().actualizarViewImagen();
    
    }
    
}
