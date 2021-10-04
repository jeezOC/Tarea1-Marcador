package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import cr.ac.una.marcador.util.FlowController;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.wsConsumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
public class loginController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtID;

    @FXML
    private PasswordField txtContra;

    @FXML
    private JFXButton btnSalir;

    @FXML
    private JFXButton btnConfirmar;

    @FXML
    void onAction_btnConfirmar(ActionEvent event) {
        
        try {   
            String folio = txtID.getText();
            String psswrd = txtContra.getText();
//            
//            boolean isAdmin = wsConsumer.getInstance().validarFolioContrasena(folio, psswrd);
//            if(isAdmin){
//               this.getStage().close();
                FlowController.getInstance().hide();
                FlowController.getInstance().goViewInWindow("baseContainer");
//            }
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error iniciando secion" ,this.getStage(),ex.getMessage());  
            txtID.setText("");
            txtContra.setText("");
        }
    }

    @FXML
    void onAction_btnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
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