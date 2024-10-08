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
import cr.ac.una.marcador.util.AppContext;
import cr.ac.una.marcador.util.FlowController;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.Respuesta;
import cr.ac.una.marcador.util.wsConsumer;
import java.awt.Event;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    private JFXButton btnFiltrar;
    @FXML
    private TextField txtBuscar;
      @FXML
    private ImageView imgInfo;
    @FXML
    private JFXButton btnBuscar;
    
    @FXML
    private JFXButton BtnExcel;
    @FXML
    private JFXButton btnInfo;
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

 
    @FXML
    private JFXButton btnSig;
    int cont = 0;
    @FXML
    void onAction_btnSig(ActionEvent event) {
        if(!indexes.isEmpty()){
            int primeraIncon = indexes.get(cont);
            tableMarcas.requestFocus();
            tableMarcas.getSelectionModel().select(primeraIncon);
            tableMarcas.getFocusModel().focus(primeraIncon);
            if(cont>=indexes.size()){
                cont = 0;
            }else{
                cont++;
            }
        }
    }
    /**
     * Initializes the controller class.
     */
    
    List<MarcaDto> marcasList=null;
    EmpleadoDto emp = new EmpleadoDto();
    @FXML
    private Label lblCantEmpRealizaronMarcas;
    @FXML
    private Label lblTotalMarcasRealizadas;
    @FXML
    private Label lblTotalHrsTrabajadasTodosEmp;
    
    Tooltip buscarInfo =  new Tooltip("Digite el folio que desea buscar o\ndejelo vacio para buscar en todo el sistema");
    List<Integer> indexes = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        indexes.clear();
        tableMarcas.refresh();
        btnInfo.setTooltip(buscarInfo);
        BtnExcel.setDisable(true);
        btnFiltrar.setDisable(true);
    }
    
    
    private void buscar(){
    tableMarcas.getItems().clear();
        String folio = txtBuscar.getText();
        if(folio.equals("")) {
            marcasList = wsConsumer.getInstance().obtenerTodasMarcas();
        }else{
            emp = wsConsumer.getInstance().buscarEmpleadoFolio(txtBuscar.getText());
            marcasList = wsConsumer.getInstance().buscarMarcasFolioFechas(folio);
        }
        actualizarTabla(marcasList);
    }
    
    @FXML
    private void onAction_btnBuscar(ActionEvent event) {   
        buscar();
        
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
    private  List<MarcaDto> FiltrarHora(LocalDateTime ini, LocalDateTime fin ){
        BtnExcel.setDisable(false);  
        btnFiltrar.setDisable(false);
        List<MarcaDto> marcasFltradas = marcasList.stream()
              .filter(m-> (!ini.isAfter(m.getMarcahoraEntrada())&& !fin.isBefore(m.getMarcahoraEntrada())) 
                      || (!ini.isAfter(m.getMarcahoraSalida()) && !fin.isBefore(m.getMarcahoraSalida())))
              .collect(Collectors.toList());
        return marcasFltradas;
    }
       private Date convertLocaDateToDate(LocalDate ld){
         return Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
    @FXML
    void onAction_btnFiltrar(ActionEvent event) {
        if(dpINI.getValue()!=null && dpFin.getValue() != null){
            Date ini = convertLocaDateToDate(dpINI.getValue());
            Date fin = convertLocaDateToDate(dpFin.getValue());
            if(!ini.equals(fin)){
                if(ini.before(fin)){
                    List<MarcaDto> marcasFltradas = FiltrarHora(dpINI.getValue().atStartOfDay(),dpFin.getValue().atTime(23,59));
                    if(!marcasFltradas.isEmpty()){
                        actualizarTabla(FiltrarHora(dpINI.getValue().atStartOfDay(), dpFin.getValue().atStartOfDay()));
                    }else{
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Marcas no encontradas" ,this.getStage(),"No se encontro ninguna marca en el rango de fechas seleccionado");  
                    }
                }else{
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Rango de fechas invalido" ,this.getStage(),"Por favor seleccione un rango de fechas valido");
                }
            }else{
                new Mensaje().show(Alert.AlertType.ERROR, "Validar datos", "Debe seleccionar un rango de fechas valido\nTome en cuenta que el rango fechas se extiende\nde las 00:00 del dia inicial a las 00:00 del dia final.");

            }
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Rango de fechas invalido" ,this.getStage(),"Por favor seleccione un rango de fechas");
        }
    }
    @FXML
    private void BtnExcel(ActionEvent event) {
            BtnExcel.setDisable(false);  
            btnFiltrar.setDisable(false);
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

        //TODO
        
        MarcaDto toDelete=(MarcaDto) tableMarcas.getFocusModel().getFocusedItem();
        
        try {
            if (toDelete.getMarcaid() == null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar marca", getStage(), "Debe seleccionar el empleado que desea eliminar.");
            } else {
                
                if(new Mensaje().showConfirmation("Eliminar marca", getStage(), "¿Desea eliminar la marca seleccionada?")){
                Respuesta respuesta  = wsConsumer.getInstance().eliminarMarcaId(toDelete.getMarcaid());
                
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar marca", getStage(), respuesta.getMensaje());
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar marca", getStage(), "Marca eliminada correctamente.");
                    //actualizar
                    buscar();
                }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(mantEmpleadosController.class.getName()).log(Level.SEVERE, "Error eliminando la marca.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar marca", getStage(), "Ocurrió un error eliminando la marca.");
        }
        
    }
    @FXML
    void onAction_btnEditar(ActionEvent event) {
        MarcaDto marcaSeleccionada = (MarcaDto) tableMarcas.getFocusModel().getFocusedItem();
        AppContext.getInstance().set("marcaSeleccionada", marcaSeleccionada);
        
        FlowController.getInstance().goViewInWindowModalUndec("correccionMarcas", this.getStage(), false);


    }
    @FXML
    private JFXButton btnNueva;
    @FXML
    void onAction_btnNueva(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModalUndec("correccionMarcas", this.getStage(), false);
    }

    @Override
    public void initialize() {
        indexes.clear();
        tableMarcas.refresh();
//        btnInfo.setTooltip(buscarInfo);
//        BtnExcel.setDisable(true);
//        btnFiltrar.setDisable(true);

    }

    @Override
    public Node getRoot() {
       return root;
    }
    
    ////////////////////// Streams ////////////////////////
    
    private String cantidadEmpleadosRealizaronMarcas(){
        //TODO suerte!
        if(marcasList == null){
            return "0";
        }else{
        int i = marcasList.stream().
                collect(Collectors.toCollection(
                () -> new TreeSet<MarcaDto>((m1,m2) 
                        -> m1.getEmpleadoid().getEmpleadoFolio().compareTo(m2.getEmpleadoid().getEmpleadoFolio()))
                )).size();
        
        return String.valueOf(i);
       }
    }
    
    private String totalMarcasRealizadas(){
          Long totalEntradas = marcasList.stream().filter(m-> m.getMarcahoraEntrada() != null).count();
          Long totalSalidas = marcasList.stream().filter(m-> m.getMarcahoraSalida() != null).count();
        return String.valueOf(totalEntradas + totalSalidas);
    }
    
    private String totalHorasTrabajadasPorTodosEmpleados(){
           
        Long totalEntradas = marcasList.stream().filter(m-> m.getMarcahoraEntrada() != null
        && m.getMarcahoraSalida() != null).mapToLong(m-> m.getMarcahoraSalida().getHour() - m.getMarcahoraEntrada().getHour() ).sum();     
        
        return String.valueOf(totalEntradas);
    }
    
    private void cargarStreamsToView(){
        lblCantEmpRealizaronMarcas.setText(cantidadEmpleadosRealizaronMarcas());
        lblTotalMarcasRealizadas.setText(totalMarcasRealizadas());
        lblTotalHrsTrabajadasTodosEmp.setText(totalHorasTrabajadasPorTodosEmpleados());
        
    }
    private void actualizarTabla(List<MarcaDto> marcasList){
        tableMarcas.getItems().clear();
        indexes.clear();
        String folio = txtBuscar.getText();
        cargarStreamsToView();
        ObservableList<MarcaDto> MarcasForView =FXCollections.observableArrayList ();
        
        if(!marcasList.isEmpty()){
            marcasList.forEach(marca -> {
                if(marca.getMarcahoraSalida()==null){
                    marca.setMarcahoraSalida(LocalDateTime.of(0, 1, 1, 0, 0, 0));
                }   
                MarcasForView.add(marca);
            });
            
            clmFolio.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getEmpleadoid().getEmpleadoFolio()));
            clmNombre.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getEmpleadoid().getEmpleadoNombre()));
            clmApellido.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getEmpleadoid().getEmpleadoApellido()));
            clmJornada.setCellValueFactory(mark->new SimpleStringProperty(mark.getValue().getMarcajornada().toString()));
            clmEntrada.setCellValueFactory(mark->new SimpleStringProperty(String.valueOf(mark.getValue().getMarcahoraEntrada())));
            clmSalida.setCellValueFactory(mark->new SimpleStringProperty(String.valueOf(mark.getValue().getMarcahoraSalida())));
            tableMarcas.setItems((ObservableList<MarcaDto>) MarcasForView);
            tableMarcas.setRowFactory(row->new TableRow<MarcaDto>(){
            @Override
            public void updateItem(MarcaDto item,boolean empty){
                super.updateItem(item, empty);
                if(item == null){
                    setStyle("-fx-fill : green");
                }else if(item.getMarcahoraSalida().toString().equals("0000-01-01T00:00")){
                    indexes.add(getIndex());
                    setStyle("-fx-background-color: #ffd7d1;");
    //                    setStyle("-fx-background-color: #baffba;");
                }else{
                     setStyle("-fx-background-color: #baffba;");
                }

    //           setStyle("-fx-fill : yellow");
            }

            });
            tableMarcas.refresh();
            BtnExcel.setDisable(false);  
            btnFiltrar.setDisable(false);
           
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Datos no existentes" ,this.getStage(),"No hay marcas registradas.");
        }
    }
    
    
}
