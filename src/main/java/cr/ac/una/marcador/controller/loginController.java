package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.marcador.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
public class loginController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtContra;

    @FXML
    private JFXButton btnSalir;

    @FXML
    private JFXButton btnConfirmar;

    @FXML
    void onAction_btnConfirmar(ActionEvent event) {
        
        try { // Call Web Service Operation
            cr.ac.una.relojunaws.WS service = new cr.ac.una.relojunaws.WS();
            cr.ac.una.relojunaws.Relojunaws port = service.getRelojunawsPort();
            // TODO initialize WS operation arguments here
            java.lang.String folio = txtID.getText();
            java.lang.String psswrd = txtContra.getText();
            // TODO process result here
            cr.ac.una.relojunaws.Respuesta result = port.login(folio, psswrd);
            if(result.isEstado()){
                System.out.println("Felicidades, haz conseguido consultar el WS-SOAP");
                System.out.println("SIUUUUUUUUUUUUUUUUUUUUUUUU");
                FlowController.getInstance().hide();
                FlowController.getInstance().goViewInWindow("baseContainer");
            }
        } catch (Exception ex) {
            System.out.println("Result = "+ex);
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