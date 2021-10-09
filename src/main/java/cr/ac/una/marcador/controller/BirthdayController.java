/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.marcador.util.AppContext;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;


import animatefx.animation.AnimationFX;
import animatefx.animation.BounceIn;
import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import animatefx.animation.Swing;
import animatefx.animation.Tada;
import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.marcador.util.FlowController;
import java.io.ByteArrayInputStream;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;




/**
 * FXML Controller class
 *
 * @author Kendall
 */
public class BirthdayController extends Controller implements Initializable {


    @FXML
    private VBox root;

    @FXML
    private ImageView imvFondo;

    @FXML
    private ImageView imvGlobos1;

    @FXML
    private ImageView imvGlobos2;

    @FXML
    private Label lblSaludo;

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
         public void iniciarVista() {
        
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
      
            new BounceIn(lblSaludo).play();
            new Pulse(lblSaludo).play();
             new Flash(lblSaludo).play();
             AnimationFX afx = new Swing(lblSaludo);
           AnimationFX afx2 = new Tada(lblSaludo);
            transicion();
         
            mover(imvGlobos2,550,-550);
            mover(imvGlobos1,550,-550);
            
            afx.setCycleCount(50);
            afx.play();
            afx2.setCycleCount(50);
            afx2.play();   
    }
    //ANIMACIONES
     public void mover(Node c,double fromY, double toY){
      TranslateTransition t = new TranslateTransition();
         t.setNode(c);
         t.setDuration(Duration.millis(3500));
         t.setCycleCount(100);
         t.setAutoReverse(false);
         t.setFromY(fromY);
         t.setByY(toY);
         t.play();
     }   
        
     public void transicion(){
        
        FadeTransition ft = new FadeTransition(Duration.millis(1500), imvEmp);
       ft.setFromValue(0.1);
       ft.setToValue(0.8);
        ft.setCycleCount(100);
        ft.setAutoReverse(false);
        ft.play();
    }
    public void rotacion(Node msj){
        RotateTransition rt = new RotateTransition(Duration.millis(3000),msj);
        rt.setByAngle(360);
        rt.setCycleCount(200);
        rt.setAutoReverse(true);
        rt.play();
    }   

    @Override
    public void initialize() {
        EmpleadoDto empleado = (EmpleadoDto) AppContext.getInstance().get("empMarcador");
        if(empleado.getFoto()!=null){
            Image image = new Image(new ByteArrayInputStream(empleado.getFoto()));
            imvEmp.setImage(image);
        }
       iniciarVista();
        String[] EmpleadoMarca = (String[]) AppContext.getInstance().get("EmpleadoMarca");
        lblNombreApellido.setText(EmpleadoMarca[0] +" " +EmpleadoMarca[1]);
        lblHora.setText(EmpleadoMarca[2]);
        lblSaludo.setText("¡FELIZ CUMPLEAÑOS "+EmpleadoMarca[0]+"!");
    }

    @Override
    public Node getRoot() {
      return root;
    }
    
}
