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
        if(txtID.getText().equals("una") && txtContra.getText().equals("una")){
            FlowController.getInstance().hide();
            FlowController.getInstance().goViewInWindow("baseContainer");
        }else{
            System.out.print("error");

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