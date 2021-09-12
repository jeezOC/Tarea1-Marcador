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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;

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
     
       JFXButton  aux2 = new JFXButton(),aux3 = new JFXButton(),aux4 = new JFXButton();
    
    private void SetearBtn(JFXButton aux, String val, int x, int y){
    
        aux = new JFXButton();
        aux.setText(val);
        grdClock.add(aux,x,y);
        
      }
    
      private void Reloj(){
          Date date = new Date();  
          SimpleDateFormat formatter;   
          JFXButton aux = new JFXButton();
          String divd="/",divh=":";
          
          SetearBtn(aux, divd,1,0);
          SetearBtn(aux, divd,3,0);
          
            SetearBtn(aux, divh,1,1);
          SetearBtn(aux, divh,3,1);
              
          //dia
          formatter = new SimpleDateFormat("dd");  
          String strDate= formatter.format(date);
          SetearBtn(aux,strDate,0,0);
              
          
          //mes
          formatter = new SimpleDateFormat("MM");  
          strDate= formatter.format(date);
          SetearBtn(aux,strDate,2,0);
          
          //año
          formatter = new SimpleDateFormat("yyy");  
          strDate= formatter.format(date);
         SetearBtn(aux,strDate,4,0);
              
        new Thread(new Runnable() {
              @Override
              public void run() {
                  while (true){
                      Calendar cal = new GregorianCalendar();
                      int hour = cal.get(Calendar.HOUR);
                      int minute = cal.get(Calendar.MINUTE);
                      int seconds = cal.get(Calendar.SECOND);
                      String realTime = Integer.toString(hour) + " : " + Integer.toString(minute) + " : " + Integer.toString(seconds);
                      
                      System.out.println(realTime);

                           Platform.runLater(()->{
                         aux2.setText(String.valueOf(seconds));
                         aux3.setText(String.valueOf(minute));
                        aux4.setText(String.valueOf(hour));
                               grdClock.add(aux2,4,1);
                               grdClock.add(aux3,2,1);
                               grdClock.add(aux4,0,1);                       
                           });  
                          
                      try {
                          Thread.sleep(1000);
                      } catch (Exception ex) {
                          ex.getStackTrace();
                      }
                  } }
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
        Reloj();
        txtFolio.setFocusTraversable(false);

    }

}

