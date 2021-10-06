/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.util.AppContext;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.wsConsumer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Farlen
 */
public class ReporteViewController extends Controller implements Initializable {

    @FXML
    private ToggleGroup tggGroupMarcaEmpleado;
    @FXML
    private ToggleGroup tggGroupMuchosUno;
    @FXML
    private JFXDatePicker dpIni;
    @FXML
    private JFXDatePicker dpFin;
    @FXML
    private JFXButton jfxButtonGenerar;
    @FXML
    private JFXRadioButton tggEmpleados;
    @FXML
    private JFXRadioButton tggMarcasDeEmpleados;
    @FXML
    private JFXRadioButton tggUnEmpleado;
    @FXML
    private JFXRadioButton tggTodosLosEmpleados;
    @FXML
    private TextField txtFolio;
    EmpleadoDto admin = (EmpleadoDto) AppContext.getInstance().get("admin");
    @FXML
    private VBox root;
    @FXML
    private TextField txtNombreArchivoMarcas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onActionBtnGenerar(ActionEvent event) {

        if (validarTipoReporte()) {
            datos.put("nombreAdmin", admin.getNombre() + " " + admin.getApellido());
            datos.put("folioAdmin", admin.getFolio());
            datos.put("folioEmp", txtFolio.getText());
            byte[] bytes = wsConsumer.getInstance().generarReporteJasper(datos);
                   
                 if(txtNombreArchivoMarcas.getText().length() != 0 && !crearReportePdf(bytes,txtNombreArchivoMarcas.getText())){
                     new Mensaje().show(Alert.AlertType.INFORMATION, "Generar reporte", "Ocurrió un error al crear el reporte.");
                 }else{
            new Mensaje().show(Alert.AlertType.INFORMATION, "Generar reporte", "Reporte generado correctamente.");
            limpiarCampos();
        }
        }

    }

    HashMap<String, Object> datos = new HashMap<>();

    public boolean crearReportePdf(byte[] bytes, String nombre){
        try (OutputStream out = new FileOutputStream("Reportes Generados/"+nombre+".pdf")) {
            out.write(bytes);
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(reporteMarcasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reporteMarcasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private void limpiarCampos() {
        txtFolio.setText("");
        dpIni.setValue(null);
        dpFin.setValue(null);
    }

    private boolean validarTipoReporte() {
        if(!txtNombreArchivoMarcas.getText().isEmpty() || !txtNombreArchivoMarcas.getText().contains(".")){
        if (tggEmpleados.isSelected()) {
            if (tggTodosLosEmpleados.isSelected()) {
                datos.put("tipo", 1);
                return true;
            } else {
                if (txtFolio.getText().isBlank()) {
                    new Mensaje().show(Alert.AlertType.ERROR, "Validar datos", "Debe ingresar el folio de un empleado folio.");
                    return false;
                } else {
                    datos.put("tipo", 2);
                    return true;
                }
            }

        } else {
            if (dpFin.getValue() != null || dpIni.getValue() != null) {
                Date ini = convertLocaDateToDate(dpIni.getValue());
                Date fin = convertLocaDateToDate(dpFin.getValue());

                if (ini.after(fin)) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Validar datos", this.getStage(), "La fecha de inicio no puede ser mayor que la fechas final.");
                    return false;
                } else {
                    datos.put("ini", ini);
                    datos.put("fin", fin);
                    if (tggUnEmpleado.isSelected()) {
                        if (txtFolio.getText().isBlank()) {
                            new Mensaje().show(Alert.AlertType.ERROR, "Validar datos", "Debe ingresar el folio de un empleado folio.");
                            return false;
                        } else {

                            datos.put("tipo", 3);
                            return true;

                        }
                    } else {
                        datos.put("tipo", 4);
                        return true;
                    }
                }
            } else {
                new Mensaje().show(Alert.AlertType.ERROR, "Validar datos", "Debe ingresar ambas fechas para consultar.");
                return false;
            }
        }
        }else{
            new Mensaje().show(Alert.AlertType.ERROR, "Validar datos", "Debe ingresar un nombre para el reporte, sin extensión.");
        return false;
        }

    }

    private Date convertLocaDateToDate(LocalDate ld) {
        ZoneId z = ZoneId.of("America/Costa_Rica");
        System.out.println(z);
        ZonedDateTime zdt = dpIni.getValue().atStartOfDay(z);
        Instant instant = zdt.toInstant();
        return Date.from(instant);
    }

    @Override
    public void initialize() {

    }

    @Override
    public Node getRoot() {
        return root;
    }

    @FXML
    private void onActionTggUnEmpleado(ActionEvent event) {
        txtFolio.setDisable(false);
    }

    @FXML
    private void onActionTggTodosEmpleado(ActionEvent event) {
        txtFolio.setDisable(true);
    }

    @FXML
    private void onActionTggEmpleados(ActionEvent event) {
        dpIni.setDisable(true);
        dpFin.setDisable(true);
    }

    @FXML
    private void onActionTggMarcasEmpleados(ActionEvent event) {
        dpIni.setDisable(false);
        dpFin.setDisable(false);
    }

}
