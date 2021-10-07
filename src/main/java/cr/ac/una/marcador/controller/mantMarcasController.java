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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
    /**
     * Initializes the controller class.
     */
    
     List<MarcaDto> listdto = null;
      EmpleadoDto emp=new EmpleadoDto();
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onAction_btnBuscar(ActionEvent event) {   
        
        String folio = txtBuscar.getText();
        if(!"".equals(txtBuscar.getText())) {
            
            emp = wsConsumer.getInstance().buscarEmpleadoFolio(txtBuscar.getText());
            
            listdto = wsConsumer.getInstance().buscarMarcasFolioFechas(folio);
        }
        else new Mensaje().showModal(Alert.AlertType.ERROR, "Datos insuficientes" ,this.getStage(),"Favor ingrese el dato a buscar");
        
        
        if(folio!=""&&folio.length()==7){
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
            case 4:  head ="Entradas";  break;
            case 5:  head ="Salidas";break;
                
        }
        return head;
    } 
     
    @FXML
    private void BtnExcel(ActionEvent event) {
        if(!"".equals(txtBuscar.getText())) {
             
            try (OutputStream fileOut = new FileOutputStream("Reporte"+txtBuscar.getText()+".xlsx")) {
                Workbook wb = new XSSFWorkbook();
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                Sheet sheet = wb.createSheet("Sheet");
                sheet.autoSizeColumn(150);
                Cell cell;
                Row row = sheet.createRow(0);
                
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
              //  row.setRowStyle(style);
                
                for (int i = 0 ;i < 6;i++){//cabeceras
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
                
                if(!listdto.isEmpty()){
              for (int i= 1; i < listdto.size(); i++) {    
                    row = sheet.createRow(i);
                 for (int k = 1; k <= 6; k++) {   
                    cell = row.createCell(k-1); 
                    cell.setCellStyle(style2);
                    style2.setBorderTop(BorderStyle.MEDIUM);
                    style2.setBorderBottom(BorderStyle.MEDIUM);
                    style2.setBorderLeft(BorderStyle.MEDIUM);
                    style2.setBorderRight(BorderStyle.MEDIUM);                    
                    
                 if(k == 1) cell.setCellValue(emp.getFolio());  
                    
                 if(k == 2) cell.setCellValue(emp.getCedula());
                   
                 if(k == 3) cell.setCellValue(emp.getNombre());
                   
                 if(k == 4) cell.setCellValue(emp.getApellido());
   
                 if(k == 5) cell.setCellValue(listdto.get(i).getHoraEntrada());
                 
                 if(k == 6) cell.setCellValue(listdto.get(i).getHoraSalida());
                 }
              }
            }
                
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Resultado satisfactorio" ,this.getStage(),"Se creó el excel solicitado");
                wb.write(fileOut);
            }catch(Exception e) {
                System.out.println(e.getMessage());
                new Mensaje().showModal(Alert.AlertType.ERROR, "Falla en archivo" ,this.getStage(),e.getMessage());  
            }
        } else new Mensaje().showModal(Alert.AlertType.ERROR, "Favor ingrese el dato a buscar" ,this.getStage(),"No se puede crear archivo");
        
    }

    @Override
    public void initialize() {
     
    }

    @Override
    public Node getRoot() {
       return root;
    }
    
}
