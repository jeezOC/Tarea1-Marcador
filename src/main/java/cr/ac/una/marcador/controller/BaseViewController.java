package cr.ac.una.marcador.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.marcador.util.FlowController;
import cr.ac.una.marcador.util.Mensaje;
import cr.ac.una.marcador.util.Respuesta;
import cr.ac.una.marcador.util.wsConsumer;
//import cr.ac.una.relojunaws.EmpleadoDto;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.model.MarcaDto;
import cr.ac.una.marcador.util.AppContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.scene.text.TextAlignment;
import javax.xml.datatype.DatatypeConfigurationException;

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

    private JFXButton SetearBtn(JFXButton aux, String val, int x, int y){
        aux = new JFXButton();
        aux.setFocusTraversable(false);
        aux.setText(val);
        aux.setStyle("-fx-font-size: 15px;");
        aux.setStyle("-fx-text-fill: #E0EEF6;");
        grdClock.add(aux,x,y);
        return  aux;
      }
    private void Reloj(){
        Date date = new Date();
        SimpleDateFormat formatter;
        JFXButton aux = new JFXButton();
        String divd="/",divh=":";
        JFXButton  aux2 = new JFXButton(),aux3 = new JFXButton(),aux4 = new JFXButton();

        aux2.setTextAlignment(TextAlignment.CENTER);
        aux3.setTextAlignment(TextAlignment.CENTER);
        aux4.setTextAlignment(TextAlignment.CENTER);
        aux2.setStyle("-fx-font-size: 35px; -fx-text-fill: #E0EEF6;");
        aux3.setStyle("-fx-font-size: 35px; -fx-text-fill: #E0EEF6;");
        aux4.setStyle("-fx-font-size: 35px; -fx-text-fill: #E0EEF6;");
        SetearBtn(aux, divd,1,0);
        SetearBtn(aux, divd,3,0);
        SetearBtn(aux, divh,1,1);
        SetearBtn(aux, divh,3,1);
        //dia
        formatter = new SimpleDateFormat("dd");
        String strDate= formatter.format(date);
        SetearBtn(aux,strDate,0,0).setStyle("-fx-font-size: 35px; -fx-text-fill: #E0EEF6;");
        //mes
        formatter = new SimpleDateFormat("MM");
        strDate= formatter.format(date);
        SetearBtn(aux,strDate,2,0).setStyle("-fx-font-size: 35px; -fx-text-fill: #E0EEF6;");
        //año
        formatter = new SimpleDateFormat("yyy");
        strDate= formatter.format(date);
        SetearBtn(aux,strDate,4,0).setStyle("-fx-font-size: 35px; -fx-text-fill: #E0EEF6;");
        grdClock.setAlignment(Pos.CENTER);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Calendar cal = new GregorianCalendar();
                    int hour = cal.get(Calendar.HOUR);
                    int minute = cal.get(Calendar.MINUTE);
                    int seconds = cal.get(Calendar.SECOND);
                    String realTime = Integer.toString(hour) + " : " + Integer.toString(minute) + " : " + Integer.toString(seconds);
                    Platform.runLater(()->{
                        grdClock.getChildren().remove(aux2);
                        grdClock.getChildren().remove(aux3);
                        grdClock.getChildren().remove(aux4);
                        aux2.setText(String.valueOf(seconds));
                        if(minute<10) {
                          aux3.setText("0"+String.valueOf(minute));
                        }else{
                          aux3.setText(String.valueOf(minute));
                        }
                        if(hour==0) {
                          aux4.setText("12");
                        }else{
                          aux4.setText(String.valueOf(hour));
                        }
                        grdClock.add(aux2,4,1);
                        grdClock.add(aux3,2,1);
                        grdClock.add(aux4,0,1);
                    });
                    try {
                      Thread.sleep(1000);
                    } catch (Exception ex) {
                      ex.getStackTrace();
                    }
                }
            }
        }).start();
    }

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
    void onAction_btnConfirmar(ActionEvent event) throws DatatypeConfigurationException {
        String folio = txtFolio.getText();
        wsConsumer.getInstance().existeEmpleado(folio);
     //   aux =  wsConsumer.getInstance().buscarEmpleadoFolio(folio);
        if(wsConsumer.getInstance().getRespuesta().isEstado()){
            Date date= new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH)+1;
            int today = cal.get(Calendar.DAY_OF_MONTH);
            
            String h;
            if(cal.get(Calendar.HOUR)==0){
                h = "00";
            }else{
                h =String.valueOf(cal.get(Calendar.HOUR));
            }
            String m;
            if(cal.get(Calendar.MINUTE)<10){
                m = "0"+String.valueOf(cal.get(Calendar.MINUTE));
            }else{
                m = String.valueOf(cal.get(Calendar.MINUTE));
            }
            String hora = h +":"+m;
//           
            EmpleadoDto aux = wsConsumer.getInstance().buscarEmpleadoFolio(folio);
            if(aux != null){
                MarcaDto marcaDto = null;
                Optional<MarcaDto> ultima = null;
                List<MarcaDto> marcasEmpleado = wsConsumer.getInstance().buscarMarcasFolioFechas(folio);
                
                if(marcasEmpleado.isEmpty()){
                    marcaDto.crearMarca(LocalDateTime.now(),true);
                }else{
                    ultima = marcasEmpleado.stream()
                            .filter(x->x.getMarcajornada() == LocalDate.now()).findFirst();
                    
                  
                    marcaDto = ultima.orElse(null);
                    if(ultima==null){
                        marcaDto.crearMarca(LocalDateTime.now(),true);
                    }else{
                        marcaDto.crearMarca(LocalDateTime.now(),false);
                    }
                }
                if(wsConsumer.getInstance().crearMarca(marcaDto, folio).isEstado()){
                    AppContext.getInstance().set("EmpleadoMarca", new String[]{aux.getNombre(), aux.getApellido(),hora});

                    if(aux.getNacimiento().getDayOfMonth() == today &&  aux.getNacimiento().getMonthValue() == month){ 
        //                FlowController.getInstance().goViewInWindowUncap("birthday");
                         FlowController.getInstance().goViewInWindowModalUndec("birthday", this.getStage(), false);
                    }else{
        //                FlowController.getInstance().goViewInWindowUncap("bienvenido");
                        FlowController.getInstance().goViewInWindowModalUndec("bienvenido", this.getStage(), false);
                    }
                }else{
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Marcar", getStage(), wsConsumer.getInstance().getRespuesta().getMensaje());
                }
            }
            
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Marcar", getStage(), wsConsumer.getInstance().getRespuesta().getMensaje());
        }
        nFolio="";
        txtFolio.setText("");
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
        Reloj();
        txtFolio.setFocusTraversable(false);
        btnConfirmar.requestFocus();
    }
}

