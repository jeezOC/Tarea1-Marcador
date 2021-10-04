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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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
    EmpleadoDto empleado;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onAction_btnBuscar(ActionEvent event) {
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
           try (OutputStream fileOut = new FileOutputStream("Reporte"+txtBuscar.getText()+".xlsx")) {  
            Workbook wb = new XSSFWorkbook();  
            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle(); 
            Sheet sheet = wb.createSheet("Sheet");
             Cell cell;
            Row row = sheet.createRow(0);  
           
            
             style.setFillForegroundColor(IndexedColors.GREEN.getIndex());  
             style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
//             row.setRowStyle(style);
            
             for (int i = 0 ;i < 6;i++){//cabeceras
                 
                cell = row.createCell(i); 
                cell.setCellStyle(style);
                cell.setCellValue(ValueOfHeader(i));  
                 
             }
               new Mensaje().showModal(Alert.AlertType.INFORMATION, "Resultado satisfactorio" ,this.getStage(),"Se creó el excel solicitado");
            wb.write(fileOut);  
            }catch(Exception e) {  
                System.out.println(e.getMessage());  
                new Mensaje().showModal(Alert.AlertType.ERROR, "Falla en archivo" ,this.getStage(),e.getMessage());  
            }
        
    }

    @Override
    public void initialize() {
     
    }

    @Override
    public Node getRoot() {
       return root;
    }
    
}
