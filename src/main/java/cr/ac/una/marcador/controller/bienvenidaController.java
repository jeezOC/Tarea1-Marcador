

package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.marcador.util.AppContext;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class bienvenidaController extends Controller implements Initializable{

    @FXML
    private VBox root;

    @FXML
    private ImageView imvEmp;

    @FXML
    private Label lblNombreApellido;
    
    @FXML
    private Label lblHora;

    @FXML
    private JFXButton btnCerrrar;

    @FXML
    void onAction_btnCerrrar(ActionEvent event) {
        this.getStage().close();
    }

    @Override
    public void initialize() {
        String[] EmpleadoMarca = (String[]) AppContext.getInstance().get("EmpleadoMarca");
        lblNombreApellido.setText(EmpleadoMarca[0] +" " +EmpleadoMarca[1]);
        lblHora.setText(EmpleadoMarca[2]);
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

}
