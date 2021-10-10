package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXToggleButton;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.util.AppContext;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.wsConsumer;
import java.io.File;
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
import javafx.scene.layout.VBox;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class reporteMarcasController extends Controller implements Initializable {

    @FXML
    private VBox root;


    @FXML
    private JFXDatePicker dpFin;

    @FXML
    private JFXToggleButton tggIsTodosMarcas;



    @FXML
    private JFXToggleButton tggIsTodosEmpleados;


    @FXML
    private JFXDatePicker dpIni;
    @FXML
    private JFXButton btnGenerarReportesMarcas;
    @FXML
    private JFXButton btnGenerarReportesEmpleados;
    @FXML
    private TextField txtFolioMarcas;
    @FXML
    private TextField txtFolioEmpleados;

    @FXML
    void onAction_tggIsTodosEmpleados(ActionEvent event) {
        validarDatosEmpleados();
    }

    @FXML
    void onAction_tggIsTodosMarcas(ActionEvent event) {
        validarDatosMarcas();
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public Node getRoot() {
        return root;
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    private Date convertLocaDateToDate(LocalDate ld){
        ZoneId z = ZoneId.of("America/Costa_Rica");
//        System.out.println(z);
        ZonedDateTime zdt = ld.atStartOfDay(z);
        Instant instant = zdt.toInstant();
        return Date.from(instant);
    }
    
        EmpleadoDto admin = (EmpleadoDto) AppContext.getInstance().get("admin");
    @FXML
    private void onAction_btnGenerarReportesMarcas(ActionEvent event) throws TransformerConfigurationException, TransformerException, JRException {
//        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        
        if(dpFin.getValue()!=null || dpIni.getValue()!=null){
            Date ini = convertLocaDateToDate(dpIni.getValue());
            Date fin = convertLocaDateToDate(dpFin.getValue());
            HashMap<String,Object> datos = new HashMap<>();
            datos.put("nombreAdmin",admin.getNombre()+" "+admin.getApellido());
            datos.put("folioAdmin", admin.getFolio());
        
        if(ini.after(fin)){
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Validar datos", this.getStage(), "La fecha de inicio no puede ser mayor que la fechas final.");
        }else{
            if(!tggIsTodosMarcas.isSelected() && txtFolioMarcas.getText().isBlank()){
               new Mensaje().showModal(Alert.AlertType.INFORMATION, "Validar datos", this.getStage(), "Ingrese el folio del empleado al que desea generar un reporte de marcas.");
            }else{
                
                
                datos.put("ini", ini);
                datos.put("fin", fin);
                if(tggIsTodosMarcas.isSelected()){
                    datos.put("tipo", 4);
                }else{
                    datos.put("folio", txtFolioMarcas);
                    datos.put("tipo", 3);
                }
                
//                TODO//
                    
                    byte[] bytes = wsConsumer.getInstance().generarReporteJasper(datos);
                     crearReportePdf(bytes,"prueba");

//                   org.w3c.dom.Document doc = wsConsumer.getInstance().generarReporteJasper(datos);
//                   Transformer transformer = TransformerFactory.newInstance().newTransformer();
//                   Result output = new StreamResult(new File("Reportes/ReportTemporaly.xml"));
//                   Source input = new DOMSource((org.w3c.dom.Node) doc);
//                   transformer.transform(input, output);
//                   
//                   JRViewer view = new JRViewer("Reportes/ReportTemporaly.xml", true);
            }
        }
        
    }else{
       new Mensaje().showModal(Alert.AlertType.INFORMATION, "Validar datos", this.getStage(), "Una o ambas fechas están vacias, verifique e intente de nuevo");
        }
    }

    
    public void crearReportePdf(byte[] bytes, String nombre){
        try (OutputStream out = new FileOutputStream(nombre+".pdf")) {
            out.write(bytes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(reporteMarcasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reporteMarcasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void onAction_btnGenerarReportesEmpleados(ActionEvent event) throws TransformerException, JRException {
        
        if(admin != null){
        HashMap<String,Object> datos = new HashMap<>();
        datos.put("nombreAdmin",admin.getNombre()+" "+admin.getApellido());
        datos.put("folioAdmin", admin.getFolio());
        if(!tggIsTodosEmpleados.isSelected() && txtFolioEmpleados.getText().isBlank()){
               new Mensaje().showModal(Alert.AlertType.INFORMATION, "Validar datos", this.getStage(), "Ingrese el folio del empleado al que desea generar un reporte de información.");
            }else{  
                if(tggIsTodosEmpleados.isSelected()){
                    datos.put("tipo", 1);
                }else{
                    datos.put("folio", txtFolioEmpleados);
                    datos.put("tipo", 2);
                }
                
//                TODO//
                 byte[] bytes = wsConsumer.getInstance().generarReporteJasper(datos);
                   
                 crearReportePdf(bytes,"prueba");
                 
//                 Transformer transformer = TransformerFactory.newInstance().newTransformer();
//                   Result output = new StreamResult(new File("ReportTemporaly.xml"));
//                   Source input = new DOMSource((org.w3c.dom.Node) doc);
//                   
//                   transformer.transform(input, output);
                   
                   
                   
                   
                   
//                   JRViewer viewer = new JRViewer("ReportTemporaly.xml", true);
////                    viewer.sett("REPORTE DE CLIENTES");
//                    viewer.setOpaque(true);
//                    viewer.setVisible(true);
//                    viewer.repaint();
//                    JasperPrint jp = JasperFillManager.
//                    System.out.println(new File(".").getAbsolutePath());
//                    JasperViewer.viewReport("ReportTemporaly.xml", true, false);
//                    System.out.println("aaaaaaaaaaaaaa");
//  viewer.setVisible(true);
//                   <view.
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Validar datos", this.getStage(), "Reporte generado.");
             
//                    wsConsumer.getInstance().generarReporteJasper(datos);
//                 F
            }
        }else{
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Validar datos", this.getStage(), "No se existe un admin registrado.");
        }
        
    }

    
    private void validarDatosMarcas(){
        
        if(tggIsTodosMarcas.isSelected()){
            txtFolioMarcas.setDisable(true);
        }else{
            txtFolioMarcas.setDisable(false);
        }
    }
    
    private void validarDatosEmpleados(){
        if(tggIsTodosEmpleados.isSelected()){
            txtFolioEmpleados.setDisable(true);
        }else{
            txtFolioEmpleados.setDisable(false);
        }
    }
    
    
}
