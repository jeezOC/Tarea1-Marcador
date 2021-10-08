/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.model.MarcaDto;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.wsConsumer;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class mantMarcasController extends Controller implements Initializable {
//    EmpleadoDto empleado;
    MarcaDto marca;
    @FXML
    private VBox root;
    @FXML
    private JFXDatePicker dpINI;
    @FXML
    private JFXDatePicker dpFin;
    @FXML
    private TextField txtBuscar;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton BtnExcel;
    @FXML
    private VBox boxView;
     @FXML
    private TableView<MarcaDto> tableMarcas;
     @FXML
    private TableColumn<MarcaDto, String> clmFolio;

    @FXML
    private TableColumn<MarcaDto, String> clmNombre;
    
    @FXML
    private TableColumn<MarcaDto, String>clmApellido;

    @FXML
    private TableColumn<MarcaDto, String> clmJornada;

    @FXML
    private TableColumn<MarcaDto, String> clmEntrada;

    @FXML
    private TableColumn<MarcaDto, String> clmSalida;
    
    @FXML
    private JFXButton btnBorrar;

    @FXML
    private JFXButton btnNuevo;

    @FXML
    private JFXButton btnGuardar;

 
    
    /**
     * Initializes the controller class.
     */
    
    List<MarcaDto> marcasList=null;
    EmpleadoDto emp = new EmpleadoDto();
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//            listMarcas.getItems().add("Entradas");
//            listMarcas.getItems().add("SALIDAS");
            BtnExcel.setDisable(true);
         
         
        
    }    
    
    @FXML
    private void onAction_btnBuscar(ActionEvent event) {   
        
        String folio = txtBuscar.getText();
         ObservableList<MarcaDto> MarcasForView =FXCollections.observableArrayList ();
        if(folio.equals("")) {
            marcasList = wsConsumer.getInstance().obtenerTodasMarcas();
        }else{
            emp = wsConsumer.getInstance().buscarEmpleadoFolio(txtBuscar.getText());
            marcasList = wsConsumer.getInstance().buscarMarcasFolioFechas(folio);
        }
        if(!marcasList.isEmpty()){
            marcasList.forEach(marca -> {
                MarcasForView.add(marca);
                clmFolio.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getEmpleadoid().getEmpleadoFolio()));
                clmNombre.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getEmpleadoid().getEmpleadoNombre()));
                clmApellido.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getEmpleadoid().getEmpleadoApellido()));
                clmJornada.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getMarcahoraEntrada().toString()));
                clmEntrada.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getMarcajornada().toString()));
                if(marca.getMarcahoraSalida()!=null){
                    clmSalida.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getMarcahoraSalida().toString()));
                }else{
                    clmSalida.setCellValueFactory(mark->new SimpleStringProperty("No registrada"));
                    clmSalida.setStyle(" -fx-text-fill: black");
                }   
            });
            tableMarcas.setItems((ObservableList<MarcaDto>) MarcasForView);
                    
            BtnExcel.setDisable(false);      
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Datos no existentes" ,this.getStage(),"No hay marcas registradas.");
        }
        
        if(!"".equals(folio)&&folio.length()==7){
            LocalDate fIni = dpINI.getValue();
            LocalDate fFin = dpFin.getValue();          
        }
    }
    
    private static String ValueOfHeader(int i){
       String head="";

       switch(i){
           case 0:  head ="Folio" ; break;        
           case 1:  head ="Cedula" ; break;
           case 2:  head ="Nombre" ; break;
           case 3:  head ="Apellido" ; break;

           case 4:  head ="Jornada";break;
           case 5:  head ="Entradas";  break;
           case 6:  head ="Salidas";break;

       }
       return head;
   } 
     
    @FXML
    private void BtnExcel(ActionEvent event) {
//        if(!"".equals(txtBuscar.getText())) { 
            try (OutputStream fileOut = new FileOutputStream("Reporte"+txtBuscar.getText()+".xlsx")) {
                Workbook wb = new XSSFWorkbook();
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                CreationHelper createHelper = wb.getCreationHelper();
                Sheet sheet = wb.createSheet("Sheet");
                sheet.autoSizeColumn(300);
                Cell cell;
                Row row = sheet.createRow(0);
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                for (int i = 0 ;i < 7;i++){//cabeceras
                    cell = row.createCell(i);
                    cell.setCellStyle(style);
                    style.setBorderTop(BorderStyle.MEDIUM);
                    style.setBorderBottom(BorderStyle.MEDIUM);
                    style.setBorderLeft(BorderStyle.MEDIUM);
                    style.setBorderRight(BorderStyle.MEDIUM);           
                    cell.setCellValue(ValueOfHeader(i));
                }
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                
                if(!marcasList.isEmpty()){
                    for (int i= 1; i < marcasList.size(); i++) {    
                        row = sheet.createRow(i);
                        for (int k = 1; k <= 7; k++) {   
                            cell = row.createCell(k-1); 
                            cell.setCellStyle(style2);
                            style2.setBorderTop(BorderStyle.MEDIUM);
                            style2.setBorderBottom(BorderStyle.MEDIUM);
                            style2.setBorderLeft(BorderStyle.MEDIUM);
                            style2.setBorderRight(BorderStyle.MEDIUM);                    
                            style2.setAlignment(HorizontalAlignment.CENTER);
                            if(k == 1) cell.setCellValue(emp.getFolio());  

                            if(k == 2) cell.setCellValue(emp.getCedula());

                            if(k == 3) cell.setCellValue(emp.getNombre());

                            if(k == 4) cell.setCellValue(emp.getApellido());

                            if(k == 5){
                               style2.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
                               cell.setCellValue(marcasList.get(i).getMarcajornada());
                            }
                            if(k == 6) {
                               style2.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
                               cell.setCellValue((LocalDateTime) marcasList.get(i).getMarcahoraEntrada());
                               cell.setCellStyle(style2);
                            }
                            if(k == 7) {
                               style2.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
                               cell.setCellValue((LocalDateTime) marcasList.get(i).getMarcahoraSalida());
                               cell.setCellStyle(style2);

                            }
                            sheet.setColumnWidth(k, 4500);
                            }
                    }
                }
                wb.write(fileOut);
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Resultado satisfactorio" ,this.getStage(),"Se creó el excel solicitado");  
            }catch(Exception e) {
                System.out.println(e.getMessage());
                new Mensaje().showModal(Alert.AlertType.ERROR, "Falla en archivo" ,this.getStage(),e.getMessage());  
            }
//        }else{
//            new Mensaje().showModal(Alert.AlertType.ERROR, "Favor ingrese el dato a buscar" ,this.getStage(),"No se puede crear archivo");
//        }
        
    }
    public void autoSizeColumns(Workbook workbook) {
    int numberOfSheets = workbook.getNumberOfSheets();
    for (int i = 0; i < numberOfSheets; i++) {
        Sheet sheet = workbook.getSheetAt(i);
        if (sheet.getPhysicalNumberOfRows() > 0) {
            Row row = sheet.getRow(sheet.getFirstRowNum());
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                sheet.autoSizeColumn(columnIndex);
            }
        }
    }
}
    @FXML
    void onAction_btnBorrar(ActionEvent event) {

    }

    @FXML
    void onAction_btnGuardar(ActionEvent event) {

    }

    @FXML
    void onAction_btnNuevo(ActionEvent event) {

    }

    @Override
    public void initialize() {
     
    }

    @Override
    public Node getRoot() {
       return root;
    }
    
}
