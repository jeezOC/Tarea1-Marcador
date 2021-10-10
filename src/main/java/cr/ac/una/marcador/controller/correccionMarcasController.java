/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import static cr.ac.una.marcador.controller.BaseViewController.obtenerUltimaMarca;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.util.AppContext;
import cr.ac.una.marcador.model.MarcaDto;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.wsConsumer;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.HOURS;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javax.xml.datatype.DatatypeConfigurationException;



/**
 *
 * @author jeez
 */
public class correccionMarcasController extends Controller implements Initializable {

          @FXML
    private VBox root;

    @FXML
    private TextField txtBuscar;  
    @FXML
    private Label lblEmpleado;
    

    @FXML
    private JFXButton btnBuscar;

    @FXML
    private JFXDatePicker dpFechaNueva;

    @FXML
    private Label lblEmpleado1;

    @FXML
    private JFXTextField txtHE;

    @FXML
    private JFXTextField txtME;

    @FXML
    private Label lblEmpleado11;

    @FXML
    private JFXTextField txtHS;

    @FXML
    private JFXTextField txtMS;

    @FXML
    private JFXButton btnSalir;

    @FXML
    private JFXButton btnConfirmar;
    MarcaDto marcaSeleccionada = new MarcaDto();
    EmpleadoDto aux = new EmpleadoDto();
    String folio;
    @FXML
    void onAction_btnBuscar(ActionEvent event) {
        folio = txtBuscar.getText();
        aux = wsConsumer.getInstance().buscarEmpleadoFolio(folio);
        lblEmpleado.setText(aux.getNombre()+" "+aux.getApellido());
        txtHE.setText("00");
        txtME.setText("00");
        txtHS.setText("00");
        txtMS.setText("00");
    }
    @FXML
    void onAction_btnConfirmar(ActionEvent event) throws DatatypeConfigurationException {
        
            int he = Integer.valueOf(txtHE.getText());
            int me = Integer.valueOf(txtME.getText());
            int hs = Integer.valueOf(txtHS.getText());
            int ms = Integer.valueOf(txtMS.getText());
            LocalDateTime entrada = dpFechaNueva.getValue().atTime(he , me);
            LocalDateTime salida = dpFechaNueva.getValue().atTime(hs , ms);
            if (aux != null) {
                MarcaDto marcaDto = new MarcaDto();
                List<MarcaDto> marcasEmpleado = wsConsumer.getInstance().buscarMarcasFolioFechas(folio);
                if (marcasEmpleado.isEmpty()) {
//                    marcaDto.crearMarca(LocalDateTime.now(), true);
                    marcaSeleccionada.setMarcajornada(dpFechaNueva.getValue());
                    marcaSeleccionada.setMarcahoraEntrada(entrada);
                    marcaSeleccionada.setMarcahoraSalida(salida);
                    if (wsConsumer.getInstance().crearMarca(marcaDto, folio).isEstado()){
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Marcar", getStage(), wsConsumer.getInstance().getRespuesta().getMensaje());

                    }else{
                        new Mensaje().showModal(Alert.AlertType.CONFIRMATION, "Marcar", getStage(), wsConsumer.getInstance().getRespuesta().getMensaje());

                    }
                }
            }

        
    }
   

    @FXML
    void onAction_btnSalir(ActionEvent event) {
        AppContext.getInstance().delete("marcaSeleccionada");
        this.getStage().close();

    }

    @Override
    public void initialize() {
        marcaSeleccionada = new MarcaDto();
        marcaSeleccionada = new MarcaDto();
        marcaSeleccionada = (MarcaDto)AppContext.getInstance().get("marcaSeleccionada");
        if(marcaSeleccionada!=null){
            txtBuscar.setText(marcaSeleccionada.getEmpleadoid().getEmpleadoFolio());
            dpFechaNueva.setValue(marcaSeleccionada.getMarcajornada());
            txtHE.setText(String.valueOf(marcaSeleccionada.getMarcahoraEntrada().getHour()));
            txtME.setText(String.valueOf(marcaSeleccionada.getMarcahoraEntrada().getMinute()));
            txtHS.setText(String.valueOf(marcaSeleccionada.getMarcahoraSalida().getHour()));
            txtMS.setText(String.valueOf(marcaSeleccionada.getMarcahoraSalida().getMinute()));
            
        }else{
            txtBuscar.setText("");
            dpFechaNueva.setValue(null);
            txtHE.setText("00");
            txtME.setText("00");
            txtHS.setText("00");
            txtMS.setText("00");
        }
            
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaSeleccionada = new MarcaDto();
        marcaSeleccionada = new MarcaDto();
        marcaSeleccionada = (MarcaDto)AppContext.getInstance().get("marcaSeleccionada");
        if(marcaSeleccionada!=null){
            txtBuscar.setText(marcaSeleccionada.getEmpleadoid().getEmpleadoFolio());
            dpFechaNueva.setValue(marcaSeleccionada.getMarcajornada());
            txtHE.setText(String.valueOf(marcaSeleccionada.getMarcahoraEntrada().getHour()));
            txtME.setText(String.valueOf(marcaSeleccionada.getMarcahoraEntrada().getMinute()));
            txtHS.setText(String.valueOf(marcaSeleccionada.getMarcahoraSalida().getHour()));
            txtMS.setText(String.valueOf(marcaSeleccionada.getMarcahoraSalida().getMinute()));
            
        }else{
            txtBuscar.setText("");
            dpFechaNueva.setValue(null);
            txtHE.setText("00");
            txtME.setText("00");
            txtHS.setText("00");
            txtMS.setText("00");
        }
    }

}



