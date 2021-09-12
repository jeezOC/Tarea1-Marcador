package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private GridPane grdClock;

    @FXML
    private TextField txtFolio;

    @FXML
    private JFXButton btnUno;

    @FXML
    private JFXButton btnDos;

    @FXML
    private JFXButton btnTres;

    @FXML
    private JFXButton btnCuatro;

    @FXML
    private JFXButton btnCinco;

    @FXML
    private JFXButton btnSeis;

    @FXML
    private JFXButton btnSiete;

    @FXML
    private JFXButton btnOcho;

    @FXML
    private JFXButton btnNueve;

    @FXML
    private JFXButton btnCero;

    @FXML
    private JFXButton btnBorrar;

    @FXML
    private JFXButton btnConfirmar;

    public String nFolio = "";
    public int tFolio = 0;

    @FXML
    void onAction_btnBorrar(ActionEvent event) {
        if(nFolio.length()>0){
            nFolio = nFolio.substring(0, nFolio.length() - 1);
            txtFolio.setText(nFolio);
        }else{
            nFolio="";

        }
    }

    @FXML
    void onAction_btnCero(ActionEvent event) {
        nFolio+="0";
        txtFolio.setText(nFolio);
    }

    @FXML
    void onAction_btnUno(ActionEvent event) {
        nFolio+="1";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnDos(ActionEvent event) {
        nFolio+="2";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnTres(ActionEvent event) {
        nFolio+="3";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnCuatro(ActionEvent event) {
        nFolio+="4";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnCinco(ActionEvent event) {
        nFolio+="5";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnSeis(ActionEvent event) {
        nFolio+="6";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnSiete(ActionEvent event) {
        nFolio+="7";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnOcho(ActionEvent event) {
        nFolio+="8";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnNueve(ActionEvent event) {
        nFolio+="9";
        txtFolio.setText(nFolio);

    }
    @FXML
    void onAction_btnConfirmar(ActionEvent event) {
//check db
    }


    public void initialize() {
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtFolio.setFocusTraversable(false);

    }

}

